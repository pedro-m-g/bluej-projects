package com.pedromg.bluej.shapes.demo;

import java.util.Set;

public class DemoNotFoundException extends RuntimeException {

  private final String demoName;
  private final Set<String> availableDemos;

  public DemoNotFoundException(String demoName, Set<String> availableDemos) {
    super(
        "Command not found: "
            + demoName
            + "; Available demos: "
            + String.join(", ", availableDemos));
    this.demoName = demoName;
    this.availableDemos = availableDemos;
  }

  public String demoName() {
    return demoName;
  }

  public Set<String> availableDemos() {
    return availableDemos;
  }
}
