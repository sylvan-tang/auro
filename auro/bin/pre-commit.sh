#!/bin/bash
sbt "scalafix RemoveUnused"
git add .
