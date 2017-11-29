#!/usr/bin/env bash

set -e

cd "$(dirname "${BASH_SOURCE[0]}")"; cd ..

lein with-profile +demo do clean, cljsbuild once

./scripts/dev-server.sh
