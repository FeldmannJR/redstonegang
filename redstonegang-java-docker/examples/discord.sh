#!/bin/sh
. "$( cd "$(dirname "$0")" ; pwd -P )/config.sh"

#Configs
ram=512m

docker run --rm -it --name discord \
    $(echo $defaultArguments) \
    -e SERVER_NAME="discord" \
    -e JAVA_DEBUG="1" \
    -e SERVER_DEBUG="true" \
    -e SERVER_DEV="1" \
    -p 5010:5005 \
    -m $ram \
    redstonegang-discord