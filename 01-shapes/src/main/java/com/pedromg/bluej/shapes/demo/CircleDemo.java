package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import java.awt.Color;

public class CircleDemo implements Demo {

  /**
   * Displays a red circle with a radius of 100 in the specified canvas.
   *
   * @param canvas the canvas to draw the circle
   * @throws PreConditionsException if {@code canvas} is null
   */
  public void execute(Canvas canvas) {
    PreConditions.requireNotNull(canvas, "canvas must not be null");

    Circle circle = new Circle(100, Color.RED);
    CirclePanel circlePanel = new CirclePanel(circle);
    canvas.draw(circlePanel);
  }
}
