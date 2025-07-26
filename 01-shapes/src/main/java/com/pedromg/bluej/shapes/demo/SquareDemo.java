package com.pedromg.bluej.shapes.demo;

import java.awt.Color;

import com.pedromg.bluej.shapes.domain.Square;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.SquarePanel;

public class SquareDemo implements Demo {

  /**
   * Displays a blue square in the specified canvas.
   *
   * @param canvas the canvas to draw the square; must not be null
   *
   * @throws PreConditionsException if {@code canvas} is null
   */
  public void execute(Canvas canvas) {
    PreConditions
        .requireNot(canvas == null, "canvas must not be null")
        .check();

    Square square = new Square(200, Color.BLUE);
    SquarePanel squarePanel = new SquarePanel(square);
    canvas.draw(squarePanel);
  }
}
