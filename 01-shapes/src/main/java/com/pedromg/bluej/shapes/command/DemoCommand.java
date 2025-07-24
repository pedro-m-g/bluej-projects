package com.pedromg.bluej.shapes.command;

import java.util.Map;

import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.Demo;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
import com.pedromg.bluej.shapes.ui.MainFrame;
import com.pedromg.bluej.shapes.validation.CollectionSizeRule;
import com.pedromg.bluej.shapes.validation.NotBlankRule;

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
   * @param request command line request containing <shape> param
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
    CollectionSizeRule.validate(
        request.params(), 1, USAGE_MESSAGE);
    NotBlankRule.validate(
        request.params().get(0), USAGE_MESSAGE);
  }

}
