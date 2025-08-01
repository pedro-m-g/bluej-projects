package com.pedromg.bluej.shapes.command;

public interface CommandHandler {

  void handle(CommandRequest request);

  String helpMessage();
}
