package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandPalette {

  private final String usageMessage;
  private final Map<String, CommandHandler> handlers;

  public CommandPalette(String usageMessage) {
    PreConditions.requireNotNull(usageMessage, "usageMessage must not be null")
        .andNot(usageMessage.isBlank(), "usageMessage must not be blank");

    this.usageMessage = usageMessage;
    this.handlers = new LinkedHashMap<>();
  }

  public CommandPalette() {
    this.usageMessage = null;
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

  public String helpMessage() {
    StringBuilder builder = new StringBuilder();
    if (usageMessage != null) {
      builder.append("Usage: " + usageMessage + "\n");
    }

    if (handlers.entrySet().isEmpty()) {
      builder.append("No commands registered");
      return builder.toString();
    }

    builder.append("Available commands:\n");
    handlers
        .entrySet()
        .forEach(entry -> builder.append(helpEntry(entry.getKey(), entry.getValue())));

    return builder.toString();
  }

  private String helpEntry(String name, CommandHandler handler) {
    return "    " + name + ": " + handler.helpMessage();
  }
}
