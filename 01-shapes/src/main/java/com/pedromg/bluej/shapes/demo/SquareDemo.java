package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.domain.Square;
import com.pedromg.bluej.shapes.ui.MainFrame;
import com.pedromg.bluej.shapes.ui.SquarePanel;

public class SquareDemo {

  public void run(MainFrame mainFrame) {
    if (mainFrame == null) {
      throw new IllegalArgumentException("MainFrame cannot be null");
    }

    Square square = new Square(200, java.awt.Color.BLUE);
    SquarePanel squarePanel = new SquarePanel(square);
    mainFrame.add(squarePanel);
  }
}
