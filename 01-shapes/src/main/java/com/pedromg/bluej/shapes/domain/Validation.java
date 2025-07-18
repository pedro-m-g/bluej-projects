package com.pedromg.bluej.shapes.domain;

public class Validation {

  /**
   * Private constructor to prevent instantiation of the {@code Validation} utility class.
   */
  private Validation() {
    // Prevent instantiation
  }

  /**
   * Validates that the specified integer is greater than zero.
   *
   * @param number the integer value to check
   * @param name the name of the parameter, used in the exception message if validation fails
   * @throws IllegalArgumentException if {@code number} is less than or equal to zero
   */
  public static void positiveNumber(int number, String name) {
    if (number <= 0) {
      throw new IllegalArgumentException(name + " must be greater than zero");
    }
  }

}
