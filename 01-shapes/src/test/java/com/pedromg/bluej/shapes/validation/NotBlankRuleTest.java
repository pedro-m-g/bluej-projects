package com.pedromg.bluej.shapes.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class NotBlankRuleTest {

  @ParameterizedTest
  @MethodSource("positiveArguments")
  void testValidatePositiveArguments(String text) {
    assertDoesNotThrow(() -> NotBlankRule
        .validate(text, "Oops!"));
  }

  static Stream<String> positiveArguments() {
    return Stream.of(
        "asdasdasd",
        "  asd asd asd ",
        "_________");
  }

  @ParameterizedTest
  @MethodSource("negativeArguments")
  void testValidateNegativeArguments(String text) {
    assertThrows(
        IllegalArgumentException.class,
        () -> NotBlankRule
            .validate(text, "Oops!"));
  }

  static Stream<String> negativeArguments() {
    return Stream.of(
        "",
        null,
        "         ");
  }

}
