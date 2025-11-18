package com.mattnworb.helloworld;

// placeholder so there is at least one test for CI
public class SimpleTest {
  public static void main(String[] args) {
    if (2 + 2 != 4) {
      throw new RuntimeException("math is broken");
    }
  }
}
