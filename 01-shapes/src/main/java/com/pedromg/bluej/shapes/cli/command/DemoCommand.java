package com.pedromg.bluej.shapes.cli.command;

import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
import com.pedromg.bluej.shapes.domain.Validation;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class DemoCommand {

  /**
   * Executes the demo command with the specified shape.
   *
   * @param args Command line arguments where the first argument is the command
   *             and the second argument is the shape to demo (circle, square, triangle).
   *
   * @throws IllegalArgumentException if the shape is not recognized or if the arguments are invalid
   */
  public void execute(String[] args) {
    String shape = getShape(args);

    if ("circle".equalsIgnoreCase(shape)) {
      MainFrame mainFrame = new MainFrame();
      mainFrame.open();
      new CircleDemo().run(mainFrame);
    } else if ("square".equalsIgnoreCase(shape)) {
      MainFrame mainFrame = new MainFrame();
      mainFrame.open();
      new SquareDemo().run(mainFrame);
    } else if ("triangle".equalsIgnoreCase(shape)) {
      MainFrame mainFrame = new MainFrame();
      mainFrame.open();
      new TriangleDemo().run(mainFrame);
    } else {
      throw new IllegalArgumentException(
        "Unknown shape: " + shape + ". Available shapes: circle, square, triangle.");
    }
  }

  /**
   * Extracts the shape from the command line arguments.
   *
   * @param args Command line arguments where the first argument is the command
   *             and the second argument is the shape to demo.
   *
   * @throws IllegalArgumentException if the shape argument is missing or blank
   *
   * @return The shape to demo.
   */
  private String getShape(String[] args) {
    try {
      Validation.atLeast(args.length, 2, "Command line arguments length");
      Validation.notBlank(args[1], "Shape");
      return args[1];
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Usage: java -jar 01-shapes.jar demo <shape>");
    }
  }

}
