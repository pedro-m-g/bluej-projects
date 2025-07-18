package com.pedromg.bluej.shapes.cli;

import java.util.Map;
import java.util.Objects;

import com.pedromg.bluej.shapes.cli.command.Command;
import com.pedromg.bluej.shapes.cli.command.DemoCommand;
import com.pedromg.bluej.shapes.cli.command.HelpCommand;
import com.pedromg.bluej.shapes.domain.Validation;

public class CommandLineRunner {

  private static final String USAGE_MESSAGE = "Usage: java -jar 01-shapes.jar <action> [<args>]";
  private static final String UNKNOWN_ACTION_MESSAGE_FORMAT = "Unknown action: %s. Available actions: %s";

  private static final Map<String, Command> COMMAND_PALETTE = Map.of(
    "help", new HelpCommand(),
    "demo", new DemoCommand()
  );

  /**
   * Runs the command line application.
   *
   * @param args Command line arguments where the first argument is the action
   *             (e.g., "help", "demo") and the subsequent arguments depend on
   *             the action.
   */
  public void run(String[] args) {
    try {
      String action = getAction(args);

      if (!COMMAND_PALETTE.containsKey(action)) {
        throw new IllegalArgumentException(
            String.format(
                UNKNOWN_ACTION_MESSAGE_FORMAT,
                action,
                COMMAND_PALETTE.keySet()));
      }

      Command command = COMMAND_PALETTE.get(action);
      command.execute(args);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Extracts the action from the command line arguments.
   *
   * @param args Command line arguments
   *
   * @throws IllegalArgumentException if the action is not provided or is blank
   *
   * @return the action as a String
   */
  private String getAction(String[] args) {
    try {
      Objects.requireNonNull(args, "Command line arguments must not be null");
      Validation.atLeast(args.length, 1, "Command line arguments length");

      String action = args[0];
      Validation.notBlank(action, "Action");
      return action.toLowerCase();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(USAGE_MESSAGE, e);
    }
  }

}
