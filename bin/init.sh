#!/bin/bash
git config user.name sylvan
git config user.email sylvan2future@gmail.com

cp ./bin/pre-commit.sh ./.git/hooks/pre-commit
cp ./bin/pre-push.sh ./.git/hooks/pre-push
