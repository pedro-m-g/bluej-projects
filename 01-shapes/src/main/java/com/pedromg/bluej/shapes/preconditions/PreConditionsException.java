package com.pedromg.bluej.shapes.preconditions;

import java.util.List;

public class PreConditionsException extends IllegalArgumentException {

  private final List<String> violations;

  public PreConditionsException(List<String> violations) {
    this.violations = violations;
  }

  public List<String> violations() {
    return violations;
  }

}
