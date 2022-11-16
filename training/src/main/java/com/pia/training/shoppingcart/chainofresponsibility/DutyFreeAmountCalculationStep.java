package com.pia.training.shoppingcart.chainofresponsibility;

import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DutyFreeAmountCalculationStep extends CalculationStep {
    private Integer quantity;

    public DutyFreeAmountCalculationStep(CartPrice cartPrice, Integer quantity) {
        super(cartPrice);
        this.quantity = quantity;
    }

    @Override
    public void calculate() {
        if (cartPrice.getPrice().getDutyFreeAmount() == null
                && cartPrice.getPrice().getTaxRate() != null
                && cartPrice.getPrice().getTaxIncludedAmount() != null) {
            cartPrice.getPrice().setDutyFreeAmount(
                    BigDecimal.valueOf(cartPrice.getPrice().getTaxIncludedAmount() /
                                    (1.0F + cartPrice.getPrice().getTaxRate() / 100.0F)).setScale(2, RoundingMode.HALF_UP)
                            .floatValue());
        }
    }

    @Override
    public CalculationStep getNextStep() {
        return new PriceAlterationStep(cartPrice, quantity);
    }
}
