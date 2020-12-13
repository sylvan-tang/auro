#!/bin/bash
sbt clean assembly && sbt coverageReport && sbt coverageAggregate
git add .
git commit --amend --no-edit
