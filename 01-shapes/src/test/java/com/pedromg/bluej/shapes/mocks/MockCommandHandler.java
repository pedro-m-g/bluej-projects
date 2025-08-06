package com.pedromg.bluej.shapes.mocks;

import com.pedromg.bluej.shapes.command.CommandHandler;
import com.pedromg.bluej.shapes.command.CommandRequest;
import com.pedromg.bluej.shapes.command.CommandSignature;

public class MockCommandHandler extends CommandHandler {

  private int numCalls = 0;

  @Override
  public void handle(CommandRequest request) {
    numCalls++;
  }

  @Override
  public String helpMessage() {
    return "Mock command";
  }

  @Override
  public CommandSignature signature() {
    return new CommandSignature("mock");
  }

  public int numCalls() {
    return numCalls;
  }
}
