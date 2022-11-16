package com.pia.training.shoppingcart.chainofresponsibility;

import com.pia.training.shoppingcart.model.CartPrice;

public abstract class CalculationStep {

    protected CartPrice cartPrice;

    protected CalculationStep(CartPrice cartPrice){
        this.cartPrice = cartPrice;
    }

    public void calculateAll(){
        this.calculate();
        CalculationStep nextStep = getNextStep();
        if(nextStep != null){
            nextStep.calculateAll();
        }
    }

    public abstract void calculate();

    public abstract CalculationStep getNextStep();
}
