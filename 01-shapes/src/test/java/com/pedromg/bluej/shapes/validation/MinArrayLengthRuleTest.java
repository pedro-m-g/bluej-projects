package com.pedromg.bluej.shapes.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MinArrayLengthRuleTest {

  @ParameterizedTest
  @MethodSource("positiveArguments")
  void testValidatePositiveArguments(Object[] array, int minLength) {
    assertDoesNotThrow(() -> MinArrayLengthRule
        .validate(array, minLength, "This should not happen!"));
  }

  static Stream<Arguments> positiveArguments() {
    return Stream.of(
        Arguments.of((Object) new String[] { "" }, 1),
        Arguments.of((Object) new Integer[] { 1, 2, 3 }, 2),
        Arguments.of((Object) new Boolean[] { false, true, false }, 3));
  }

  @ParameterizedTest
  @MethodSource("negativeArguments")
  void testValidateNegativeArguments(Object[] array, int minLength) {
    assertThrows(
        IllegalArgumentException.class,
        () -> MinArrayLengthRule
            .validate(array, minLength, "This should not happen!"));
  }

  static Stream<Arguments> negativeArguments() {
    return Stream.of(
        Arguments.of((Object) new String[] { "" }, 2),
        Arguments.of((Object) new Integer[] { 1, 2, 3 }, 4),
        Arguments.of((Object) new Boolean[] { false, true, false }, 10));
  }

}
