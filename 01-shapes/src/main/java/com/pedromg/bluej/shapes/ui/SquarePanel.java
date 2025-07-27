package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Square;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Objects;
import javax.swing.JPanel;

public class SquarePanel extends JPanel {

  private final Square square;

  /**
   * Constructs a SquarePanel to visually represent the specified Square.
   *
   * @param square the Square to be displayed; must not be null
   * @throws NullPointerException if the provided square is null
   */
  public SquarePanel(Square square) {
    Objects.requireNonNull(square, "Square must not be null");

    this.square = square;
  }

  /** Returns the preferred size of the panel, which is determined by the square's side length. */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(square.lengthInPixels(), square.lengthInPixels());
  }

  /**
   * Paints the panel by rendering the associated square with its color and size.
   *
   * <p>This method fills the panel with a square whose color and side length are determined by the
   * associated {@code Square} object.
   *
   * @param g the {@code Graphics} context in which to paint
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(square.color());

    int sideLength = square.lengthInPixels();
    g2d.fillRect(0, 0, sideLength, sideLength);
  }
}
