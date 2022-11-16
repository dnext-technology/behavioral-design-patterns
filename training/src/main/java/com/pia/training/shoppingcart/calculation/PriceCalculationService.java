package com.pia.training.shoppingcart.calculation;

import com.pia.training.shoppingcart.chainofresponsibility.DutyFreeAmountCalculationStep;
import com.pia.training.shoppingcart.model.CartPrice;

public class PriceCalculationService {

    private CartPrice cartPrice;

    public PriceCalculationService(CartPrice cartPrice) {
        this.cartPrice = cartPrice;
    }

    public CartPrice calculate(Integer quantity) {
        if (cartPrice == null || cartPrice.getPrice() == null) {
            return null;
        }
        new DutyFreeAmountCalculationStep(cartPrice,quantity).calculateAll();
        return cartPrice;
    }

}
