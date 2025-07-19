package com.pedromg.bluej.shapes.domain;

import java.util.Objects;

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

  /**
   * Validates that the given string is not blank.
   *
   * @param text the string to validate
   * @param name the name of the parameter for error messages
   *
   * @throws IllegalArgumentException if the string is blank
   */
  public static void notBlank(String text, String name) {
    if (text == null || text.trim().isEmpty()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }

  /**
   * Validates that the given number is at least a specified minimum value.
   *
   * @param number the number to validate
   * @param min the minimum value
   * @param name the name of the parameter for error messages
   *
   * @throws IllegalArgumentException if the number is less than the minimum value
   */
  public static void atLeast(int number, int min, String name) {
    if (number < min) {
      throw new IllegalArgumentException(name + " must be at least " + min);
    }
  }

  /**
   * Validates that the given value is exactly the expected
   *
   * @param value the value to evaluate
   * @param expected the expected value
   * @param name name of the parameter, for error formatting
   */
  public static <T> void exactly(T value, T expected, String name) {
    if (!Objects.equals(value, expected)) {
      throw new IllegalArgumentException(name + " must be exactly " + expected);
    }
  }

}
