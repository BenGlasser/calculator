package com.benglasser.calculator.operators;

import com.benglasser.calculator.operations.Operation;

/**
 * Created by bglasser on 9/13/15.
 */

public class Subtract extends Operation
{

  @Override
  public String getName()
  {
    return "Subtract";
  }

  @Override
  public int calculate(int num1, int num2)
  {
    return num1 - num2;
  }
}
