package com.pedromg.bluej.demo;

import java.awt.Color;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class CircleDemo {

  public void run(MainFrame mainFrame) {
    if (mainFrame == null) {
      throw new IllegalArgumentException("MainFrame cannot be null");
    }
    
    Circle circle = new Circle(150, Color.RED);
    CirclePanel circlePanel = new CirclePanel(circle);
    mainFrame.add(circlePanel);
  }

}
