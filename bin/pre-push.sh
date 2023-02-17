#!/bin/bash
ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && cd .. && pwd)"
make sonar-scan
