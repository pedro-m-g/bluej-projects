#!/usr/bin/env bash
set -euo pipefail

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

MODULE="${1:-}"

# Ensure a module was supplied
if [ -z "$MODULE" ]; then
	echo "[build] Error: No module specified."
	echo "Usage: $0 <module>"
	exit 1
fi

echo "[test] Testing module $MODULE..."
if ! mvn -pl "$MODULE" test; then
  echo "[test] Build failed ❌" >&2
  exit 1
fi
