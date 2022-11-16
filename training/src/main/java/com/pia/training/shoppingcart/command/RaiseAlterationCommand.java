package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.PriceAlteration;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RaiseAlterationCommand extends PriceAlterationCommand{

    public RaiseAlterationCommand(CartPrice cartPrice, PriceAlteration priceAlteration) {
        super(cartPrice, priceAlteration);
    }

    @Override
    public void calculate() {
        if (priceAlteration.getPrice().getPercentage() != null) {
            cartPrice.getPrice().setDutyFreeAmount(
                    BigDecimal.valueOf(cartPrice.getPrice().getDutyFreeAmount()
                                    *
                                    (1.0F + priceAlteration.getPrice().getPercentage() / 100.0F))
                            .setScale(2, RoundingMode.HALF_UP)
                            .floatValue());
        } else {
            cartPrice.getPrice().setDutyFreeAmount(cartPrice.getPrice().getDutyFreeAmount() + priceAlteration.getPrice().getDutyFreeAmount());
        }
    }
}
