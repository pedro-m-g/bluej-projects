package com.pedromg.bluej.shapes.command;

import java.util.Map;

public class CLICommandHandler {

  private static final String UNKNOWN_ACTION_MESSAGE_FORMAT = "Unknown action: %s. Available actions: %s";

  private static final Map<String, Command> COMMAND_PALETTE = Map.of(
      "help", new HelpCommand(),
      "demo", new DemoCommand());

  /**
   * Handles the given CLIRequest
   *
   * @param request the command line request
   */
  public void handle(CLIRequest request) {
    try {
      String action = request.action().toLowerCase();

      if (!COMMAND_PALETTE.containsKey(action)) {
        throw new IllegalArgumentException(
            String.format(
                UNKNOWN_ACTION_MESSAGE_FORMAT,
                action,
                COMMAND_PALETTE.keySet()));
      }

      Command command = COMMAND_PALETTE.get(action);
      command.execute(request);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }

}
