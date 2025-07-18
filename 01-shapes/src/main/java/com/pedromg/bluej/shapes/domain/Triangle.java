package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

public class Triangle implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final double TRIANGLE_HEIGHT_FACTOR = Math.sqrt(3) / 2;

  private final int lengthInPixels;
  private final Color color;

  /**
   * Constructs an equilateral triangle with the specified side length and color.
   *
   * @param lengthInPixels the length of each side of the triangle in pixels; must be positive
   * @param color the color of the triangle; must not be null
   * @throws IllegalArgumentException if {@code lengthInPixels} is not positive
   * @throws NullPointerException if {@code color} is null
   */
  public Triangle(int lengthInPixels, Color color) {
    Validation.positiveNumber(lengthInPixels, "Side length");
    Objects.requireNonNull(color, "Color must not be null");

    this.lengthInPixels = lengthInPixels;
    this.color = color;
  }

  /**
   * Returns the side length of the triangle in pixels.
   *
   * @return the length of each side in pixels
   */
  public int getLengthInPixels() {
    return lengthInPixels;
  }

  /**
   * Returns the color of this triangle.
   *
   * @return the color assigned to this triangle
   */
  public Color getColor() {
    return color;
  }

  /**
   * Calculates and returns the height of the equilateral triangle in pixels, rounding up to the nearest integer.
   *
   * @return the height of the triangle in pixels
   * @see <a href="https://en.wikipedia.org/wiki/Equilateral_triangle#Height">Height of an equilateral triangle</a>
   */
  public int getHeightInPixels() {
    return (int) Math.ceil(TRIANGLE_HEIGHT_FACTOR * lengthInPixels);
  }

  /**
   * Returns a formatted string describing the triangle's side length in pixels and its color.
   *
   * @return a string representation of the triangle
   */
  @Override
  public String toString() {
    return String.format(
        "Triangle[lengthInPixels=%d, color=%s]",
        lengthInPixels,
        color);
  }

}
