package com.pedromg.bluej.shapes.validation;

public class NotBlankRule {

  private NotBlankRule() {
  }

  /**
   * Validates the given {@code text} is not blank.
   * That includes null values, empty strings and text
   * containing only spaces.
   *
   * @param text    the text to be evaluated
   * @param message the error message if the validation fails
   */
  public static void validate(String text, String message) {
    if (text == null || text.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

}
