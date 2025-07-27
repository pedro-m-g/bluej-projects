package com.pedromg.bluej.shapes.preconditions;

public class PreConditions {

  private PreConditions() {
    /* no-op */
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * doesn't throw an Exception.
   *
   * @param preCondition the code that might throw an exception
   * @param message      the error message
   *
   * @throws PreConditionsException if the {@code preCondition} throws
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
   * @throws PreConditionsException if the {@code preCondition} is false
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
   * @throws PreConditionsException if the {@code preCondition} is false
   *
   * @return this object for method chaining
   */
  public static PreConditions requireNot(
      boolean preCondition,
      String message) {
    return require(!preCondition, message);
  }

  /**
   * Adds a precondition, which is valid only if {@code target} is not null.
   *
   * @param target  the object to evaluate
   * @param message the error message
   *
   * @throws PreConditionsException if the {@code target} is null
   *
   * @return this object for method chaining
   */
  public static PreConditions requireNonNull(
      Object target,
      String message) {
    return require(target != null, message);
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * doesn't throw an Exception.
   *
   * @param preCondition the code that might throw an exception
   * @param message      the error message
   *
   * @throws PreConditionsException if the {@code preCondition} throws
   *
   * @return this object, for method chaining
   */
  public PreConditions and(Runnable preCondition, String message) {
    try {
      preCondition.run();
      return this;
    } catch (Exception ex) {
      throw new PreConditionsException(message, ex);
    }
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * evaluates to true.
   *
   * @param preCondition the assertion to test
   * @param message      the error message
   *
   * @throws PreConditionsException if the {@code preCondition} is false
   *
   * @return this object for method chaining
   */
  public PreConditions and(boolean preCondition, String message) {
    if (!preCondition) {
      throw new PreConditionsException(
          message, new IllegalArgumentException(message));
    }
    return this;
  }

  /**
   * Adds a precondition, which is valid only if {@code preCondition}
   * evaluates to false.
   *
   * @param preCondition the assertion to test
   * @param message      the error message
   *
   * @throws PreConditionsException if the {@code preCondition} is true
   *
   * @return this object for method chaining
   */
  public PreConditions andNot(boolean preCondition, String message) {
    return and(!preCondition, message);
  }

  /**
   * Adds a precondition, which is valid only if {@code target} is not null.
   *
   * @param target  the object to evaluate
   * @param message the error message
   *
   * @throws PreConditionsException if the {@code preCondition} is null
   *
   * @return this object for method chaining
   */
  public PreConditions andNonNull(Object target, String message) {
    return and(target != null, message);
  }

}
