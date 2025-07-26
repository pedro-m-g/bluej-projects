package com.pedromg.bluej.shapes.preconditions;

import java.util.List;

public class PreConditionsException extends IllegalArgumentException {

  private final List<String> violations;

  /**
   * Creates a new PreConditionsException.
   *
   * @param violations the list of violations for preconditions failed
   */
  public PreConditionsException(List<String> violations) {
    super("Preconditions failed: "
        + String.join(
            ",",
            violations == null ? List.of() : violations));
    if (violations == null || violations.isEmpty()) {
      throw new IllegalArgumentException(
          "Violations list must not be null or empty");
    }
    this.violations = List.copyOf(violations);
  }

  /**
   * Get preconditions violations for this exception.
   *
   * @return violations
   */
  public List<String> violations() {
    return List.copyOf(violations);
  }

}
