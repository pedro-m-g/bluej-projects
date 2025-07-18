package com.pedromg.bluej.shapes.demo;

import java.awt.Color;
import java.util.Objects;

import com.pedromg.bluej.shapes.domain.Triangle;
import com.pedromg.bluej.shapes.ui.MainFrame;
import com.pedromg.bluej.shapes.ui.TrianglePanel;

public class TriangleDemo {

  /**
   * Displays a yellow triangle in the specified main application frame.
   *
   * @param mainFrame the main application frame to which the yellow triangle panel will be added; must not be null
   *
   * @throws NullPointerException if {@code mainFrame} is null
   */
  public void run(MainFrame mainFrame) {
    Objects.requireNonNull(mainFrame, "MainFrame cannot be null");

    Triangle triangle = new Triangle(200, Color.YELLOW);
    TrianglePanel trianglePanel = new TrianglePanel(triangle);
    mainFrame.add(trianglePanel);
  }

}
