package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;

public class QuantityCalculationCommand extends CalculationCommand{
    private Integer quantity;

    public QuantityCalculationCommand(CartPrice cartPrice, Integer quantity) {
        super(cartPrice);
        this.quantity = quantity;
    }

    @Override
    public void calculate() {
        if (quantity != null && quantity > 0) {
            cartPrice.getPrice().setDutyFreeAmount(cartPrice.getPrice().getDutyFreeAmount() * quantity);
        }
    }
}
