package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

class CommandRequestTest {

  @Test
  void shouldConstructWithValidSetup() {
    // Given
    String action = "demo";
    List<String> params = List.of("circle");
    Set<String> flags = Set.of("verbose");

    // When
    CommandRequest request = new CommandRequest(
        action,
        params,
        flags);

    // Then
    assertEquals(action, request.action());
    assertEquals(params, request.params());
    assertEquals(flags, request.flags());
  }

  @Test
  void shouldConstructWithEmptyParams() {
    // Given
    String action = "demo";
    List<String> params = List.of();
    Set<String> flags = Set.of("verbose");

    // When
    CommandRequest request = new CommandRequest(
        action,
        params,
        flags);

    // Then
    assertEquals(action, request.action());
    assertEquals(params, request.params());
    assertEquals(flags, request.flags());
  }

  @Test
  void shouldConstructWithEmptyFlags() {
    // Given
    String action = "demo";
    List<String> params = List.of("circle");
    Set<String> flags = Set.of();

    // When
    CommandRequest request = new CommandRequest(
        action,
        params,
        flags);

    // Then
    assertEquals(action, request.action());
    assertEquals(params, request.params());
    assertEquals(flags, request.flags());
  }

  @Test
  void shouldConstructWithEmptyParamsAndFlags() {
    // Given
    String action = "demo";
    List<String> params = List.of();
    Set<String> flags = Set.of();

    // When
    CommandRequest request = new CommandRequest(
        action,
        params,
        flags);

    // Then
    assertEquals(action, request.action());
    assertEquals(params, request.params());
    assertEquals(flags, request.flags());
  }

  @ParameterizedTest
  @MethodSource("invalidActionProvider")
  void shouldFailToConstructWithInvalidAction(
      String action, String expectedViolation) {
    // Given
    List<String> params = List.of();
    Set<String> flags = Set.of("verbose");

    // When
    PreConditionsException exception = assertThrows(
        PreConditionsException.class,
        () -> new CommandRequest(
            action,
            params,
            flags));

    // Then
    assertEquals(expectedViolation, exception.getMessage());
  }

  static Stream<Arguments> invalidActionProvider() {
    return Stream.of(
        Arguments.of(null, "action must not be null"),
        Arguments.of("", "action must not be blank"),
        Arguments.of("        ", "action must not be blank"));
  }

}
