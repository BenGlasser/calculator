package com.benglasser.calculator.app.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by bglasser on 9/13/15.
 */

@Getter
@AllArgsConstructor
public abstract class OperationUnary implements Operation{
    private int num1;
}
