package com.pedromg.bluej.shapes.command;

public class CommandNotFoundException extends IllegalArgumentException {

  private final String command;

  public CommandNotFoundException(String command) {
    super("Command not found: " + command);
    this.command = command;
  }

  public String command() {
    return command;
  }

}
