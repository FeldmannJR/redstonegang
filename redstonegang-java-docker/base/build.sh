#!/bin/sh
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"

docker build $SCRIPTPATH -t redstonegang-java --build-arg API_TOKEN=${API_TOKEN:-'localapitoken'} --build-arg API_URL=${API_URL:-'http://api.app.rg/'}