package com.pedromg.bluej.shapes.validation;

public class MinArrayLengthRule {

  private MinArrayLengthRule() {
  }

  /**
   * Validates given {@code array} has at least {@code minValue} items.
   *
   * @param array    the array to evaluate
   * @param minValue the minimum items quantity
   * @param message  the error message if the validation fails
   */
  public static void validate(Object[] array, int minValue, String message) {
    if (array.length < minValue) {
      throw new IllegalArgumentException(message);
    }
  }

}
