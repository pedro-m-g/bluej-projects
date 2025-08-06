package com.pedromg.bluej.shapes.command;

public abstract class CommandHandler {

  public abstract String helpMessage();

  protected abstract void handle(CommandRequest request);

  protected abstract CommandSignature signature();
}
