package com.pedromg.bluej.shapes.domain;

import java.awt.Color;
import java.io.Serializable;

public class Circle implements Serializable {

  private int radiusInPixels;
  private Color color;

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

  public int getRadiusInPixels() {
    return radiusInPixels;
  }

  public int getDiameterInPixels() {
    return radiusInPixels * 2;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public String toString() {
    return String.format(
      "Circle [radiusInPixels=%d, color=%s]",
      radiusInPixels,
      color);
  }

}
