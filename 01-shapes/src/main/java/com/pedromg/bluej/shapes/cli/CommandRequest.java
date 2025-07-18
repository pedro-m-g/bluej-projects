package com.pedromg.bluej.shapes.cli;

import java.util.List;
import java.util.Set;

public record CommandRequest(
    String action,
    List<String> params,
    Set<String> flags) {

}
