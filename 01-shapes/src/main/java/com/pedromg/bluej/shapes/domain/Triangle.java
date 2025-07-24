package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.util.Objects;

import com.pedromg.bluej.shapes.validation.PositiveNumberRule;

public record Triangle(
    int lengthInPixels,
    Color color) {

  private static final double TRIANGLE_HEIGHT_FACTOR = Math.sqrt(3) / 2;

  /**
   * Creates a new triangle with the given side length and color.
   *
   * @param lengthInPixels the length of each side of the triangle in pixels; must
   *                       be positive
   * @param color          the color of the triangle; must not be null
   *
   * @throws IllegalArgumentException if {@code lengthInPixels} is not positive
   * @throws NullPointerException     if {@code color} is null
   */
  public Triangle {
    PositiveNumberRule.validate(
        lengthInPixels,
        "Side length must be a positive number");
    Objects.requireNonNull(color, "Color must not be null");
  }

  /**
   * Returns the height of the triangle in pixels, calculated using the formula
   * for an equilateral triangle.
   *
   * @return the height of the triangle in pixels
   * @see <a href=
   *      "https://en.wikipedia.org/wiki/Equilateral_triangle#Height">Height of an
   *      equilateral triangle</a>
   */
  public int heightInPixels() {
    return (int) Math.ceil(TRIANGLE_HEIGHT_FACTOR * lengthInPixels);
  }

}
