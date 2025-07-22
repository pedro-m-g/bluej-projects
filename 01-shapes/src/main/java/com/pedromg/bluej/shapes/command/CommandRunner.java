package com.pedromg.bluej.shapes.command;

import java.util.Map;

public class CommandRunner {

  private static final String UNKNOWN_ACTION_MESSAGE_FORMAT = "Unknown action: %s. Available actions: %s";

  private static final Map<String, Command> COMMAND_PALETTE = Map.of(
      "help", new HelpCommand(),
      "demo", new DemoCommand());

  /**
   * Runs the command line application.
   *
   * @param request the command line request
   */
  public void run(CommandRequest request) {
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
