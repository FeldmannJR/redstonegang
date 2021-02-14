#!/bin/bash
source .env

docker run -it --rm \
    --name Login \
    -v `pwd`/jars/:"/builds/jars/" \
    -v $SOURCE_DIR:$SOURCE_DIR \
    --network redstonegang-network \
    -e SERVER_NAME=Login \
    -e SERVER_DEBUG="true" \
    -e JAVA_DEBUG="1" \
    -p 5011:5005 \
    -e SERVER_DEV="true" \
    -e SERVER_ENVIRONMENT="local" \
    -e SPIGOT_GAME_NAME="LOGIN" \
    -m 512m \
    redstonegang-spigot 
