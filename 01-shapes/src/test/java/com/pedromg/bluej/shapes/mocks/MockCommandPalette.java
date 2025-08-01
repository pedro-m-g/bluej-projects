package com.pedromg.bluej.shapes.mocks;

import com.pedromg.bluej.shapes.command.CommandPalette;

public class MockCommandPalette extends CommandPalette {

  private int helpCalls = 0;

  @Override
  public void help() {
    helpCalls++;
  }

  public int helpCalls() {
    return helpCalls;
  }
}
