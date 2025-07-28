package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Triangle;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class TrianglePanel extends JPanel {

  private final Triangle triangle;
  private final int[] xPoints;
  private final int[] yPoints;

  /**
   * Constructs a TrianglePanel with the specified triangle.
   *
   * @param triangle the Triangle object to be displayed in the panel
   * @throws PreConditionsException if the triangle is null
   */
  public TrianglePanel(Triangle triangle) {
    PreConditions.requireNotNull(triangle, "triangle must not be null");

    this.triangle = triangle;
    this.xPoints = calculateXPoints();
    this.yPoints = calculateYPoints();
  }

  /** Returns the preferred size of the panel based on the triangle's side length. */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(triangle.lengthInPixels(), triangle.heightInPixels());
  }

  /**
   * Draws an equilateral triangle.
   *
   * <p>The triangle is drawn with its base at the bottom and its apex pointing upwards. The
   * vertices are calculated based on the side length and height. The points are: - Bottom left: (0,
   * height) - Top: (sideLength / 2, 0) - Bottom right: (sideLength, height)
   *
   * @param g the Graphics object used for drawing
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.setColor(triangle.color());
    g2d.fillPolygon(xPoints, yPoints, 3);
  }

  private int[] calculateXPoints() {
    return new int[] {0, triangle.lengthInPixels() / 2, triangle.lengthInPixels()};
  }

  private int[] calculateYPoints() {
    return new int[] {triangle.heightInPixels(), 0, triangle.heightInPixels()};
  }
}
