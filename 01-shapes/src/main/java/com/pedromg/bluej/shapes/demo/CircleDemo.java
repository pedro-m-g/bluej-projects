package com.pedromg.bluej.shapes.demo;

import java.awt.Color;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class CircleDemo {

  /**
   * Displays a red circle with a radius of 100 in the specified main application frame.
   *
   * @param mainFrame the main application frame to which the circle panel will be added; must not be null
   * @throws IllegalArgumentException if {@code mainFrame} is null
   */
  public void run(MainFrame mainFrame) {
    if (mainFrame == null) {
      throw new IllegalArgumentException("MainFrame cannot be null");
    }

    Circle circle = new Circle(100, Color.RED);
    CirclePanel circlePanel = new CirclePanel(circle);
    mainFrame.add(circlePanel);
  }

}
