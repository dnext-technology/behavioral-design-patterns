package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CalculationCommand {
    protected CartPrice cartPrice;

    protected CalculationCommand(CartPrice cartPrice) {
        this.cartPrice = cartPrice;
    }

    public abstract void calculate();
}
