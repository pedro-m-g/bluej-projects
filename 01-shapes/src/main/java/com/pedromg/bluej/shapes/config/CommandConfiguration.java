package com.pedromg.bluej.shapes.config;

import com.pedromg.bluej.shapes.command.CommandDispatcher;
import com.pedromg.bluej.shapes.command.CommandPalette;
import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.DemoCatalog;
import com.pedromg.bluej.shapes.demo.DemoCommand;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;

public class CommandConfiguration {

  private static final String USAGE_MESSAGE = "run <action> <args>";

  private final CommandPalette commandPalette;

  public CommandConfiguration() {
    this.commandPalette = createCommandPalette();
  }

  public CommandDispatcher commandDispatcher() {
    return new CommandDispatcher(commandPalette);
  }

  private CommandPalette createCommandPalette() {
    return new CommandPalette(USAGE_MESSAGE).add("demo", createDemoCommand());
  }

  private DemoCommand createDemoCommand() {
    return new DemoCommand(
        new DemoCatalog()
            .register("circle", new CircleDemo())
            .register("square", new SquareDemo())
            .register("triangle", new TriangleDemo()));
  }
}
