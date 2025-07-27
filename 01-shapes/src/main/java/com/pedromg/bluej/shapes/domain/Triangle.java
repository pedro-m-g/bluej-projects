package com.pedromg.bluej.shapes.domain;

import java.awt.Color;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

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
   * @throws PreConditionsException if {@code lengthInPixels} is not positive or
   *                                if {@code color} is null
   */
  public Triangle {
    PreConditions
        .require(
            lengthInPixels > 0,
            "length must be a positive number")
        .and(color != null, "color must not be null");
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
