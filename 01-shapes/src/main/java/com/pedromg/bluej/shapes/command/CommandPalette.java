package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandPalette {

  private final String ussageMessage;
  private final Map<String, CommandHandler> handlers;

  public CommandPalette(String ussageMessage) {
    PreConditions.requireNotNull(ussageMessage, "usageMessage must not be null")
        .andNot(ussageMessage.isBlank(), "ussageMessage must not be blank");

    this.ussageMessage = ussageMessage;
    this.handlers = new LinkedHashMap<>();
  }

  public CommandPalette() {
    this.ussageMessage = null;
    this.handlers = new LinkedHashMap<>();
  }

  public CommandPalette add(String name, CommandHandler handler) {
    PreConditions.requireNotNull(name, "name must not be null")
        .andNot(name.isBlank(), "name must not be blank")
        .andNotNull(handler, "handler must not be null");

    handlers.put(name, handler);
    return this;
  }

  public boolean hasCommand(String name) {
    PreConditions.requireNotNull(name, "name must not be null")
        .andNot(name.isBlank(), "name must not be blank");

    return handlers.containsKey(name);
  }

  public CommandHandler find(String name) {
    if (!hasCommand(name)) {
      throw new CommandNotFoundException(name);
    }
    return handlers.get(name);
  }

  public void help() {
    if (ussageMessage != null) {
      System.out.println("Ussage: " + ussageMessage + "\n");
    }

    if (handlers.entrySet().isEmpty()) {
      System.out.println("No commands registered");
      return;
    }

    System.out.println("Available command:\n");
    handlers.entrySet().forEach(entry -> printHelpEntry(entry.getKey(), entry.getValue()));
  }

  private void printHelpEntry(String name, CommandHandler handler) {
    System.out.println("    " + name + ": " + handler.helpMessage());
  }
}
