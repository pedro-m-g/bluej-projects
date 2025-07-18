package com.pedromg.bluej.shapes.cli;

import java.util.Map;

import com.pedromg.bluej.shapes.cli.command.Command;
import com.pedromg.bluej.shapes.cli.command.DemoCommand;
import com.pedromg.bluej.shapes.cli.command.HelpCommand;

public class CommandRunner {

  private static final String UNKNOWN_ACTION_MESSAGE_FORMAT = "Unknown action: %s. Available actions: %s";

  private static final Map<String, Command> COMMAND_PALETTE = Map.of(
      "help", new HelpCommand(),
      "demo", new DemoCommand());

  /**
   * Runs the command line application.
   *
   * @param args Command line arguments where the first argument is the action
   *             (e.g., "help", "demo") and the subsequent arguments depend on
   *             the action.
   */
  public void run(String[] args) {
    try {
      CommandParser commandParser = new CommandParser();
      CommandRequest request = commandParser.parse(args);
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
