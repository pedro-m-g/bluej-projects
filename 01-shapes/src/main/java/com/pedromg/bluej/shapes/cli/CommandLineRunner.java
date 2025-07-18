package com.pedromg.bluej.shapes.cli;

import java.util.Objects;
import java.util.logging.Logger;

import com.pedromg.bluej.shapes.cli.command.DemoCommand;
import com.pedromg.bluej.shapes.domain.Validation;

public class CommandLineRunner {

  private static final Logger LOGGER = Logger.getLogger(CommandLineRunner.class.getName());

  /**
   * Runs the command line application.
   *
   * @param args Command line arguments where the first argument is the action
   *             (e.g., "help", "demo") and the subsequent arguments depend on
   *             the action.
   */
  public void run(String[] args) {
    String action = getAction(args);

    if ("help".equalsIgnoreCase(action)) {
      LOGGER.info("Usage: java -jar 01-shapes.jar <action> [<args>]");
      LOGGER.info("Available actions: help, demo");
    } else if ("demo".equalsIgnoreCase(action)) {
      LOGGER.info("Running demo...");
      DemoCommand demoCommand = new DemoCommand();
      demoCommand.execute(args);
    } else {
      throw new IllegalArgumentException(
        String.format(
          "Unknown action: %s. Available actions: help, demo",
          action));
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
      Validation.positiveNumber(args.length, "Command line arguments length");

      String action = args[0];
      Validation.notBlank(action, "Action");
      return action;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Usage: java -jar 01-shapes.jar <action> [<args>]");
    }
  }

}
