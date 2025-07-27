package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CommandRequestTest {

  @Test
  void shouldConstructProperlyWithValidSetup() {
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

}
