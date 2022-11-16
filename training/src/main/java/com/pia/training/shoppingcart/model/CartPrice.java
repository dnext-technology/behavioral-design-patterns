package com.pia.training.shoppingcart.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartPrice {

    private String name = null;
    private Price price = null;
    private List<PriceAlteration> priceAlteration = null;

}

