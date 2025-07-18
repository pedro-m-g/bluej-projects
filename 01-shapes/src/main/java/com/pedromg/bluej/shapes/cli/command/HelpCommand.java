package com.pedromg.bluej.shapes.cli.command;

public class HelpCommand implements Command {

  /**
   * Displays the help message for the CLI commands.
   */
  @Override
  public void execute(String[] args) {
    System.out.println("Available commands:");
    System.out.println("1. demo <shape> - Run a demo for the specified shape (circle, square, triangle).");
    System.out.println("2. help - Display this help message.");
  }

}
