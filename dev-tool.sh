#!/bin/bash

BOLD="\033[1m"
BLUE="\033[34m"
CYAN="\033[36m"
GRAY="\033[90m"
YELLOW="\033[33m"
GREEN="\033[32m"
RESET="\033[0m"

clear
echo -e "${GREEN}┌──────────────────────────────────────────────┐${RESET}"
echo -e "${GREEN}│${RESET}  ${BOLD}Dev Tools Started${RESET}${GREEN}                           |${RESET}"
echo -e "${GREEN}└──────────────────────────────────────────────┘${RESET}"

ACTIVE_MODULE=""
OLD_PS1=$PS1
export PS1="\[\033[1;36m\][dev-tools:\[\033[0;32m\]root\[\033[1;36m\]]\[\033[0m\] > "

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
    export PS1="\[\033[1;36m\][dev-tools:\[\033[0;32m\]$ACTIVE_MODULE\[\033[1;36m\]]\[\033[0m\] > "
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
    export PS1="$\[\033[1;36m\][dev-tools:\[\033[0;32m\]root\[\033[1;36m\]]\[\033[0m\] > "
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
    echo -e "${BOLD}${CYAN}Available Commands:${RESET}"
    echo -e "  🆘  ${BOLD}help${RESET}         ${GRAY}Show this help message.${RESET}"
    echo -e "  📦  ${BOLD}list${RESET}         ${GRAY}List available modules.${RESET}"
    echo -e "  🎯  ${BOLD}choose <mod>${RESET} ${GRAY}Select a module (e.g., 'choose 01-shapes').${RESET}"
    echo -e "                   ${GRAY}Tab-completion is enabled.${RESET}"
    echo -e "  📍  ${BOLD}current${RESET}      ${GRAY}Show the current module.${RESET}"
    echo -e "  ⬅️  ${BOLD}back${RESET}         ${GRAY}Go back to root module.${RESET}"
    echo -e "  🛠️  ${BOLD}build${RESET}        ${GRAY}Build the project / selected module.${RESET}"
    echo -e "  ✅  ${BOLD}verify${RESET}       ${GRAY}Run tests and open results in the browser.${RESET}"
    echo -e "  🚀  ${BOLD}run [args]${RESET}   ${GRAY}Run the selected module, passing [args].${RESET}"
    echo -e "  💾  ${BOLD}save${RESET}         ${GRAY}Commit current changes and open editor.${RESET}"
    echo -e "  ❌  ${BOLD}exit${RESET}         ${GRAY}Exit the console.${RESET}"
}

# Exit the console
exit() {
    export PS1=$OLD_PS1

    unset ACTIVE_MODULE
    unset OLD_PS1
    unset -f list choose back build verify run current help _choose_completion exit
    complete -r choose
    
    echo -e "${GREEN}┌──────────────────────────────────────────────┐${RESET}"
    echo -e "${GREEN}│${RESET}  ${BOLD}Dev Tools Stopped  ${RESET}${GREEN}                         |${RESET}"
    echo -e "${GREEN}└──────────────────────────────────────────────┘${RESET}"
}


# Register the completion function for the 'choose' command
complete -F _choose_completion choose

echo ""
help
echo ""
