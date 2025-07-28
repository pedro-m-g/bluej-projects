#!/bin/bash

clear
echo "
##################################################
#        _                   _              _    #
#       | |                 | |            | |   #
#     __| | _____   ________| |_ ___   ___ | |   #
#    / _  |/ _ \ \ / /______| __/ _ \ / _ \| |   #
#   | (_| |  __/\ V /       | || (_) | (_) | |   #
#    \__,_|\___| \_/         \__\___/ \___/|_|   #
#                                                #
##################################################
"

# --- Dev Tool ---
#
# To use, source this script in your shell:
#   $ source dev-tool.sh
#
# This will make the following commands available:
#   - list:   List available modules.
#   - choose: Select a module to work on (with tab-completion).
#   - build:  Build the selected module.
#   - start:  Run the selected module.
#   - help: Show this help message.

ACTIVE_MODULE=""
OLD_PS1=$PS1

# List available modules
list() {
  for d in [0-9][0-9]-*/ ; do
    [[ -d "$d" && -f "$d/pom.xml" ]] && printf '%s\n' "${d%/}"
  done
}

# Select a module to work on
choose() {
  if [ -z "$1" ]; then
    echo "Usage: choose <module>" >&2
    return 1
  fi

  if [ -d "$1" ] && [ -f "$1/pom.xml" ]; then
    ACTIVE_MODULE="$1"
    # Set PS1 to show the active module
    # No echo, to not pollute the output
    export PS1="($ACTIVE_MODULE) $ "
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
  mvn -pl "$ACTIVE_MODULE" -am clean install
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
    mapfile -t COMPREPLY < <(compgen -W "${opts}" -- "${cur}")
  fi

  return 0
}

# Show current module
current() {
    if [ -z "$ACTIVE_MODULE" ]; then
        echo "No module selected"
    else
        echo "Current module: $ACTIVE_MODULE"
    fi
}

# Help function
help() {
    cat <<'EOF'
Usage: source dev-tool.sh

Commands:
  list         List available modules.
  choose <mod> Select a module (e.g., 'choose 01-shapes'). Tab-completion is enabled.
  build        Build the selected module.
  start [args] Run the selected module, passing [args] to the application.
  current      Show the current module.
  help         Show this help message.
  exit         Exit the console.
EOF
}

# Exit the console
exit() {
    unset ACTIVE_MODULE
    unset -f list choose build start current help _choose_completion exit
    complete -r choose
    export PS1=$OLD_PS1
    echo "Dev tool unloaded"
}


# Register the completion function for the 'choose' command
complete -F _choose_completion choose

echo "Dev tool loaded. Type 'help' for commands."
