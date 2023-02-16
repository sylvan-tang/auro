#!/usr/bin/env bash
set -ex

ROOT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

python3 $ROOT_PATH/bin/generate_ci_workflow.py $ROOT_PATH/.github/workflow-templates $ROOT_PATH/.github/workflow-conf.yml $ROOT_PATH/.github/workflows $ROOT_PATH
