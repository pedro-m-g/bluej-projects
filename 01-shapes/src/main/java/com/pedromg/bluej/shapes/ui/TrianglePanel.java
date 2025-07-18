package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Triangle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Objects;

import javax.swing.JPanel;

public class TrianglePanel extends JPanel {

  private final Triangle triangle;

  /**
   * Creates a panel that displays the given triangle.
   *
   * @param triangle the Triangle to render; must not be null
   * @throws NullPointerException if {@code triangle} is null
   */
  public TrianglePanel(Triangle triangle) {
    Objects.requireNonNull(triangle, "Triangle cannot be null");

    this.triangle = triangle;
  }

  /**
   * Returns the preferred size of this panel, using the triangle's side length as width and its height as height in pixels.
   *
   * @return a Dimension representing the preferred size for displaying the triangle
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(
        triangle.getLengthInPixels(),
        triangle.getHeightInPixels());
  }

  /**
   * Renders the associated equilateral triangle onto the panel.
   *
   * The triangle is filled with its configured color, with the base aligned to the bottom of the panel and the apex pointing upward. The triangle's size and position are determined by its side length and height in pixels.
   *
   * @param g the Graphics context used for painting
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
