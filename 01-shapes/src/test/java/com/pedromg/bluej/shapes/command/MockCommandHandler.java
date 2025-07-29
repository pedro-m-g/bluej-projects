package com.pedromg.bluej.shapes.command;

class MockCommandHandler implements CommandHandler {

  private int numCalls = 0;

  @Override
  public void handle(CLIRequest request) {
    numCalls++;
  }

  @Override
  public String helpMessage() {
    return "Mock command";
  }

  public int numCalls() {
    return numCalls;
  }
}
