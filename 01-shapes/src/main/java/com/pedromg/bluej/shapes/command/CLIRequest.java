package com.pedromg.bluej.shapes.command;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import java.util.List;
import java.util.Set;

public record CLIRequest(String action, List<String> params, Set<String> flags) {

  public CLIRequest {
    PreConditions.requireNotBlank(action, "action must not be blank")
        .andNotNull(params, "params must not be null")
        .andNotNull(flags, "flags must not be null");
  }

  /**
   * Checks if a flag is present in this request
   *
   * @param flagName the flag to check
   * @throws PreConditionsException if the {@code flagName} is blank or null
   * @return true only if the flag is present
   */
  public boolean hasFlag(String flagName) {
    PreConditions.requireNotNull(flagName, "Flag name must not be null")
        .andNot(flagName.isBlank(), "Flag name must not be blank");

    return flags.contains(flagName.toLowerCase());
  }
}
