package com.pia.training;

import com.pia.training.shoppingcart.calculation.PriceCalculationService;
import com.pia.training.shoppingcart.model.AlterationType;
import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.Price;
import com.pia.training.shoppingcart.model.PriceAlteration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class PriceCalculationServiceTest {

    @Test
    void whenThereIsNoDutyFreeAmount_thenDutyFreeAmountMustBeCalculatedCorrectly(){
        Price price = new Price();
        price.setTaxIncludedAmount(120.0f);
        price.setTaxRate(20.0f);
        CartPrice cartPrice = new CartPrice();
        cartPrice.setPrice(price);

        PriceCalculationService priceCalculationService = new PriceCalculationService(cartPrice);
        CartPrice calculationResult = priceCalculationService.calculate(0);
        Assertions.assertNotNull(calculationResult);
        Assertions.assertNotNull(calculationResult.getPrice());
        Assertions.assertEquals(100.0f, calculationResult.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(120.0f, calculationResult.getPrice().getTaxIncludedAmount());
        Assertions.assertEquals(20.0f, calculationResult.getPrice().getTaxRate());
    }

    @Test
    void whenThereIsNoTaxIncludedAmount_thenTaxIncludedAmountMustBeCalculatedCorrectly(){
        Price price = new Price();
        price.setDutyFreeAmount(100.0f);
        price.setTaxRate(20.0f);
        CartPrice cartPrice = new CartPrice();
        cartPrice.setPrice(price);

        PriceCalculationService priceCalculationService = new PriceCalculationService(cartPrice);
        CartPrice calculationResult = priceCalculationService.calculate(0);
        Assertions.assertNotNull(calculationResult);
        Assertions.assertNotNull(calculationResult.getPrice());
        Assertions.assertEquals(100.0f, calculationResult.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(120.0f, calculationResult.getPrice().getTaxIncludedAmount());
        Assertions.assertEquals(20.0f, calculationResult.getPrice().getTaxRate());
    }

    @Test
    void whenThereIsOneDiscountAlterationWithFixedAmount_thenCalculationsMustBeDoneCorrectly(){
        Price price = new Price();
        price.setDutyFreeAmount(100.0f);
        price.setTaxRate(20.0f);
        CartPrice cartPrice = new CartPrice();
        cartPrice.setPrice(price);

        PriceAlteration alteration = new PriceAlteration();
        alteration.setAlterationType(AlterationType.DISCOUNT);

        Price alterationPrice = new Price();
        alterationPrice.setDutyFreeAmount(50.0f);
        alteration.setPrice(alterationPrice);

        cartPrice.setPriceAlteration(Arrays.asList(alteration));

        PriceCalculationService priceCalculationService = new PriceCalculationService(cartPrice);
        CartPrice calculationResult = priceCalculationService.calculate(0);
        Assertions.assertNotNull(calculationResult);
        Assertions.assertNotNull(calculationResult.getPrice());
        Assertions.assertEquals(50.0f, calculationResult.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(60.0f, calculationResult.getPrice().getTaxIncludedAmount());
        Assertions.assertEquals(20.0f, calculationResult.getPrice().getTaxRate());
    }

    @Test
    void whenThereAreMoreThanOneDiscountAlteration_thenCalculationsMustBeDoneCorrectly(){
        Price price = new Price();
        price.setDutyFreeAmount(100.0f);
        price.setTaxRate(20.0f);
        CartPrice cartPrice = new CartPrice();
        cartPrice.setPrice(price);

        PriceAlteration discountAlteration = new PriceAlteration();
        Price discountAlterationPrice = new Price();
        discountAlterationPrice.setPercentage(25.0f);
        discountAlteration.setPrice(discountAlterationPrice);
        discountAlteration.setPriority(1);
        discountAlteration.setAlterationType(AlterationType.DISCOUNT);

        PriceAlteration overrideAlteration = new PriceAlteration();
        Price overrideAlterationPrice = new Price();
        overrideAlterationPrice.setDutyFreeAmount(200.0f);
        overrideAlteration.setPrice(overrideAlterationPrice);
        overrideAlteration.setAlterationType(AlterationType.OVERRIDE);
        overrideAlteration.setPriority(2);

        PriceAlteration raiseAlteration = new PriceAlteration();
        Price raiseAlterationPrice = new Price();
        raiseAlterationPrice.setDutyFreeAmount(100.0f);
        raiseAlteration.setPrice(raiseAlterationPrice);
        raiseAlteration.setAlterationType(AlterationType.RAISE);
        raiseAlteration.setPriority(3);

        cartPrice.setPriceAlteration(Arrays.asList(discountAlteration, raiseAlteration, overrideAlteration));

        PriceCalculationService priceCalculationService = new PriceCalculationService(cartPrice);
        CartPrice calculationResult = priceCalculationService.calculate(2);
        Assertions.assertNotNull(calculationResult);
        Assertions.assertNotNull(calculationResult.getPrice());
        Assertions.assertEquals(600.0f, calculationResult.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(720.0f, calculationResult.getPrice().getTaxIncludedAmount());
        Assertions.assertEquals(20.0f, calculationResult.getPrice().getTaxRate());

    }

    @Test
    void whenCartPriceIsNull_thenNullMustReturn(){
        PriceCalculationService priceCalculationService = new PriceCalculationService(null);
        Assertions.assertNull(priceCalculationService.calculate(1));
    }
    @Test
    void whenPriceOfCartPriceIsNull_thenNullMustReturn(){
        PriceCalculationService priceCalculationService = new PriceCalculationService(new CartPrice());
        Assertions.assertNull(priceCalculationService.calculate(1));
    }


}
