package com.pedromg.bluej.shapes;

import com.pedromg.bluej.shapes.command.CommandParser;
import com.pedromg.bluej.shapes.config.CommandConfiguration;
import javax.swing.SwingUtilities;

public class App {

  /**
   * Launches the app on the Swing event dispatch thread.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    Launcher launcher =
        new Launcher(new CommandParser(), new CommandConfiguration().commandDispatcher());

    SwingUtilities.invokeLater(() -> launcher.launchApp(args));
  }
}
