package com.pedromg.bluej.shapes;

import com.pedromg.bluej.shapes.command.CommandDispatcher;
import com.pedromg.bluej.shapes.command.CommandParser;
import com.pedromg.bluej.shapes.command.CommandRequest;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

public class Launcher {

  private final CommandParser commandParser;
  private final CommandDispatcher dispatcher;

  public Launcher(CommandParser commandParser, CommandDispatcher dispatcher) {
    PreConditions.requireNotNull(commandParser, "commandParser must not be null")
        .andNotNull(dispatcher, "dispatcher must not be null");

    this.commandParser = commandParser;
    this.dispatcher = dispatcher;
  }

  public void launchApp(String[] args) {
    try {
      CommandRequest request = commandParser.parse(args);
      dispatcher.dispatch(request);
    } catch (PreConditionsException exception) {
      System.err.println(dispatcher.helpMessage());
    }
  }
}
