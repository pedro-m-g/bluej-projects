package com.pedromg.bluej.shapes.command;

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
