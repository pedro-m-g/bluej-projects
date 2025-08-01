package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pedromg.bluej.shapes.mocks.MockCommandHandler;
import com.pedromg.bluej.shapes.mocks.MockCommandPalette;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CLICommandHandlerTest {

  @Test
  void shouldExecuteCommandSuccessfully() {
    // Given
    MockCommandHandler mockCommandHandler = new MockCommandHandler();
    CommandPalette palette = new CommandPalette().add("mock", mockCommandHandler);
    CLICommandHandler handler = new CLICommandHandler(palette);

    // When
    handler.handle(new CommandRequest("mock", List.of(), Set.of()));

    // Then
    assertEquals(1, mockCommandHandler.numCalls());
  }

  @Test
  void shouldRejectNullCommandPalette() {
    assertThrows(PreConditionsException.class, () -> new CLICommandHandler(null));
  }

  @Test
  void shouldCallHelpWhenCommandIsUnknown() {
    // Given
    MockCommandPalette palette = new MockCommandPalette();
    CLICommandHandler handler = new CLICommandHandler(palette);

    // When
    handler.handle(new CommandRequest("mock", List.of(), Set.of()));

    // Then
    assertEquals(1, palette.helpCalls());
  }
}
