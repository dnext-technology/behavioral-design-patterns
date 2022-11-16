package com.pia.training.shoppingcart.chainofresponsibility;

import com.pia.training.shoppingcart.command.DiscountAlterationCommand;
import com.pia.training.shoppingcart.command.OverrideAlterationCommand;
import com.pia.training.shoppingcart.command.PriceAlterationCommand;
import com.pia.training.shoppingcart.command.RaiseAlterationCommand;
import com.pia.training.shoppingcart.model.AlterationType;
import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.PriceAlteration;

import java.util.Comparator;

public class PriceAlterationStep extends CalculationStep {
    private Integer quantity;
    public PriceAlterationStep(CartPrice cartPrice, Integer quantity) {
        super(cartPrice);
        this.quantity = quantity;
    }

    @Override
    public void calculate() {
        if (cartPrice.getPriceAlteration() != null && !cartPrice.getPriceAlteration().isEmpty()) {
            cartPrice.getPriceAlteration()
                    .stream()
                    .sorted(Comparator.comparing(PriceAlteration::getPriority))
                    .map(priceAlteration -> createPriceAlterationCommand(priceAlteration, cartPrice))
                    .forEach(priceAlterationCommand -> priceAlterationCommand.calculate());
        }
    }

    @Override
    public CalculationStep getNextStep() {
        return new QuantityCalculationStep(cartPrice, quantity);
    }

    public PriceAlterationCommand createPriceAlterationCommand(PriceAlteration priceAlteration, CartPrice cartPrice) {
        if (priceAlteration.getAlterationType() == AlterationType.DISCOUNT) {
            return new DiscountAlterationCommand(cartPrice, priceAlteration);
        } else if (priceAlteration.getAlterationType() == AlterationType.RAISE) {
            return new RaiseAlterationCommand(cartPrice, priceAlteration);
        } else if (priceAlteration.getAlterationType() == AlterationType.OVERRIDE) {
            return new OverrideAlterationCommand(cartPrice, priceAlteration);
        }
        return null;
    }
}
