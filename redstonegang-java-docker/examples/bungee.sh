#!/bin/sh
. "$( cd "$(dirname "$0")" ; pwd -P )/config.sh"

#Configs
ram=512m

docker rm Bungee-1
docker run -it --name Bungee-1 \
    --restart unless-stopped \
    -v redstonegang-builds:/builds
    --network redstonegang-network
    -e SERVER_NAME="Bungee-1" \
    -p 25568:25565 \
    -m 1024m \
    redstonegang-bungee