package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.io.Serializable;

public class Square implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int lengthInPixels;
  private final Color color;

  /**
   * Constructs a square with the specified side length and color.
   *
   * @param lengthInPixels the length of each side of the square, in pixels; must
   *                       be positive
   * @param color          the color of the square; must not be null
   * @throws IllegalArgumentException if {@code sideLength} is not positive or
   *                                  {@code color} is null
   */
  public Square(int lengthInPixels, Color color) {
    if (lengthInPixels <= 0) {
      throw new IllegalArgumentException("Side length must be positive");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }

    this.lengthInPixels = lengthInPixels;
    this.color = color;
  }

  public int getLengthInPixels() {
    return lengthInPixels;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public String toString() {
    return String.format(
        "Square[sideLength=%d, color=%s]",
        lengthInPixels,
        color);
  }

}
