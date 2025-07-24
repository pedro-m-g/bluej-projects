package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.util.Objects;

import com.pedromg.bluej.shapes.validation.PositiveNumberRule;

public record Square(int lengthInPixels, Color color) {

  /**
   * Creates a new square with the given side length and color.
   *
   * @param lengthInPixels the length of each side of the square in pixels; must
   *                       be positive
   * @param color          the color of the square; must not be null
   *
   * @throws IllegalArgumentException if {@code lengthInPixels} is not positive
   * @throws NullPointerException     if {@code color} is null
   */
  public Square {
    PositiveNumberRule.validate(
        lengthInPixels,
        "Side length must be a positive number");
    Objects.requireNonNull(color, "Color must not be null");
  }

}
