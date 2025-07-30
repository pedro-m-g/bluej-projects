package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DemoCatalogTest {

  // Register with null arguments
  @ParameterizedTest
  @MethodSource("invalidRegisterArgumentsProvider")
  void shouldThrowWhenRegisterNullArguments(String name, Demo demo) {
    DemoCatalog catalog = new DemoCatalog();
    assertThrows(PreConditionsException.class, () -> catalog.register(name, demo));
  }

  static Stream<Arguments> invalidRegisterArgumentsProvider() {
    return Stream.of(
        Arguments.of((String) null, (Demo) null),
        Arguments.of("circle", (Demo) null),
        Arguments.of(null, new CircleDemo()));
  }

  // Find non existent demo

  // Get available demos

  // Find demo correctly

}
