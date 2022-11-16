package com.pia.training.shoppingcart.calculation;

import com.pia.training.shoppingcart.model.AlterationType;
import com.pia.training.shoppingcart.model.CartPrice;
import com.pia.training.shoppingcart.model.Price;
import com.pia.training.shoppingcart.model.PriceAlteration;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

public class PriceCalculationService {

    private CartPrice cartPrice;

    public PriceCalculationService(CartPrice cartPrice) {
        this.cartPrice = cartPrice;
    }

    public CartPrice calculate(Integer quantity){
        if(cartPrice.getPrice().getDutyFreeAmount() == null){
            cartPrice.getPrice().setDutyFreeAmount(
                    BigDecimal.valueOf(cartPrice.getPrice().getTaxIncludedAmount() / (1.0F + cartPrice.getPrice().getTaxRate() / 100.0F)).setScale(2, RoundingMode.HALF_UP)
                            .floatValue());
        }

        if(cartPrice.getPriceAlteration() != null && !cartPrice.getPriceAlteration().isEmpty()){
            cartPrice.getPriceAlteration().stream().sorted(Comparator.comparing(PriceAlteration::getPriority)).forEach(priceAlteration -> calculatePriceAlteration(priceAlteration));
        }

        if(quantity != null && quantity > 0){
            cartPrice.getPrice().setDutyFreeAmount(cartPrice.getPrice().getDutyFreeAmount() * quantity);
        }


        Float taxRate = cartPrice.getPrice().getTaxRate() == null ? 0.0F : cartPrice.getPrice().getTaxRate();
        cartPrice.getPrice().setTaxIncludedAmount(
                BigDecimal.valueOf(cartPrice.getPrice().getDutyFreeAmount()
                                *
                                (1.0F + taxRate / 100.0F)).setScale(2, RoundingMode.HALF_UP)
                        .floatValue());

        return cartPrice;
    }



    public void calculatePriceAlteration(PriceAlteration priceAlteration){
        if(priceAlteration.getAlterationType() == AlterationType.DISCOUNT){
            applyDiscountAlteration(priceAlteration);
        } else if (priceAlteration.getAlterationType() == AlterationType.RAISE){
            applyRaiseAlteration(priceAlteration);
        } else if (priceAlteration.getAlterationType() == AlterationType.OVERRIDE) {
            applyOverrideAlteration(priceAlteration);
        }
    }

    public void applyOverrideAlteration(PriceAlteration priceAlteration){
        cartPrice.getPrice().setDutyFreeAmount(priceAlteration.getPrice().getDutyFreeAmount());
    }


    public void applyDiscountAlteration(PriceAlteration priceAlteration){
        if (priceAlteration.getPrice().getPercentage() != null) {
            cartPrice.getPrice().setDutyFreeAmount(
                    BigDecimal.valueOf(cartPrice.getPrice().getDutyFreeAmount()
                                    *
                                    (1.0F - priceAlteration.getPrice().getPercentage() / 100.0F))
                            .setScale(2, RoundingMode.HALF_UP)
                            .floatValue());
        } else {
            cartPrice.getPrice().setDutyFreeAmount(cartPrice.getPrice().getDutyFreeAmount() - priceAlteration.getPrice().getDutyFreeAmount());
        }
    }

    public void applyRaiseAlteration(PriceAlteration priceAlteration){
        if (priceAlteration.getPrice().getPercentage() != null) {
            cartPrice.getPrice().setDutyFreeAmount(
                    BigDecimal.valueOf(cartPrice.getPrice().getDutyFreeAmount()
                                    *
                                    (1.0F + priceAlteration.getPrice().getPercentage() / 100.0F))
                            .setScale(2, RoundingMode.HALF_UP)
                            .floatValue());
        } else {
            cartPrice.getPrice().setDutyFreeAmount(cartPrice.getPrice().getDutyFreeAmount() + priceAlteration.getPrice().getDutyFreeAmount());
        }
    }
}
