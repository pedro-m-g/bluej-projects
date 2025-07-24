package com.pedromg.bluej.shapes.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CollectionSizeRuleTest {

  @ParameterizedTest
  @MethodSource("positiveArguments")
  void testValidatePositiveArguments(List<?> list, int size) {
    assertDoesNotThrow(() -> CollectionSizeRule
        .validate(list, size, "This should not happen"));
  }

  static Stream<Arguments> positiveArguments() {
    return Stream.of(
        Arguments.of(List.of(), 0),
        Arguments.of(List.of("1", "2"), 2),
        Arguments.of(List.of(1, 2, 3), 3));
  }

  @ParameterizedTest
  @MethodSource("negativeArguments")
  void testValidateNegativeArguments(List<?> list, int size) {
    assertThrows(
        IllegalArgumentException.class,
        () -> CollectionSizeRule
            .validate(list, size, "Oops!"));
  }

  static Stream<Arguments> negativeArguments() {
    return Stream.of(
        Arguments.of(List.of(), 1),
        Arguments.of(List.of("1", "2"), 0),
        Arguments.of(List.of(1, 2, 3), 2));
  }

}
