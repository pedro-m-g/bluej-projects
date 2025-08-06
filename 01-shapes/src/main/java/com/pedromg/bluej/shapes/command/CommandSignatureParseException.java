package com.pedromg.bluej.shapes.command;

public class CommandSignatureParseException extends RuntimeException {

  private final String signature;
  private final String token;

  public CommandSignatureParseException(String signature, String token) {
    super("Invalid command signature: " + signature + "; unrecognized token: " + token);
    this.signature = signature;
    this.token = token;
  }

  public String signature() {
    return signature;
  }

  public String token() {
    return token;
  }
}
