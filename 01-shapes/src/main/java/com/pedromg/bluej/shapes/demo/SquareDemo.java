package com.pedromg.bluej.shapes.demo;

import java.util.Objects;

import com.pedromg.bluej.shapes.domain.Square;
import com.pedromg.bluej.shapes.ui.MainFrame;
import com.pedromg.bluej.shapes.ui.SquarePanel;

public class SquareDemo {

  /**
   * Adds a panel displaying a blue square to the specified main application frame.
   *
   * @param mainFrame the main application frame to which the blue square panel will be added; must not be null
   * @throws NullPointerException if {@code mainFrame} is null
   */
  public void run(MainFrame mainFrame) {
    Objects.requireNonNull(mainFrame, "MainFrame cannot be null");

    Square square = new Square(200, java.awt.Color.BLUE);
    SquarePanel squarePanel = new SquarePanel(square);
    mainFrame.add(squarePanel);
  }
}
