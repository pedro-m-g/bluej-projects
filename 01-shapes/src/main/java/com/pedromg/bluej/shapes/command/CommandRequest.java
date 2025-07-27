package com.pedromg.bluej.shapes.command;

import java.util.List;
import java.util.Set;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

public record CommandRequest(
        String action,
        List<String> params,
        Set<String> flags) {

    public CommandRequest {
        PreConditions
                .requireNonNull(action, "action must not be null")
                .check();
    }

    /**
     * Checks if a flag is present in this request
     *
     * @param flagName the flag to check
     *
     * @throws PreConditionsException if the {@code flagName} is blank or null
     *
     * @return true only if the flag is present
     */
    public boolean hasFlag(String flagName) {
        PreConditions
                .requireNonNull(flagName, "Flag name must not be null")
                .andNot(flagName.trim().isEmpty(), "Flag name must not be blank")
                .check();

        return flags.contains(flagName.toLowerCase());
    }

}
