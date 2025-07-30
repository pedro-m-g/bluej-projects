package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.cli.CLIRequest;

public interface CommandHandler {

  void handle(CLIRequest request);

  String helpMessage();
}
