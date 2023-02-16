#!/bin/bash
set -e
for server_name in $(ls cmd); do
  go build -o bin/${server_name} ./cmd/${server_name}
done
