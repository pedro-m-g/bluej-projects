#!/usr/bin/env bash
set -euo pipefail

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

MODULE="${1:-}"

# Ensure a module was supplied
if [ -z "$MODULE" ]; then
	echo "[start] Error: No module specified."
	echo "Usage: $0 <module>"
	exit 1
fi

shift

echo "[start] Running module: $MODULE with args: $*"
if ! mvn -pl "$MODULE" exec:java -Dexec.args="$*"; then
  echo "[start] Build failed ❌" >&2
  exit 1
fi
