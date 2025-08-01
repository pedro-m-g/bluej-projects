package com.pedromg.bluej.shapes.mocks;

import com.pedromg.bluej.shapes.command.CommandHandler;
import com.pedromg.bluej.shapes.command.CommandRequest;

public class MockCommandHandler implements CommandHandler {

  private int numCalls = 0;

  @Override
  public void handle(CommandRequest request) {
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
