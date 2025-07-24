package com.pedromg.bluej.shapes.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositiveNumberRuleTest {

  @ParameterizedTest
  @CsvSource({
      "123",
      "234234",
      "1"
  })
  void testValidatePositiveArguments(int number) {
    assertDoesNotThrow(() -> PositiveNumberRule
        .validate(number, "Oops!"));
  }

  @ParameterizedTest
  @CsvSource({
      "0",
      "-1",
      "-123456"
  })
  void testValidateNegativeArguments(int number) {
    assertThrows(
        IllegalArgumentException.class,
        () -> PositiveNumberRule
            .validate(number, "Oops!"));
  }

}
