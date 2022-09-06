#!/bin/bash

mkdir /tmp/blank
rsync --delete-before -d /tmp/blank/ "/tmp/smalls/"
