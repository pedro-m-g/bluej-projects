package com.pedromg.bluej.shapes.domain;

public class Validation {

  private Validation() {
    // Prevent instantiation
  }

  /**
   * Validates that the given number is greater than zero.
   *
   * @param number the number to validate
   * @param name the name of the parameter for error messages
   *
   * @throws IllegalArgumentException if the number is not greater than zero
   */
  public static void positiveNumber(int number, String name) {
    if (number <= 0) {
      throw new IllegalArgumentException(name + " must be greater than zero");
    }
  }

}
