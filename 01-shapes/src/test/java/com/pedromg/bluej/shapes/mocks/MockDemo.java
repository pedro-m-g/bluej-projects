package com.pedromg.bluej.shapes.mocks;

import com.pedromg.bluej.shapes.demo.Demo;
import com.pedromg.bluej.shapes.ui.Canvas;

public class MockDemo implements Demo {

  private int numCalls = 0;

  @Override
  public void execute(Canvas canvas) {
    numCalls++;
  }

  public int numCalls() {
    return numCalls;
  }
}
