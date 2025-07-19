#!/usr/bin/env bash
set -euo pipefail

# Change working directory to match project root
cd "$(dirname "$0")/.." || exit 1

echo echo "[test] Testing full project..."
exec mvn test
