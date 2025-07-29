package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class CirclePanel extends JPanel {

  private final Circle circle;

  /**
   * Constructs a CirclePanel to display the specified Circle.
   *
   * @param circle the Circle to be rendered; must not be null
   * @throws PreConditionsException if the provided Circle is null
   */
  public CirclePanel(Circle circle) {
    PreConditions.requireNotNull(circle, "circle must not be null");

    this.circle = circle;
  }

  /** Returns the preferred size of the panel based on the circle's diameter. */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(circle.diameterInPixels(), circle.diameterInPixels());
  }

  /**
   * Renders the associated circle onto the panel with anti-aliasing and the circle's color.
   *
   * @param g the Graphics context used for painting
   */
  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(circle.color());
    g2d.fillOval(0, 0, circle.diameterInPixels(), circle.diameterInPixels());
  }
}
