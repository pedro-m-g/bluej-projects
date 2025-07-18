package com.pedromg.bluej.shapes.cli;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.pedromg.bluej.shapes.domain.Validation;

public class CommandParser {

  private static final String USAGE_MESSAGE = "Usage: java -jar 01-shapes.jar <action> [<args>]";

  public CommandRequest parse(String[] args) {
    try {
      Validation.atLeast(
        args.length,
        1,
        "Arguments count");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(USAGE_MESSAGE, e);
    }

    String action = args[0];

    List<String> arguments = List.of(args)
      .subList(1, args.length);

    List<String> params = new ArrayList<>(arguments.size());
    Set<String> flags = new LinkedHashSet<>(arguments.size());
    for (String arg : arguments) {
      if (arg.startsWith("--")) {
        flags.add(arg.substring(2));
      } else if (!arg.equals(action)) {
        params.add(arg);
      }
    }

    return new CommandRequest(action, params, flags);
  }

}
