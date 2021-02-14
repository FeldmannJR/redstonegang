#!/bin/bash
# Path do script
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"

. $SCRIPTPATH/.env

# Lugar onde está os jars pra montar no sistema
jar_dir="$SCRIPTPATH/jars"

defaultArguments="--env SERVER_API_URL=http://app.rg/api/ \
                   -e SERVER_API_TOKEN=$API_TOKEN \
                    --network redstonegang-network  \
                    -v $jar_dir:/jars \
                    -v $sourceCodeFolder:$sourceCodeFolder "
