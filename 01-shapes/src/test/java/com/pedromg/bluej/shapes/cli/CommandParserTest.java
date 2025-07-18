package com.pedromg.bluej.shapes.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import static com.pedromg.bluej.shapes.CustomAssertions.assertCollectionEmpty;
import static com.pedromg.bluej.shapes.CustomAssertions.assertCollectionEquals;
import static com.pedromg.bluej.shapes.CustomAssertions.assertExceptionThrown;

class CommandParserTest {

  @Test
  void testParseHelpCommand() {
    // Given a command with parameters and options
    CommandParser parser = new CommandParser();
    String[] args = { "help" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created correctly
    assertEquals("help", request.action());
    assertCollectionEmpty(request.params());
    assertCollectionEmpty(request.flags());
  }

  @Test
  void testParseDemoCommand() {
    // Given a command with parameters and options
    CommandParser parser = new CommandParser();
    String[] args = { "demo", "circle" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created correctly
    assertEquals("demo", request.action());
    assertCollectionEquals(List.of("circle"), request.params());
    assertCollectionEmpty(request.flags());
  }

  @Test
  void testParseCommandWithFlags() {
    // Given a command with parameters and options
    CommandParser parser = new CommandParser();
    String[] args = { "demo", "square", "--verbose" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created correctly
    assertEquals("demo", request.action());
    assertCollectionEquals(List.of("square"), request.params());
    assertCollectionEquals(List.of("verbose"),request.flags());
  }

  @Test
  void testParseCommandWithFlagsDuplicated() {
    // Given a command with parameters and options
    CommandParser parser = new CommandParser();
    String[] args = { "demo", "square", "--verbose", "--verbose" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created correctly
    assertEquals("demo", request.action());
    assertCollectionEquals(List.of("square"), request.params());
    assertCollectionEquals(List.of("verbose"), request.flags());
  }

  @Test
  void testEmptyCommand() {
    assertExceptionThrown(
      IllegalArgumentException.class,
      () -> {
        // Given an empty command
        CommandParser parser = new CommandParser();
        String[] args = {};

        // When parsing the command, it should throw an exception
        parser.parse(args);
      });
  }

}
