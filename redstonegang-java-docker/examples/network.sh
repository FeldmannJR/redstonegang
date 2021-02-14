#!/bin/sh
. "$( cd "$(dirname "$0")" ; pwd -P )/config.sh"

#Configs
port=9000
ram=512m


docker run -it --name network \
     --rm \
     $(echo $defaultArguments) \
    -e SERVER_NAME="network" \
    -e SERVER_DEBUG="true" \
    -e SERVER_DEV="1" \
    -e NETWORK_PORT="${port}" \
    -p $port:$port \
    --hostname="network" \
    -m $ram \
    redstonegang-network