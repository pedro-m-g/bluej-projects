package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Triangle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TrianglePanel extends JPanel {

  private final Triangle triangle;

  public TrianglePanel(Triangle triangle) {
    if (triangle == null) {
      throw new IllegalArgumentException("Triangle cannot be null");
    }

    this.triangle = triangle;
  }

  /**
   * Returns the preferred size of the panel based on the triangle's side length.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(
        triangle.getLengthInPixels(),
        triangle.getHeightInPixels());
  }

  /**
   * Draws an equilateral triangle.
   *
   * The triangle is drawn with its base at the bottom and its apex pointing
   * upwards.
   * The vertices are calculated based on the side length and height.
   * The points are:
   * - Bottom left: (0, height)
   * - Top: (sideLength / 2, 0)
   * - Bottom right: (sideLength, height)
   *
   * @param g the Graphics object used for drawing
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(
        java.awt.RenderingHints.KEY_ANTIALIASING,
        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

    int sideLength = triangle.getLengthInPixels();
    int height = (triangle.getHeightInPixels());

    int[] xPoints = {0, sideLength / 2, sideLength};
    int[] yPoints = {height, 0, height};

    g2d.setColor(triangle.getColor());
    g2d.fillPolygon(xPoints, yPoints, 3);
  }

}
