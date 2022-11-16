package com.pia.training.shoppingcart.chainofresponsibility;

import com.pia.training.shoppingcart.model.CartPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculationStep extends CalculationStep {

    public TaxCalculationStep(CartPrice cartPrice) {
        super(cartPrice);
    }

    @Override
    public void calculate() {
        Float taxRate = cartPrice.getPrice().getTaxRate() == null ? 0.0F : cartPrice.getPrice().getTaxRate();
        cartPrice.getPrice().setTaxIncludedAmount(
                BigDecimal.valueOf(cartPrice.getPrice().getDutyFreeAmount()
                                *
                                (1.0F + taxRate / 100.0F)).setScale(2, RoundingMode.HALF_UP)
                        .floatValue());
    }

    @Override
    public CalculationStep getNextStep() {
        return null;
    }
}
