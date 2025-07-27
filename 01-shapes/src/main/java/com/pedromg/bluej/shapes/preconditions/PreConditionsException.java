package com.pedromg.bluej.shapes.preconditions;

public class PreConditionsException extends IllegalArgumentException {

  public PreConditionsException(String message) {
    super(message);
  }

  public PreConditionsException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
