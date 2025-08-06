package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;

public class CommandDispatcher {

  private final CommandPalette commandPalette;

  public CommandDispatcher(CommandPalette commandPalette) {
    PreConditions.requireNotNull(commandPalette, "commandPalette must not be null");

    this.commandPalette = commandPalette;
  }

  /**
   * Handles the given CLIRequest
   *
   * @param request the command line request
   */
  public void handle(CommandRequest request) {
    String action = request.action();
    if (commandPalette.hasCommand(action)) {
      CommandHandler handler = commandPalette.find(action);
      handler.handle(request);
    } else {
      System.err.println(helpMessage());
    }
  }

  public String helpMessage() {
    return commandPalette.helpMessage();
  }

  public CommandSignature signature() {
    return new CommandSignature("<action>");
  }
}
