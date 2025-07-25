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
        + String.join(",", violations));
    this.violations = violations;
  }

  /**
   * Get preconditions violations for this exception.
   *
   * @return violations
   */
  public List<String> violations() {
    return violations;
  }

}
