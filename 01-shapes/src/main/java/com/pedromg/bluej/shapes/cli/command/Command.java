package com.pedromg.bluej.shapes.cli.command;

public interface Command {

  /**
   * Executes the command with the provided arguments.
   *
   * @param args Command line arguments specific to the command
   */
  void execute(String[] args);

}
