package com.benglasser.calculator.app.operators;

import com.benglasser.calculator.app.operations.OperationBinary;

/**
 * Created by bglasser on 9/13/15.
 */

public class Divide extends OperationBinary{

    public Divide(int num1, int num2) {
        super(num1, num2);
    }

    @Override
    public int calculate() {
        return getNum1() / getNum2();
    }
}
