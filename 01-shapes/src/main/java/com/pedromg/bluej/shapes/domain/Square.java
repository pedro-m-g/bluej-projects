package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

public class Square implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int lengthInPixels;
  private final Color color;

  /**
   * Creates a new square with the given side length and color.
   *
   * @param lengthInPixels the length of each side of the square in pixels; must be positive
   * @param color the color of the square; must not be null
   *
   * @throws IllegalArgumentException if {@code lengthInPixels} is not positive
   * @throws NullPointerException if {@code color} is null
   */
  public Square(int lengthInPixels, Color color) {
    Validation.positiveNumber(lengthInPixels, "Side length");
    Objects.requireNonNull(color, "Color must not be null");

    this.lengthInPixels = lengthInPixels;
    this.color = color;
  }

  /**
   * Returns the length of each side of the square in pixels.
   *
   * @return the side length of the square in pixels
   */
  public int getLengthInPixels() {
    return lengthInPixels;
  }

  /**
   * Returns the color of the square.
   *
   * @return the color of the square
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns a string representation of the square, including its side length and color.
   *
   * @return a formatted string describing the square's length in pixels and color
   */
  @Override
  public String toString() {
    return String.format(
        "Square[lengthInPixels=%d, color=%s]",
        lengthInPixels,
        color);
  }

}
