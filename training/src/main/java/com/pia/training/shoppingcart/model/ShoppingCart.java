package com.pia.training.shoppingcart.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingCart {
    private List<CartItem> cartItems;
    private CartPrice cartTotalPrice;
}
