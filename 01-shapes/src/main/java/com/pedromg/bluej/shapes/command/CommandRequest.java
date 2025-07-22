package com.pedromg.bluej.shapes.command;

import java.util.List;
import java.util.Set;

import com.pedromg.bluej.shapes.domain.Validation;

public record CommandRequest(
    String action,
    List<String> params,
    Set<String> flags) {

    /**
     * Checks if a flag is present in this request
     *
     * @param flagName the flag to check
     *
     * @return true only if the flag is present
     */
    public boolean hasFlag(String flagName) {
        Validation.notBlank(flagName, "Flag name");
        return flags.contains(flagName.toLowerCase());
    }

}
