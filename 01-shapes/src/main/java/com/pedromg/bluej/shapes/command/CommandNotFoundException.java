package com.pedromg.bluej.shapes.command;

public class CommandNotFoundException extends IllegalArgumentException {

  private static final long serialVersionUID = 1L;

  private final String command;

  public CommandNotFoundException(String command) {
    super("Command not found: " + command);
    this.command = command;
  }

  public String command() {
    return command;
  }
}
