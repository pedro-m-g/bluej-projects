package com.pedromg.bluej.shapes.domain;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.awt.Color;

public record Circle(int radiusInPixels, Color color) {

  /**
   * Constructs a new Circle with the specified radius and color.
   *
   * @param radiusInPixels the radius of the circle in pixels; must be greater than zero
   * @param color the color of the circle; must not be null
   * @throws PreConditionsException if radiusInPixels is not greater than zero or if color is null
   */
  public Circle {
    PreConditions.require(radiusInPixels > 0, "radius must be a positive number")
        .andNotNull(color, "color must not be null");
  }

  /**
   * Returns the diameter of the circle in pixels.
   *
   * @return the diameter, calculated as twice the radius
   */
  public int diameterInPixels() {
    return radiusInPixels * 2;
  }
}
