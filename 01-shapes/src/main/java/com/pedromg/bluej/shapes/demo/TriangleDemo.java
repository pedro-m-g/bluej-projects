package com.pedromg.bluej.shapes.demo;

import java.awt.Color;

import com.pedromg.bluej.shapes.domain.Triangle;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.TrianglePanel;

public class TriangleDemo implements Demo {

  /**
   * Displays a yellow triangle in the specified canvas.
   *
   * @param canvas the canvas to draw the triangle; must not be null
   *
   * @throws PreConditionsException if {@code canvas} is null
   */
  public void execute(Canvas canvas) {
    PreConditions
        .requireNonNull(canvas, "canvas must not be null")
        .check();

    Triangle triangle = new Triangle(200, Color.YELLOW);
    TrianglePanel trianglePanel = new TrianglePanel(triangle);
    canvas.draw(trianglePanel);
  }

}
