package com.pedromg.bluej.shapes.preconditions;

import java.util.LinkedList;
import java.util.List;

public class PreConditions {

  private final List<Runnable> requirements;
  private final List<String> messages;

  private PreConditions() {
    requirements = new LinkedList<>();
    messages = new LinkedList<>();
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * doesn't throw an Exception.
   *
   * @param preCondition the code that might throw an exception
   * @param message      the error message
   *
   * @return this object, for method chaining
   */
  public static PreConditions require(
      Runnable preCondition,
      String message) {
    return new PreConditions().and(preCondition, message);
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * evaluates to true.
   *
   * @param preCondition the assertion to test
   * @param message      the error message
   *
   * @return this object for method chaining
   */
  public static PreConditions require(
      boolean preCondition,
      String message) {
    return new PreConditions().and(preCondition, message);
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * evaluates to false.
   *
   * @param preCondition the assertion to test
   * @param message      the error message
   *
   * @return this object for method chaining
   */
  public static PreConditions requireNot(
      boolean preCondition,
      String message) {
    return require(!preCondition, message);
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * doesn't throw an Exception.
   *
   * @param preCondition the code that might throw an exception
   * @param message      the error message
   *
   * @return this object, for method chaining
   */
  public PreConditions and(
      Runnable preCondition,
      String message) {
    requirements.add(preCondition);
    messages.add(message);
    return this;
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * evaluates to true.
   *
   * @param preCondition the assertion to test
   * @param message      the error message
   *
   * @return this object for method chaining
   */
  public PreConditions and(
      boolean preCondition,
      String message) {
    if (preCondition) {
      requirements.add(() -> {
        throw new IllegalArgumentException(message);
      });
    } else {
      requirements.add(() -> {
        /* Do nothing */
      });
    }
    messages.add(message);
    return this;
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * evaluates to false.
   *
   * @param preCondition the assertion to test
   * @param message      the error message
   *
   * @return this object for method chaining
   */
  public PreConditions andNot(
      boolean preCondition,
      String message) {
    return and(!preCondition, message);
  }

  /**
   * Runs the preConditions defined in this object.
   */
  public void check() {
    if (requirements.isEmpty()) {
      throw new PreConditionsException(
          List.of("No requirements defined"));
    }
    List<String> violations = new LinkedList<>();
    for (int index = 0; index < requirements.size(); index++) {
      try {
        requirements.get(index).run();
      } catch (Exception exception) {
        violations.add(messages.get(index));
      }
    }
    if (!violations.isEmpty()) {
      throw new PreConditionsException(violations);
    }
  }

}
