package com.pedromg.bluej.shapes.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.pedromg.bluej.shapes.domain.Square;

public class SquarePanel extends JPanel {

  private final Square square;

  public SquarePanel(Square square) {
    if (square == null) {
      throw new IllegalArgumentException("Square cannot be null");
    }

    this.square = square;
    setPreferredSize(
        new Dimension(
            square.getLengthInPixels(),
            square.getLengthInPixels()));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(square.getColor());

    int sideLength = square.getLengthInPixels();
    g2d.fillRect(0, 0, sideLength, sideLength);
  }

}
