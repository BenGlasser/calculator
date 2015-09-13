package com.benglasser.calculator.app.operations;

/**
 * Created by bglasser on 9/13/15.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class OperationBinary implements Operation{
    final private int num1;
    final private int num2;
}
