package com.pia.training.shoppingcart.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Price {
    private Float percentage = null;
    private Float taxRate = null;
    private Float dutyFreeAmount = null;
    private Float taxIncludedAmount = null;
}

