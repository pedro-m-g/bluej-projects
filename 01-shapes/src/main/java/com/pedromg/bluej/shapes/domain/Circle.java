package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.io.Serializable;

public class Circle implements Serializable {

  private int radiusInPixels;
  private Color color;

  /**
   * Constructs a new Circle with the specified radius and color.
   *
   * @param radiusInPixels the radius of the circle in pixels; must be greater than zero
   * @param color the color of the circle; must not be null
   * @throws IllegalArgumentException if radiusInPixels is not greater than zero or if color is null
   */
  public Circle(int radiusInPixels, Color color) {
    if (radiusInPixels <= 0) {
      throw new IllegalArgumentException("Radius must be greater than zero");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }

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
   * Returns a string representation of the circle, including its radius and color.
   *
   * @return a string in the format "Circle [radiusInPixels=..., color=...]"
   */
  @Override
  public String toString() {
    return String.format(
      "Circle [radiusInPixels=%d, color=%s]",
      radiusInPixels,
      color);
  }

}
