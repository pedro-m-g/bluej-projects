package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;

public class CLICommandHandler implements CommandHandler {

  private final CommandPalette commandPalette;

  public CLICommandHandler(CommandPalette commandPalette) {
    PreConditions.requireNotNull(commandPalette, "commandPalette must not be null");

    this.commandPalette = commandPalette;
  }

  /**
   * Handles the given CLIRequest
   *
   * @param request the command line request
   */
  @Override
  public void handle(CommandRequest request) {
    String action = request.action();
    if (commandPalette.hasCommand(action)) {
      CommandHandler handler = commandPalette.find(action);
      handler.handle(request);
    } else {
      commandPalette.help();
    }
  }

  @Override
  public String helpMessage() {
    return "App entry point";
  }
}
