package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

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

  @Test
  void shouldFailToConstructWithNullAction() {
    // Given
    String action = null;
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
    assertEquals(1, exception.violations().size());
    assertEquals(
        "action must not be null",
        exception.violations().get(0));
  }

}
