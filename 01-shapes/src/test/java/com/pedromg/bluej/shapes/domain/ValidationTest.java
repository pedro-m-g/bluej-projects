package com.pedromg.bluej.shapes.domain;

import static com.pedromg.bluej.shapes.CustomAssertions.assertExceptionThrown;

import org.junit.jupiter.api.Test;

class ValidationTest {

  @Test
  void testAtLeast() {
    // Positive test
    assertExceptionThrown(
      IllegalArgumentException.class,
      () -> Validation.atLeast(1, 10, "value"));

    // Negative test
    Validation.atLeast(10, 1, "value");
  }

  @Test
  void testExactly() {
    // Positive test
    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.exactly(10, 20, "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.exactly(10, 20, "value"));

    // Negative test
    Validation.exactly(10, 10, "value");

    Validation.exactly("10", "10", "value");
  }

  @Test
  void testNotBlank() {
    // Positive test
    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.notBlank(null, "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.notBlank("", "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.notBlank("         ", "value"));

    // Negative test
    Validation.notBlank("Not a blank value", "value");
  }

  @Test
  void testPositiveNumber() {
    // Positive test
    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.positiveNumber(-1, "value"));

    assertExceptionThrown(
        IllegalArgumentException.class,
        () -> Validation.positiveNumber(0, "value"));

    // Negative test
    Validation.positiveNumber(1, "value");
  }
}
