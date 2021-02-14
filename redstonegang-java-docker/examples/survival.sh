#!/bin/sh
. "$( cd "$(dirname "$0")" ; pwd -P )/config.sh"


# Criando um volume

docker run --rm -it --name Survival-Spawn \
    $(echo $defaultArguments) \
    -e SERVER_NAME="Survival-Spawn" \
    -e SERVER_DEBUG="true" \
    -e SERVER_DEV="1" \
    -e SPIGOT_GAME_NAME="SURVIVAL_TERRENOS" \
    -e SPIGOT_USE_WORLD_EDIT="1" \
    -e SPIGOT_USE_WORLD_GUARD="1" \
    -e JAVA_DEBUG="1" \
    -p 5005:5005 \
    -m 1512m \
    redstonegang-spigot