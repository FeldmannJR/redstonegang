#!/bin/sh
. "$( cd "$(dirname "$0")" ; pwd -P )/config.sh"


docker run -it --rm --name Hub-1 \
     $(echo $defaultArguments) \
    -e SERVER_NAME="Hub-1" \
    -e SERVER_DEBUG="true" \
    -e SERVER_DEV="1" \
    -e SPIGOT_GAME_NAME="HUB" \
    -e JAVA_DEBUG="1" \
    -p 5006:5005 \
    -m 1512m \
    redstonegang-spigot