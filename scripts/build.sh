#!/bin/bash

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

MODULE="$1"

echo "[build] Compiling module $MODULE..."
mvn -pl "$MODULE" clean package || { echo "[build] Build failed"; exit 1; }
