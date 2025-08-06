package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CommandSignature {

  private static final Pattern PARAM_REGEX = Pattern.compile("^<\\w+>$");
  private static final Pattern FLAG_REGEX = Pattern.compile("^\\[--\\w+\\]$");

  private String action;
  private List<String> params;
  private Set<String> flags;

  public CommandSignature(String signature) {
    PreConditions.requireNotBlank(signature, "signature must not be blank");
    parse(signature);
  }

  public boolean canHandle(CommandRequest request) {
    return action.equals(request.action())
        && params.size() == request.params().size()
        && flags.containsAll(request.flags());
  }

  private void parse(String signature) {
    String[] tokens = signature.split("\\s+");
    action = tokens[0];

    params = new ArrayList<>(tokens.length - 1);
    flags = new HashSet<>();
    for (int index = 1; index < tokens.length; index++) {
      String token = tokens[index];
      if (PARAM_REGEX.matcher(token).matches()) {
        params.add(token);
      } else if (FLAG_REGEX.matcher(token).matches()) {
        flags.add(token);
      } else {
        throw new CommandSignatureParseException(signature, token);
      }
    }
  }
}
