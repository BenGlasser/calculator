package com.benglasser.calculator.app.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by bglasser on 9/13/15.
 */

@Getter
@AllArgsConstructor
public abstract class OperationTrinary implements Operation{
    final private int num1;
    final private int num2;
    final private int num3;
}
