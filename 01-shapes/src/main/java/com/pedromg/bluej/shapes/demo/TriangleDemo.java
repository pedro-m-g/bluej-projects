package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.domain.Triangle;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.TrianglePanel;
import java.awt.Color;

public class TriangleDemo implements Demo {

  /**
   * Displays a yellow triangle in the specified canvas.
   *
   * @param canvas the canvas to draw the triangle; must not be null
   * @throws PreConditionsException if {@code canvas} is null
   */
  public void execute(Canvas canvas) {
    PreConditions.requireNotNull(canvas, "canvas must not be null");

    Triangle triangle = new Triangle(200, Color.YELLOW);
    TrianglePanel trianglePanel = new TrianglePanel(triangle);
    canvas.draw(trianglePanel);
  }
}
