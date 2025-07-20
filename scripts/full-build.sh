#!/usr/bin/env bash
set -euo pipefail

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

echo "[build] Compiling full project..."
exec mvn -B clean package
