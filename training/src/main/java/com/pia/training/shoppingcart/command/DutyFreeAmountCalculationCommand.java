package com.pia.training.shoppingcart.command;

import com.pia.training.shoppingcart.model.CartPrice;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class DutyFreeAmountCalculationCommand extends CalculationCommand {

    public DutyFreeAmountCalculationCommand(CartPrice cartPrice) {
        super(cartPrice);
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
}
