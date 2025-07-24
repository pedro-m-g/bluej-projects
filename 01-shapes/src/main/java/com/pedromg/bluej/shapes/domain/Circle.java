package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.util.Objects;

import com.pedromg.bluej.shapes.validation.PositiveNumberRule;

public record Circle(int radiusInPixels, Color color) {

  /**
   * Constructs a new Circle with the specified radius and color.
   *
   * @param radiusInPixels the radius of the circle in pixels; must be greater
   *                       than zero
   * @param color          the color of the circle; must not be null
   *
   * @throws IllegalArgumentException if radiusInPixels is not greater than zero
   * @throws NullPointerException     if color is null
   */
  public Circle {
    PositiveNumberRule.validate(
        radiusInPixels,
        "Radius must be a positive number");
    Objects.requireNonNull(color, "Color must not be null");
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
