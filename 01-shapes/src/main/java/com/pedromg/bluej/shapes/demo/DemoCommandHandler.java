package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.cli.CLIRequest;
import com.pedromg.bluej.shapes.command.CommandHandler;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import java.util.Map;

public class DemoCommandHandler implements CommandHandler {

  private static final String USAGE_MESSAGE = "Usage: start demo <shape>";

  private static final Map<String, Demo> DEMOS =
      Map.of(
          "circle", new CircleDemo(),
          "square", new SquareDemo(),
          "triangle", new TriangleDemo());

  /**
   * Executes the demo command with the specified shape.
   *
   * @param request command line request containing {@code shape} param
   * @throws PreConditionsException if the arguments are invalid
   * @throws DemoNotFoundException if the shape is not recognized
   */
  public void handle(CLIRequest request) {
    validatePreConditions(request);
    String shape = request.params().get(0);

    if (!DEMOS.containsKey(shape)) {
      throw new DemoNotFoundException(shape, DEMOS.keySet());
    }

    Canvas canvas = new Canvas();
    canvas.show();
    DEMOS.get(shape).execute(canvas);
  }

  @Override
  public String helpMessage() {
    return String.format("Runs the requested demo. Available demos: %s", DEMOS.keySet());
  }

  private void validatePreConditions(CLIRequest request) {
    PreConditions.requireNotNull(request, "request must not be null")
        .and(request.params().size() == 1, USAGE_MESSAGE)
        .andNotNull(request.params().get(0), USAGE_MESSAGE)
        .andNot(request.params().get(0).isBlank(), USAGE_MESSAGE);
  }
}
