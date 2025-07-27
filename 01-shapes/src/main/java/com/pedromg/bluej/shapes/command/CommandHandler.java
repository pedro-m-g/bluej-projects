package com.pedromg.bluej.shapes.command;

public interface CommandHandler {

  void handle(CLIRequest request);

  String helpMessage();
}
