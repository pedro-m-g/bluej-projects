#!/bin/bash

clear
echo "------------------------- [ Dev Tool: Start ] -------------------------"
echo ""

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
    export PS1="$OLD_PS1[$ACTIVE_MODULE] > "
  else
    echo "Invalid module: $1" >&2
    return 1
  fi
}

# Go back to root module
back() {
  if [ -z "$ACTIVE_MODULE" ]; then
    echo "Already at root module"
  else
    echo "Exiting module: $ACTIVE_MODULE"
    unset ACTIVE_MODULE
    export PS1=$OLD_PS1
  fi
}

# Build the selected module
build() {
  if [ -z "$ACTIVE_MODULE" ]; then
    mvn clean install
  else
    mvn -pl "$ACTIVE_MODULE" -am clean install
  fi
}

# Test the selected module
verify() {
  if [ -z "$ACTIVE_MODULE" ]; then
    echo "Choose a module first" >&2
    return 1
  fi
  mvn -pl "$ACTIVE_MODULE" surefire-report:report
  start "$ACTIVE_MODULE/target/site/surefire-report.html"
}

# Run the selected module
run() {
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

# Commits current changes and opens editor for commit message
save() {
    git add .
    git commit
}

# Help function
help() {
    cat <<'EOF'
Available Commands:
  help          Show this help message.
  list          List available modules.
  choose <mod>  Select a module (e.g., 'choose 01-shapes').
                Tab-completion is enabled.
  current       Show the current module.
  back          Go back to root module.
  build         Build the project / selected module.
  verify        Run tests and open results in the browser for the
                selected module.
  run [args]    Run the selected module, passing [args] to the
                application.
  save          Commits current changes and opens editor for commit
                message.
  exit          Exit the console.
EOF
}

# Exit the console
exit() {
    unset ACTIVE_MODULE
    unset -f list choose back build verify run current help _choose_completion exit
    complete -r choose
    export PS1=$OLD_PS1
    echo ""
    echo "-------------------------- [ Dev Tool: Stop ] -------------------------"
}


# Register the completion function for the 'choose' command
complete -F _choose_completion choose

help
