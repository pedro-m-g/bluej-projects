package com.pedromg.bluej.shapes;

import com.pedromg.bluej.shapes.command.CLICommandHandler;
import com.pedromg.bluej.shapes.command.CLIRequest;
import com.pedromg.bluej.shapes.command.CommandPalette;
import com.pedromg.bluej.shapes.command.DemoCommand;
import com.pedromg.bluej.shapes.parser.CommandParser;
import javax.swing.SwingUtilities;

public class App {

  /**
   * Application entry point that initializes and displays the main user interface window.
   *
   * <p>Schedules UI initialization and demo execution on the Swing event dispatch thread. Logs and
   * prints the stack trace if an exception occurs during startup.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> run(args));
  }

  /**
   * Runs the application by setting up logging and executing the command line runner.
   *
   * @param args command-line arguments
   */
  private static void run(String[] args) {
    CommandParser parser = new CommandParser();
    CLIRequest request = parser.parse(args);
    CommandPalette commandPalette =
        new CommandPalette("start <action>").add("demo", new DemoCommand());
    CLICommandHandler cliCommandHandler = new CLICommandHandler(commandPalette);
    cliCommandHandler.handle(request);
  }
}
