package com.pedromg.bluej.shapes;

import com.pedromg.bluej.shapes.command.CLICommandHandler;
import com.pedromg.bluej.shapes.command.CommandPalette;
import com.pedromg.bluej.shapes.command.CommandRequest;
import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.DemoCatalog;
import com.pedromg.bluej.shapes.demo.DemoCommandHandler;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
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
      CommandRequest request = parser.parse(args);

      DemoCatalog demoCatalog =
          new DemoCatalog()
              .register("circle", new CircleDemo())
              .register("square", new SquareDemo())
              .register("triangle", new TriangleDemo());
      DemoCommandHandler demoCommandHandler = new DemoCommandHandler(demoCatalog);

      CommandPalette commandPalette =
          new CommandPalette("start <action>").add("demo", demoCommandHandler);

      CLICommandHandler cliCommandHandler = new CLICommandHandler(commandPalette);
      cliCommandHandler.handle(request);
    } catch (Exception ex) {
      System.err.println("Unexpected error: " + ex.getMessage());
    }
  }
}
