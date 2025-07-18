package com.pedromg.bluej.shapes.cli.command;

import java.util.Map;

import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.Demo;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
import com.pedromg.bluej.shapes.domain.Validation;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class DemoCommand implements Command {

  private static final String USAGE_MESSAGE = "Usage: java -jar 01-shapes.jar demo <shape>";
  private static final String UNKNOWN_SHAPE_MESSAGE_FORMAT = "Unknown shape: %s. Available shapes: %s";

  private static final Map<String, Demo> DEMOS = Map.of(
    "circle", new CircleDemo(),
    "square", new SquareDemo(),
    "triangle", new TriangleDemo()
  );

  /**
   * Executes the demo command with the specified shape.
   *
   * @param args Command line arguments where the first argument is the command
   *             and the second argument is the shape to demo (circle, square, triangle).
   *
   * @throws IllegalArgumentException if the shape is not recognized or if the arguments are invalid
   */
  public void execute(String[] args) {
    try {
      String shape = getShape(args);

      if (!DEMOS.containsKey(shape)) {
        throw new IllegalArgumentException(
          String.format(
            UNKNOWN_SHAPE_MESSAGE_FORMAT,
            shape,
            DEMOS.keySet()));
      }

      MainFrame mainFrame = new MainFrame();
      mainFrame.open();
      DEMOS.get(shape.toLowerCase()).run(mainFrame);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
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
      return args[1].toLowerCase();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(USAGE_MESSAGE, e);
    }
  }

}
