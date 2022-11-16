package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.PriceAlteration;

public abstract class PriceAlterationCommand extends CalculationCommand{
    protected PriceAlteration priceAlteration;
    protected PriceAlterationCommand(CartPrice cartPrice, PriceAlteration priceAlteration) {
        super(cartPrice);
        this.priceAlteration = priceAlteration;
    }
}
