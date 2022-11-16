package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.PriceAlteration;

public class OverrideAlterationCommand extends PriceAlterationCommand{

    public OverrideAlterationCommand(CartPrice cartPrice, PriceAlteration priceAlteration) {
        super(cartPrice, priceAlteration);
    }

    @Override
    public void calculate() {
        cartPrice.getPrice().setDutyFreeAmount(priceAlteration.getPrice().getDutyFreeAmount());
    }
}
