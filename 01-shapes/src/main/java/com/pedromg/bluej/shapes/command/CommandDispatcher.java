package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;

public class CommandDispatcher {

  private final CommandPalette commandPalette;

  public CommandDispatcher(CommandPalette commandPalette) {
    PreConditions.requireNotNull(commandPalette, "commandPalette must not be null");
    this.commandPalette = commandPalette;
  }

  public void dispatch(CommandRequest request) {
    String action = request.action();
    if (!commandPalette.hasCommand(action)) {
      System.err.println(helpMessage());
      return;
    }
    CommandHandler handler = commandPalette.find(action);
    if (!handler.signature().canHandle(request)) {
      System.err.println(helpMessage());
      return;
    }
    handler.handle(request);
  }

  public String helpMessage() {
    return commandPalette.helpMessage();
  }

  public CommandSignature signature() {
    return new CommandSignature("<action>");
  }
}
