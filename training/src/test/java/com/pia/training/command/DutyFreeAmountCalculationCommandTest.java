package com.pia.training.command;

import com.pia.training.shoppingcart.command.DutyFreeAmountCalculationCommand;
import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DutyFreeAmountCalculationCommandTest {

    @Test
    void whenCartPriceIsNull_thenNullPointerExceptionMustBeThrown() {
        Assertions.assertThrows(NullPointerException.class, () -> new DutyFreeAmountCalculationCommand(null).calculate());
    }

    @Test
    void whenPriceOfCartPriceIsNull_thenNullPointerExceptionMustBeThrown() {
        Assertions.assertThrows(NullPointerException.class, () -> new DutyFreeAmountCalculationCommand(new CartPrice()).calculate());
    }

    @Test
    void whenTaxRateIsNullAndDutyFreeAmountIsNull_thenDutyFreeAmountMustBeNull() {
        CartPrice cartPrice = new CartPrice();
        Price price = new Price();
        cartPrice.setPrice(price);
        new DutyFreeAmountCalculationCommand(cartPrice).calculate();
        Assertions.assertNotNull(cartPrice);
        Assertions.assertNotNull(cartPrice.getPrice());
        Assertions.assertNull(cartPrice.getPrice().getDutyFreeAmount());
    }

    @Test
    void whenTaxIncludedAmountIsNullAndDutyFreeAmountIsNull_thenDutyFreeAmountMustBeNull() {
        CartPrice cartPrice = new CartPrice();
        Price price = new Price();
        price.setTaxRate(20.0f);
        cartPrice.setPrice(price);
        new DutyFreeAmountCalculationCommand(cartPrice).calculate();
        Assertions.assertNotNull(cartPrice);
        Assertions.assertNotNull(cartPrice.getPrice());
        Assertions.assertNull(cartPrice.getPrice().getDutyFreeAmount());
    }

    @Test
    void whenDutyFreeAmountIsNotNull_thenDutyFreeAmountMustBeTheSame() {
        CartPrice cartPrice = new CartPrice();
        Price price = new Price();
        price.setDutyFreeAmount(100.0f);
        cartPrice.setPrice(price);
        new DutyFreeAmountCalculationCommand(cartPrice).calculate();
        Assertions.assertNotNull(cartPrice);
        Assertions.assertNotNull(cartPrice.getPrice());
        Assertions.assertNotNull(cartPrice.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(100.0f, cartPrice.getPrice().getDutyFreeAmount());
    }

    @Test
    void whenDutyFreeAmountIsNullAndTaxRateAndTaxIncludedAmountIsNotNull_thenDutyFreeAmountMustBeCalculated() {
        CartPrice cartPrice = new CartPrice();
        Price price = new Price();
        price.setTaxRate(20.0f);
        price.setTaxIncludedAmount(120.0f);
        cartPrice.setPrice(price);
        new DutyFreeAmountCalculationCommand(cartPrice).calculate();
        Assertions.assertNotNull(cartPrice);
        Assertions.assertNotNull(cartPrice.getPrice());
        Assertions.assertNotNull(cartPrice.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(100.0f, cartPrice.getPrice().getDutyFreeAmount());
    }
}
