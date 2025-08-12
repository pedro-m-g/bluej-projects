package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import java.util.List;
import java.util.Set;

public record CommandSignature(String action, List<String> params, Set<String> flags) {

  public CommandSignature {
    PreConditions.requireNotBlank(action, "action must not be null")
        .andNotNull(params, "params must not be null")
        .andNotNull(flags, "flags must not be null");
  }

  public boolean matches(CommandRequest request) {
    return action.equals(request.action())
        && params.size() == request.params().size()
        && flags.containsAll(request.flags());
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(action);
    params.forEach(param -> builder.append(String.format(" <%s>", param)));
    flags.forEach(flag -> builder.append(String.format(" [--%s]", flag)));
    return builder.toString();
  }
}
