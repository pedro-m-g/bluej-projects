package com.pedromg.bluej.shapes.mocks;

import com.pedromg.bluej.shapes.command.CommandPalette;

public class MockCommandPalette extends CommandPalette {

  private int helpCalls = 0;

  @Override
  public String helpMessage() {
    helpCalls++;
    return "Mock Command";
  }

  public int helpCalls() {
    return helpCalls;
  }
}
