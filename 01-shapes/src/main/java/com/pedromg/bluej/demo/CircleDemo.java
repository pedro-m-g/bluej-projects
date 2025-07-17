package com.pedromg.bluej.demo;

import java.awt.Color;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class CircleDemo {

  public void run(MainFrame mainFrame) {
    Circle circle = new Circle(150, Color.RED);
    CirclePanel circlePanel = new CirclePanel(circle);
    mainFrame.add(circlePanel);
  }

}
