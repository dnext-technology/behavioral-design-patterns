package com.pia.training.shoppingcart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceAlteration  {
    private String name = null;
    private AlterationType alterationType = null;
    private Integer priority = null;
    private Price price = null;
}

