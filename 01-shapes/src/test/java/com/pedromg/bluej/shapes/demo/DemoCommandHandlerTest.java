package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pedromg.bluej.shapes.cli.CLIRequest;
import com.pedromg.bluej.shapes.mocks.MockDemo;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DemoCommandHandlerTest {

  @Test
  void shouldThrowWhenRequestIsNull() {
    // Given
    DemoCommandHandler command = new DemoCommandHandler(new DemoCatalog());

    // Then
    assertThrows(PreConditionsException.class, () -> command.handle(null));
  }

  @ParameterizedTest
  @MethodSource("invalidArgumentsProvider")
  void shouldThrowWhenRequestIsInvalid(List<String> params, Set<String> flags) {
    // Given
    DemoCommandHandler command = new DemoCommandHandler(new DemoCatalog());
    CLIRequest request = new CLIRequest("demo", params, flags);

    // Then
    assertThrows(PreConditionsException.class, () -> command.handle(request));
  }

  static Stream<Arguments> invalidArgumentsProvider() {
    return Stream.of(
        Arguments.of(List.of(), Set.of()),
        Arguments.of(List.of(""), Set.of()),
        Arguments.of(List.of("   "), Set.of()));
  }

  @Test
  void shouldThrowWhenDemoIsNotRecognized() {
    // Given
    DemoCommandHandler command =
        new DemoCommandHandler(new DemoCatalog().register("circle", new MockDemo()));
    CLIRequest request = new CLIRequest("demo", List.of("pentagon"), Set.of());

    // When
    assertThrows(PreConditionsException.class, () -> command.handle(request));
  }

  @Test
  void shouldExecuteDemoSuccessfully() {
    // Given
    MockDemo mockDemo = new MockDemo();
    DemoCatalog demoCatalog = new DemoCatalog().register("circle", mockDemo);
    DemoCommandHandler handler = new DemoCommandHandler(demoCatalog);
    CLIRequest request = new CLIRequest("demo", List.of("circle"), Set.of());

    // When
    handler.handle(request);

    // Then
    assertEquals(1, mockDemo.numCalls());
  }
}
