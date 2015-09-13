package com.benglasser;

public class Main
{

  public static void main(String[] args) throws Exception
  {
      if (args.length != 2)
      {
          System.out.println("requires exactly two numbers");
          System.exit(1);
      }

      final int num1 = parseInt(args[0]);
      final int num2 = parseInt(args[1]);
      final int result = add(num1, num2);
      
      System.out.println(
          String.format("Result: %d", result));

  }

  private static int add(int num1, int num2)
  {
    return num1 + num2;
  }

  private static int parseInt(String num) throws Exception
  {

    try
    {
      return Integer.parseInt(num);
    }
    catch (Exception e)
    {
      System.out.println("argument was not an integer.");
      throw new Exception(e.getMessage());
    }

  }

}
