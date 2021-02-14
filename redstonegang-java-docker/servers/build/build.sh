#!/bin/sh
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
set -x
docker build $SCRIPTPATH \
    -t redstonegang-build
