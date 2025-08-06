package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommandRequestTest {

  @Test
  void shouldConstructWithValidSetup() {
    // Given
    String action = "demo";
    List<String> params = List.of("circle");
    Set<String> flags = Set.of("verbose");

    // When
    CommandRequest request = new CommandRequest(action, params, flags);

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
    CommandRequest request = new CommandRequest(action, params, flags);

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
    CommandRequest request = new CommandRequest(action, params, flags);

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
    CommandRequest request = new CommandRequest(action, params, flags);

    // Then
    assertEquals(action, request.action());
    assertEquals(params, request.params());
    assertEquals(flags, request.flags());
  }

  @ParameterizedTest
  @MethodSource("invalidActionProvider")
  void shouldFailToConstructWithInvalidAction(String action, String expectedViolation) {
    // Given
    List<String> params = List.of();
    Set<String> flags = Set.of("verbose");

    // When
    PreConditionsException exception =
        assertThrows(PreConditionsException.class, () -> new CommandRequest(action, params, flags));

    // Then
    assertEquals(expectedViolation, exception.getMessage());
  }

  static Stream<Arguments> invalidActionProvider() {
    return Stream.of(
        Arguments.of(null, "action must not be blank"),
        Arguments.of("", "action must not be blank"),
        Arguments.of("        ", "action must not be blank"));
  }

  @Test
  void shouldFailToConstructWithNullParams() {
    // Given
    String action = "demo";
    List<String> params = null;
    Set<String> flags = Set.of("verbose");

    // When
    PreConditionsException exception =
        assertThrows(PreConditionsException.class, () -> new CommandRequest(action, params, flags));

    // Then
    assertEquals("params must not be null", exception.getMessage());
  }

  @Test
  void shouldFailToConstructWithNullFlags() {
    // Given
    String action = "demo";
    List<String> params = List.of();
    Set<String> flags = null;

    // When
    PreConditionsException exception =
        assertThrows(PreConditionsException.class, () -> new CommandRequest(action, params, flags));

    // Then
    assertEquals("flags must not be null", exception.getMessage());
  }

  @ParameterizedTest
  @MethodSource("flagsProvider")
  void shouldDetectFlag(Set<String> flags, String flagName, boolean expectedResult) {
    // Given
    CommandRequest commandRequest = new CommandRequest("demo", List.of("circle"), flags);

    // When
    boolean actualResult = commandRequest.hasFlag(flagName);

    // Then
    assertEquals(expectedResult, actualResult);
  }

  @SuppressWarnings("unused")
  static Stream<Arguments> flagsProvider() {
    return Stream.of(
        Arguments.of(Set.of("verbose"), "verbose", true),
        Arguments.of(Set.of(), "verbose", false),
        Arguments.of(Set.of("debug"), "verbose", false));
  }
}
