package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculationCommand extends CalculationCommand{

    public TaxCalculationCommand(CartPrice cartPrice) {
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
}
