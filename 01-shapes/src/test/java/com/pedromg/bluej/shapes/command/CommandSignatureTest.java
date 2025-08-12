package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandSignatureTest {

  @ParameterizedTest
  @MethodSource("invalidArgsProvider")
  void shouldThrowWhenArgsAreInvalid(String action, List<String> params, Set<String> flags) {
    assertThrows(PreConditionsException.class, () -> new CommandSignature(action, params, flags));
  }

  static Stream<Arguments> invalidArgsProvider() {
    return Stream.of(
        Arguments.of((String) null, List.of(), Set.of()),
        Arguments.of("", List.of(), Set.of()),
        Arguments.of("   ", List.of(), Set.of()),
        Arguments.of("", null, Set.of()),
        Arguments.of("", List.of(), null));
  }

  @Test
  void shouldMatchValidCommandRequest() {
    CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of());
    CommandSignature signature = new CommandSignature("demo", List.of("name"), Set.of());
    boolean matches = signature.matches(request);
    assertTrue(matches);
  }

  @Test
  void shouldNotMatchInvalidCommandRequest() {
    CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of("verbose"));
    CommandSignature signature = new CommandSignature("demo", List.of("name"), Set.of());
    boolean matches = signature.matches(request);
    assertFalse(matches);
  }

  @Test
  void shouldConvertToString() {
    CommandSignature signature = new CommandSignature("demo", List.of("name"), Set.of("verbose"));
    String convertedString = signature.toString();
    assertEquals("demo <name> [--verbose]", convertedString);
  }
}
