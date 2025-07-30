package com.pedromg.bluej.shapes.parser;

import com.pedromg.bluej.shapes.cli.CLIRequest;
import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CommandParser {

  private static final String USAGE_MESSAGE = "Usage: java -jar 01-shapes.jar <action> [<args>]";

  /**
   * Parses the given args into a CommandRequest instance
   *
   * @param args the original command line arguments
   * @throws PreConditionsException if <action> argument is missing
   * @return the parse command request
   */
  public CLIRequest parse(String[] args) {
    PreConditions.require(args.length >= 1, USAGE_MESSAGE);

    String action = args[0];
    List<String> arguments = List.of(args).subList(1, args.length);

    List<String> params = new ArrayList<>(arguments.size());
    Set<String> flags = new LinkedHashSet<>(arguments.size());
    for (String arg : arguments) {
      if (arg.startsWith("--")) {
        flags.add(arg.substring(2));
      } else {
        params.add(arg);
      }
    }

    return new CLIRequest(action, params, flags);
  }
}
