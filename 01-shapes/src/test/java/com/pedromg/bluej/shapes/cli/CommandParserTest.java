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
    // Given a command "01-shapes.jar help"
    CommandParser parser = new CommandParser();
    String[] args = { "help" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created with action=help, params=[], flags=[]
    assertEquals("help", request.action());
    assertCollectionEmpty(request.params());
    assertCollectionEmpty(request.flags());
  }

  @Test
  void testParseDemoCommand() {
    // Given a command "01-shapes.jar demo circle"
    CommandParser parser = new CommandParser();
    String[] args = { "demo", "circle" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created with action=demo, params=[circle], flags=[]
    assertEquals("demo", request.action());
    assertCollectionEquals(List.of("circle"), request.params());
    assertCollectionEmpty(request.flags());
  }

  @Test
  void testParseCommandWithFlags() {
    // Given a command "01-shapes.jar demo square --verbose"
    CommandParser parser = new CommandParser();
    String[] args = { "demo", "square", "--verbose" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created with action=demo, params=[square], flags=[verbose]
    assertEquals("demo", request.action());
    assertCollectionEquals(List.of("square"), request.params());
    assertCollectionEquals(List.of("verbose"),request.flags());
  }

  @Test
  void testParseCommandWithFlagsDuplicated() {
    // Given a command "01-shapes.jar demo square --verbose --verbose"
    CommandParser parser = new CommandParser();
    String[] args = { "demo", "square", "--verbose", "--verbose" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created with action=demo, params=[square], flags=[verbose]
    assertEquals("demo", request.action());
    assertCollectionEquals(List.of("square"), request.params());
    assertCollectionEquals(List.of("verbose"), request.flags());
  }

  @Test
  void testParseCommandWithActionInParameters() {
    // Given a command "01-shapes.jar demo triangle demo"
    CommandParser parser = new CommandParser();
    String[] args = new String[] { "demo", "triangle", "demo" };

    // When parsing the command
    CommandRequest request = parser.parse(args);

    // Then the request should be created with action=demo, params=[triangle, demo], flags=[]
    assertEquals("demo", request.action());
    assertCollectionEquals(
      List.of("triangle", "demo"),
      request.params());
    assertCollectionEmpty(request.flags());
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
