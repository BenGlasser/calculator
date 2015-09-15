package com.benglasser.calculator.operations;

/**
 * Created by bglasser on 9/13/15.
 */
public abstract class Operation
{
  public abstract String getName();

  public abstract int calculate(int num1, int num2);
}
