package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.command.CommandHandler;
import com.pedromg.bluej.shapes.command.CommandRequest;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;

public class DemoCommandHandler implements CommandHandler {

  private static final String USAGE_MESSAGE = "Usage: start demo <shape>";

  private final DemoCatalog demoCatalog;

  /**
   * Creates a DemoCommandHandler
   *
   * @param demoCatalog the demo catalog
   * @throws PreConditionsException if demoCatalog is null
   */
  public DemoCommandHandler(DemoCatalog demoCatalog) {
    PreConditions.requireNotNull(demoCatalog, "demoCatalog must not be null");
    this.demoCatalog = demoCatalog;
  }

  /**
   * Executes the demo command associated to the specified shape.
   *
   * @param request command line request containing {@code shape} param
   * @throws PreConditionsException if the arguments are invalid or the shape param is not in demo
   *     catalog
   */
  public void handle(CommandRequest request) {
    validatePreConditions(request);

    String shape = request.params().get(0);
    Demo demo = demoCatalog.find(shape);

    Canvas canvas = new Canvas();
    canvas.show();
    demo.execute(canvas);
  }

  @Override
  public String helpMessage() {
    return String.format(
        "Runs the requested demo. Available demos: %s", demoCatalog.availableDemos());
  }

  private void validatePreConditions(CommandRequest request) {
    PreConditions.requireNotNull(request, "request must not be null")
        .and(request.params().size() == 1, USAGE_MESSAGE)
        .andNotNull(request.params().get(0), USAGE_MESSAGE)
        .andNot(request.params().get(0).isBlank(), USAGE_MESSAGE);
  }
}
