package com.pedromg.bluej.shapes.command;

import java.util.List;
import java.util.Set;

import com.pedromg.bluej.shapes.validation.NotBlankRule;

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
        NotBlankRule.validate(
                flagName, "Flag name can not be blank");
        return flags.contains(flagName.toLowerCase());
    }

}
