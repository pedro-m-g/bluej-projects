package com.pedromg.bluej.shapes;

import com.pedromg.bluej.shapes.command.CommandDispatcher;
import com.pedromg.bluej.shapes.command.CommandPalette;
import com.pedromg.bluej.shapes.command.CommandRequest;
import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.DemoCatalog;
import com.pedromg.bluej.shapes.demo.DemoCommand;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
import com.pedromg.bluej.shapes.parser.CommandParser;

public class Launcher {

  public void launchApp(String[] args) {
    CommandParser parser = new CommandParser();
    CommandRequest request = parser.parse(args);

    DemoCatalog demoCatalog =
        new DemoCatalog()
            .register("circle", new CircleDemo())
            .register("square", new SquareDemo())
            .register("triangle", new TriangleDemo());
    DemoCommand demoCommand = new DemoCommand(demoCatalog);

    CommandPalette commandPalette = new CommandPalette("start <action>").add("demo", demoCommand);

    CommandDispatcher dispatcher = new CommandDispatcher(commandPalette);
    dispatcher.handle(request);
  }
}
