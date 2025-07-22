package com.pedromg.bluej.shapes.command;

public class HelpCommand implements Command {

  /**
   * Displays the help message for the CLI commands.
   *
   * @param request command line request (unused)
   */
  @Override
  public void execute(CommandRequest request) {
    System.out.println("Available commands:");
    System.out.println("1. demo <shape> - Run a demo for the specified shape (circle, square, triangle).");
    System.out.println("2. help - Display this help message.");
  }

}
