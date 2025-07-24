package com.pedromg.bluej.shapes.validation;

public class PositiveNumberRule {

  private PositiveNumberRule() {
  }

  /**
   * Validates the given {@code number} is positive, that is,
   * greater than zero.
   *
   * @param number  the number to be evaluated
   * @param message the error message if the validation fails
   */
  public static void validate(int number, String message) {
    if (number <= 0) {
      throw new IllegalArgumentException(message);
    }
  }

}
