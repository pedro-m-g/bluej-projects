#!/bin/bash

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

MODULE="$1"

echo "[test] Compiling module $MODULE..."
mvn -pl "$MODULE" test || { echo "[test] Build failed"; exit 1; }
