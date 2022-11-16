package com.pia.training;

import com.pia.training.shoppingcart.calculation.PriceCalculationService;
import com.pia.training.shoppingcart.model.AlterationType;
import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.Price;
import com.pia.training.shoppingcart.model.PriceAlteration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceCalculationServiceTest {

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

        PriceCalculationService priceCalculationService = new PriceCalculationService(cartPrice);
        CartPrice calculationResult = priceCalculationService.calculate(0);
        Assertions.assertNotNull(calculationResult);
        Assertions.assertNotNull(calculationResult.getPrice());
        Assertions.assertEquals(100.0f, calculationResult.getPrice().getDutyFreeAmount());
        Assertions.assertEquals(120.0f, calculationResult.getPrice().getTaxIncludedAmount());
        Assertions.assertEquals(20.0f, calculationResult.getPrice().getTaxRate());
    }
}
