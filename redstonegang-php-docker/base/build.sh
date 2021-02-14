#!/bin/sh
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
docker build $SCRIPTPATH -t redstonegang-php