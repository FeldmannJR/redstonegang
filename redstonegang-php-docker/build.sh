#!/bin/sh
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
$SCRIPTPATH/base/build.sh
$SCRIPTPATH/cron/build.sh