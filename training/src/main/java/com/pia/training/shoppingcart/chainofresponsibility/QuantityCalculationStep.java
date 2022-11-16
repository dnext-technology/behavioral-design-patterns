package com.pia.training.shoppingcart.chainofresponsibility;

import com.pia.training.shoppingcart.model.CartPrice;

public class QuantityCalculationStep extends CalculationStep {
    protected Integer quantity;

    public QuantityCalculationStep(CartPrice cartPrice, Integer quantity) {
        super(cartPrice);
        this.quantity = quantity;
    }

    @Override
    public void calculate() {
        if (quantity != null && quantity > 0) {
            cartPrice.getPrice().setDutyFreeAmount(cartPrice.getPrice().getDutyFreeAmount() * quantity);
        }
    }

    @Override
    public CalculationStep getNextStep() {
        return new TaxCalculationStep(cartPrice);
    }
}
