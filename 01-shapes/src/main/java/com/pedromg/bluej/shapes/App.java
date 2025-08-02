package com.pedromg.bluej.shapes;

import javax.swing.SwingUtilities;

public class App {

  /**
   * Launches the app on the Swing event dispatch thread.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    Launcher launcher = new Launcher();
    SwingUtilities.invokeLater(() -> launcher.launchApp(args));
  }
}
