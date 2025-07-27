package com.pedromg.bluej.shapes.domain;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.awt.Color;

public record Square(int lengthInPixels, Color color) {

  /**
   * Creates a new square with the given side length and color.
   *
   * @param lengthInPixels the length of each side of the square in pixels; must be positive
   * @param color the color of the square; must not be null
   * @throws PreConditionsException if {@code lengthInPixels} is not positive or if {@code color} is
   *     null
   */
  public Square {
    PreConditions.require(lengthInPixels > 0, "length must be a positive number")
        .and(color != null, "color must not be null");
  }
}
