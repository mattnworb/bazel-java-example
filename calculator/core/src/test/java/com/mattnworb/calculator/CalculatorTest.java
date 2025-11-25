package com.mattnworb.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorTest {

  @Test
  public void testAddSingleNumber() {
    Calculator calculator = new Calculator();
    assertEquals(5, calculator.add(5));
  }

  @Test
  public void testAddTwoNumbers() {
    Calculator calculator = new Calculator();
    assertEquals(8, calculator.add(3, 5));
  }

  @Test
  public void testAddMultipleNumbers() {
    Calculator calculator = new Calculator();
    assertEquals(15, calculator.add(1, 2, 3, 4, 5));
  }

  @Test
  public void testAddNoNumbers() {
    Calculator calculator = new Calculator();
    assertEquals(0, calculator.add());
  }

  @Test
  public void testAddNegativeNumbers() {
    Calculator calculator = new Calculator();
    assertEquals(-5, calculator.add(-2, -3));
  }

  @Test
  public void testAddMixedNumbers() {
    Calculator calculator = new Calculator();
    assertEquals(5, calculator.add(10, -5));
  }
}
