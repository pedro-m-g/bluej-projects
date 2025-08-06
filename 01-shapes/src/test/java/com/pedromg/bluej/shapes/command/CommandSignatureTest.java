package com.pedromg.bluej.shapes.command;

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

  @Test
  void shouldParseSimpleCommand() {
    CommandSignature signature = new CommandSignature("mock");
    CommandRequest request = new CommandRequest("mock", List.of(), Set.of());
    assertTrue(signature.canHandle(request));
  }

  @Test
  void shouldParseParams() {
    CommandSignature signature = new CommandSignature("demo <name>");
    CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of());
    assertTrue(signature.canHandle(request));
  }

  @Test
  void shouldParseFlags() {
    CommandSignature signature = new CommandSignature("demo <name> [--verbose]");
    CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of("verbose"));
    assertTrue(signature.canHandle(request));
  }

  @Test
  void shouldNotRecognizedUnmatchedParams() {
    CommandSignature signature = new CommandSignature("demo <name>");
    CommandRequest request = new CommandRequest("demo", List.of("circle", "unknown"), Set.of());
    assertFalse(signature.canHandle(request));
  }

  @Test
  void shouldNotRecognizedUnmatchedFlags() {
    CommandSignature signature = new CommandSignature("demo <name>");
    CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of("verbose"));
    assertFalse(signature.canHandle(request));
  }

  @ParameterizedTest
  @MethodSource("blankSignaturesProvider")
  void shouldThrowWhenSignatureIsBlank(String signature) {
    assertThrows(PreConditionsException.class, () -> new CommandSignature(signature));
  }

  static Stream<Arguments> blankSignaturesProvider() {
    return Stream.of(Arguments.of(""), Arguments.of("   "), Arguments.of((String) null));
  }

  @ParameterizedTest
  @MethodSource("notPareableSignaturesProvider")
  void shouldThrowWhenSignatureIsNotParseable(String signature) {
    assertThrows(CommandSignatureParseException.class, () -> new CommandSignature(signature));
  }

  static Stream<Arguments> notPareableSignaturesProvider() {
    return Stream.of(
        Arguments.of("demo demo"),
        Arguments.of("demo <name"),
        Arguments.of("demo name>"),
        Arguments.of("demo <name> [--verbose"),
        Arguments.of("demo <name> --verbose]"),
        Arguments.of("demo <name> [verbose]"));
  }
}
