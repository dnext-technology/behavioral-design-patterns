package com.pia.training.shoppingcart.calculation;

import com.pia.training.shoppingcart.command.*;
import com.pia.training.shoppingcart.model.AlterationType;
import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.PriceAlteration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PriceCalculationService {

    private CartPrice cartPrice;

    public PriceCalculationService(CartPrice cartPrice) {
        this.cartPrice = cartPrice;
    }

    public CartPrice calculate(Integer quantity) {
        if (cartPrice == null || cartPrice.getPrice() == null) {
            return null;
        }
        new DutyFreeAmountCalculationCommand(cartPrice).calculate();

        if (cartPrice.getPriceAlteration() != null && !cartPrice.getPriceAlteration().isEmpty()) {
            cartPrice.getPriceAlteration()
                    .stream()
                    .sorted(Comparator.comparing(PriceAlteration::getPriority))
                    .map(priceAlteration -> createPriceAlterationCommand(priceAlteration, cartPrice))
                    .forEach(priceAlterationCommand -> priceAlterationCommand.calculate());
        }
        new QuantityCalculationCommand(cartPrice, quantity).calculate();
        new TaxCalculationCommand(cartPrice).calculate();
        return cartPrice;
    }


    public PriceAlterationCommand createPriceAlterationCommand(PriceAlteration priceAlteration, CartPrice cartPrice){
        if(priceAlteration.getAlterationType() == AlterationType.DISCOUNT){
            return new DiscountAlterationCommand(cartPrice, priceAlteration);
        }
        if(priceAlteration.getAlterationType() == AlterationType.RAISE){
            return new RaiseAlterationCommand(cartPrice, priceAlteration);
        }
        if(priceAlteration.getAlterationType() == AlterationType.OVERRIDE){
            return new OverrideAlterationCommand(cartPrice, priceAlteration);
        }
        return null;
    }

}
