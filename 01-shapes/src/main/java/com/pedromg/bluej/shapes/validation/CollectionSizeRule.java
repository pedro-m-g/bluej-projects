package com.pedromg.bluej.shapes.validation;

import java.util.Collection;

public class CollectionSizeRule {

  private CollectionSizeRule() {
  }

  /**
   * Validates given {@code collection} has the exact {@code expectedSize}.
   *
   * @param collection   the collection to be evaluated
   * @param expectedSize the expected size for the collection
   * @param message      the error message if validation fails
   */
  public static void validate(Collection<?> collection, int expectedSize, String message) {
    if (collection.size() != expectedSize) {
      throw new IllegalArgumentException(message);
    }
  }

}
