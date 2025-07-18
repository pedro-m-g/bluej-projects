package com.pedromg.bluej.shapes.cli.command;

import java.util.Map;

import com.pedromg.bluej.shapes.cli.CommandRequest;
import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.Demo;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
import com.pedromg.bluej.shapes.domain.Validation;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class DemoCommand implements Command {

  private static final String UNKNOWN_SHAPE_MESSAGE_FORMAT = "Unknown shape: %s. Available shapes: %s";
  private static final String USAGE_MESSAGE = "Usage: java -jar 01-shapes.jar demo <shape>";

  private static final Map<String, Demo> DEMOS = Map.of(
      "circle", new CircleDemo(),
      "square", new SquareDemo(),
      "triangle", new TriangleDemo());

  /**
   * Executes the demo command with the specified shape.
   *
   * @param args Command line arguments where the first argument is the command
   *             and the second argument is the shape to demo (circle, square,
   *             triangle).
   *
   * @throws IllegalArgumentException if the shape is not recognized or if the
   *                                  arguments are invalid
   */
  public void execute(CommandRequest request) {
    validateRequest(request);

    String shape = request.params().get(0).toLowerCase();
    if (!DEMOS.containsKey(shape)) {
      throw new IllegalArgumentException(
          String.format(
              UNKNOWN_SHAPE_MESSAGE_FORMAT,
              shape,
              DEMOS.keySet()));
    }

    MainFrame mainFrame = new MainFrame();
    mainFrame.open();
    DEMOS.get(shape).run(mainFrame);
  }

  private void validateRequest(CommandRequest request) {
    try {
      Validation.exactly(
        request.params().size(),
        1,
        "Arguments count");
      Validation.notBlank(
        request.params().get(0),
        "Shape");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(USAGE_MESSAGE);
    }
  }

}
