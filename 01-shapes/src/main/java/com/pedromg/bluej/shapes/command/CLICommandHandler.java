package com.pedromg.bluej.shapes.command;

public class CLICommandHandler implements CommandHandler {

  private final CommandPalette commandPalette;

  public CLICommandHandler(CommandPalette commandPalette) {
    this.commandPalette = commandPalette;
  }

  /**
   * Handles the given CLIRequest
   *
   * @param request the command line request
   */
  @Override
  public void handle(CLIRequest request) {
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
