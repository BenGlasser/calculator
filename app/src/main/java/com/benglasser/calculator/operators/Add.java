package com.benglasser.calculator.operators;

import com.benglasser.calculator.operations.Operation;

/**
 * Created by bglasser on 9/13/15.
 */

public class Add extends Operation
{

  @Override
  public String getName()
  {
    return "Add";
  }

  @Override
  public int calculate(int num1, int num2)
  {
    return num1 + num2;
  }
}
