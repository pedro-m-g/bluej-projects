#!/bin/bash

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

echo "[test] Compiling full project..."
mvn test || { echo "[test] Build failed"; exit 1; }
