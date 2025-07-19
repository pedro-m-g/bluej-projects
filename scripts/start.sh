#!/bin/bash

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

MODULE="$1"
shift

echo "[start] Running module: $MODULE with args: $*"
mvn -pl "$MODULE" exec:java -Dexec.args="$*"
