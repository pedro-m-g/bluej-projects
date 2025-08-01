package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pedromg.bluej.shapes.mocks.MockDemo;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DemoCatalogTest {

  @ParameterizedTest
  @MethodSource("invalidRegisterArgumentsProvider")
  void shouldThrowWhenRegisterNullArguments(String name, Demo demo) {
    // Given
    DemoCatalog catalog = new DemoCatalog();

    // Then
    assertThrows(PreConditionsException.class, () -> catalog.register(name, demo));
  }

  static Stream<Arguments> invalidRegisterArgumentsProvider() {
    return Stream.of(
        Arguments.of((String) null, (Demo) null),
        Arguments.of("circle", (Demo) null),
        Arguments.of(null, new CircleDemo()));
  }

  @Test
  void shouldThrowWhenFindingNonExistentDemo() {
    // Given
    DemoCatalog catalog = new DemoCatalog();

    // Then
    assertThrows(PreConditionsException.class, () -> catalog.find("circle"));
  }

  @Test
  void shouldRetrieveAvailableDemos() {
    // Given
    DemoCatalog catalog =
        new DemoCatalog()
            .register("circle", new MockDemo())
            .register("square", new MockDemo())
            .register("triangle", new MockDemo());

    // When
    Set<String> actualDemos = catalog.availableDemos();

    // Then
    assertEquals(Set.of("circle", "square", "triangle"), actualDemos);
  }

  @Test
  void shouldFindExistingCommandSuccessfully() {
    // Given
    Demo expectedDemo = new MockDemo();
    DemoCatalog catalog = new DemoCatalog().register("circle", expectedDemo);

    // When
    Demo actualDemo = catalog.find("circle");

    // Then
    assertEquals(expectedDemo, actualDemo);
  }
}
