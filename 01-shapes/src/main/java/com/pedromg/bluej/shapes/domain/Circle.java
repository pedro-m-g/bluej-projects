package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

public class Circle implements Serializable {

  public static final long serialVersionUID = 1L;

  private int radiusInPixels;
  private Color color;

  /****
   * Creates a Circle with the given radius and color.
   *
   * @param radiusInPixels the radius of the circle in pixels; must be positive
   * @param color the color of the circle; must not be null
   * @throws IllegalArgumentException if radiusInPixels is not positive
   * @throws NullPointerException if color is null
   */
  public Circle(int radiusInPixels, Color color) {
    Validation.positiveNumber(radiusInPixels, "Radius");
    Objects.requireNonNull(color, "Color must not be null");

    this.radiusInPixels = radiusInPixels;
    this.color = color;
  }

  /**
   * Returns the radius of the circle in pixels.
   *
   * @return the radius in pixels
   */
  public int getRadiusInPixels() {
    return radiusInPixels;
  }

  /**
   * Returns the diameter of the circle in pixels.
   *
   * @return the diameter, calculated as twice the radius
   */
  public int getDiameterInPixels() {
    return radiusInPixels * 2;
  }

  /**
   * Returns the color of the circle.
   *
   * @return the color of this circle
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns a string representation of the circle, including its radius and color in the format "Circle[radiusInPixels=..., color=...]".
   *
   * @return a string describing the circle's radius and color
   */
  @Override
  public String toString() {
    return String.format(
      "Circle[radiusInPixels=%d, color=%s]",
      radiusInPixels,
      color);
  }

}
