package com.pedromg.bluej.shapes.cli.command;

import com.pedromg.bluej.shapes.cli.CommandRequest;

public interface Command {

  /**
   * Executes the command with the provided arguments.
   *
   * @param request command line request
   */
  void execute(CommandRequest request);

}
