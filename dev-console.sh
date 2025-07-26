#!/bin/bash

# --- Maven Console Helper ---
#
# To use, source this script in your shell:
#   $ source console.sh
#
# This will make the following commands available:
#   - list:   List available modules.
#   - choose: Select a module to work on (with tab-completion).
#   - build:  Build the selected module.
#   - start:  Run the selected module.
#   - cs_help: Show this help message.

ACTIVE_MODULE=""

# List available modules
list() {
  ls -d */ | grep "^[0-9][0-9]-" | sed 's/\///'
}

# Select a module to work on
choose() {
  if [ -z "$1" ]; then
    echo "Usage: choose <module>" >&2
    return 1
  fi

  if [ -d "$1" ] && [ -f "$1/pom.xml" ]; then
    ACTIVE_MODULE="$1"
    echo "Module selected: $ACTIVE_MODULE"
    # Set PS1 to show the active module
    export PS1="($ACTIVE_MODULE) \w$ "
  else
    echo "Invalid module: $1" >&2
    return 1
  fi
}

# Build the selected module
build() {
  if [ -z "$ACTIVE_MODULE" ]; then
    echo "Choose a module first" >&2
    return 1
  fi
  mvn -pl "$ACTIVE_MODULE" -am install
}

# Run the selected module
start() {
  if [ -z "$ACTIVE_MODULE" ]; then
    echo "Choose a module first" >&2
    return 1
  fi
  mvn -pl "$ACTIVE_MODULE" exec:java -Dexec.args="$*"
}

# Autocompletion function for 'choose'
_choose_completion() {
  local cur opts
  COMPREPLY=()
  cur="${COMP_WORDS[COMP_CWORD]}"
  opts=$(list)

  if [ ${COMP_CWORD} -eq 1 ]; then
    COMPREPLY=( $(compgen -W "${opts}" -- ${cur}) )
  fi

  return 0
}

# Help function
cs_help() {
    echo "Maven Console Helper"
    echo ""
    echo "Usage: source console.sh"
    echo ""
    echo "Commands:"
    echo "  list         List available modules."
    echo "  choose <mod> Select a module (e.g., 'choose 01-shapes'). Tab-completion is enabled."
    echo "  build        Build the selected module."
    echo "  start [args] Run the selected module, passing [args] to the application."
    echo "  cs_help      Show this help message."
    echo ""
    echo "Current module: ${ACTIVE_MODULE:-None}"
}


# Register the completion function for the 'choose' command
complete -F _choose_completion choose

echo "Console script loaded. Type 'cs_help' for commands."