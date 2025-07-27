package com.pedromg.bluej.shapes.command;

public interface Command {

  /**
   * Executes the command with the provided arguments.
   *
   * @param request command line request
   */
  void execute(CLIRequest request);

}
