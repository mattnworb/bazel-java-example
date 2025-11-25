package com.mattnworb.calculator;

public class Calculator {
  public int add(int... nums) {
    int sum = 0;
    for (int n : nums) {
      sum += n;
    }
    return sum;
  }
}
