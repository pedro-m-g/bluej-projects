package com.pedromg.bluej.shapes;

import com.pedromg.bluej.shapes.cli.CLICommandHandler;
import com.pedromg.bluej.shapes.cli.CLIRequest;
import com.pedromg.bluej.shapes.command.CommandPalette;
import com.pedromg.bluej.shapes.demo.DemoCommandHandler;
import com.pedromg.bluej.shapes.parser.CommandParser;
import javax.swing.SwingUtilities;

public class App {

  /**
   * Application entry point. Schedules initialization on the Swing event dispatch thread.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> run(args));
  }

  private static void run(String[] args) {
    try {
      CommandParser parser = new CommandParser();
      CLIRequest request = parser.parse(args);
      CommandPalette commandPalette =
          new CommandPalette("start <action>").add("demo", new DemoCommandHandler());
      CLICommandHandler cliCommandHandler = new CLICommandHandler(commandPalette);
      cliCommandHandler.handle(request);
    } catch (Exception ex) {
      System.err.println("Unexpected error: " + ex.getMessage());
    }
  }
}
