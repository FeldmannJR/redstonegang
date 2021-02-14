#!/bin/bash
source .env

docker run -it --rm \
    --name discord \
    -v `pwd`/jars/:"/builds/jars/" \
    -v $SOURCE_DIR:$SOURCE_DIR \
    --network redstonegang-network \
    -e SERVER_NAME=discord \
    -e SERVER_DEBUG="true" \
    -e JAVA_DEBUG="1" \
    -p 5010:5005 \
    -e SERVER_DEV="true" \
    -e SERVER_ENVIRONMENT="local" \
    -e DISCORD_BOT_TOKEN="NjAwODU5NzY5MTY2NjI2ODE4.XS557A.mIjMfrpmDdXSwpF4h0eLeXO6fgo" \
    redstonegang-discord 
