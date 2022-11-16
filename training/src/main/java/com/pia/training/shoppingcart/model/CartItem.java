package com.pia.training.shoppingcart.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartItem {
    private Integer quantity = 1;
    private List<CartPrice> itemPrice = null;
}

