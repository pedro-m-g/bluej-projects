package com.pedromg.bluej.shapes.domain;

import static com.pedromg.bluej.shapes.CustomAssertions.assertExceptionThrown;

import org.junit.jupiter.api.Test;

class ValidationTest {

  @Test
  void testAtLeast() {
    assertExceptionThrown(
      IllegalArgumentException.class,
      () -> Validation.atLeast(1, 10, "value"));

    // This should not trigger any exceptions
    Validation.atLeast(10, 1, "value");
  }

  @Test
  void testExactly() {
    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.exactly(10, 20, "value"));

    // This should not trigger any exceptions
    Validation.exactly(10, 10, "value");
  }

  @Test
  void testNotBlank() {
    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.notBlank(null, "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.notBlank("", "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.notBlank("         ", "value"));

    // This should not trigger any exceptions
    Validation.notBlank("Not a blank value", "value");
  }

  @Test
  void testPositiveNumber() {
    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.positiveNumber(-1, "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.positiveNumber(0, "value"));

    // This should not trigger any exceptions
    Validation.positiveNumber(1, "value");
  }
}
