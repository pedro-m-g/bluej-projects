#!/usr/bin/env bash
set -euo pipefail

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

echo "[build] Compiling full project..."
if ! mvn clean package; then
  echo "[build] Build failed ❌" >&2
  exit 1
fi
