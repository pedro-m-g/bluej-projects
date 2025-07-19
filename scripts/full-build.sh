#!/bin/bash

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

echo "[build] Compiling full project..."
mvn clean package || { echo "[build] Build failed"; exit 1; }
