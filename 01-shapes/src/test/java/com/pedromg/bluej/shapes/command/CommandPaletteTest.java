package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pedromg.bluej.shapes.mocks.MockCommandHandler;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import org.junit.jupiter.api.Test;

class CommandPaletteTest {

  @Test
  void shouldThrowWhenConstructingWithNullUsageMessage() {
    assertThrows(PreConditionsException.class, () -> new CommandPalette(null));
  }

  @Test
  void shouldThrowWhenFindingOnEmptyPalette() {
    // Given
    CommandPalette palette = new CommandPalette();

    // Then
    assertThrows(CommandNotFoundException.class, () -> palette.find("demo"));
  }

  @Test
  void shouldNotHaveCommandOnPaletteEmpty() {
    // Given
    CommandPalette palette = new CommandPalette();

    // When
    boolean hasCommand = palette.hasCommand("demo");

    // Then
    assertFalse(hasCommand);
  }

  @Test
  void shouldThrowWhenFindingUnknownCommand() {
    // Given
    MockCommandHandler mockCommandHandler = new MockCommandHandler();
    CommandPalette palette = new CommandPalette().add("mock", mockCommandHandler);

    // Then
    assertThrows(CommandNotFoundException.class, () -> palette.find("demo"));
  }

  @Test
  void shouldThrowWhenFindingNullCommand() {
    // Given
    MockCommandHandler mockCommandHandler = new MockCommandHandler();
    CommandPalette palette = new CommandPalette().add("mock", mockCommandHandler);

    // Then
    assertThrows(PreConditionsException.class, () -> palette.find(null));
  }

  @Test
  void shouldFindKnownCommandSuccessfully() {
    // Given
    MockCommandHandler mockCommandHandler = new MockCommandHandler();
    CommandPalette palette = new CommandPalette().add("mock", mockCommandHandler);

    // When
    CommandHandler actualCommandHandler = palette.find("mock");

    // Then
    assertEquals(mockCommandHandler, actualCommandHandler);
  }
}
