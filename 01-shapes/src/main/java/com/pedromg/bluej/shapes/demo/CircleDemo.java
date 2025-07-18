package com.pedromg.bluej.shapes.demo;

import java.awt.Color;
import java.util.Objects;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class CircleDemo {

  /****
   * Adds a panel displaying a red circle with a radius of 100 to the specified main application frame.
   *
   * @param mainFrame the main application frame to which the circle panel will be added; must not be null
   * @throws NullPointerException if {@code mainFrame} is null
   */
  public void run(MainFrame mainFrame) {
    Objects.requireNonNull(mainFrame, "MainFrame cannot be null");

    Circle circle = new Circle(100, Color.RED);
    CirclePanel circlePanel = new CirclePanel(circle);
    mainFrame.add(circlePanel);
  }

}
