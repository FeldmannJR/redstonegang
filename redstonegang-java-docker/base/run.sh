#!/bin/bash
# Se precisar da pra sobreescrever esses valores no autoexec
jarArgs="$JAVA_JAR_ARGUMENTS"
executable="$JAVA_JAR_FILE"
args="$JAVA_ARGUMENTS"
TMUX_SESSION=server

copyDependencies(){
    source ./copyJars.sh
    for i in `find . -type f -iname autoexec\*.sh`
    do
        echo "Runing $i"
        . $i
    done
}
runServer(){
    if [ "$JAVA_DEBUG" == "1" ]; then
        args="$args -agentlib:jdwp=transport=dt_socket,server=y,address=5005,suspend=n "
    fi
    tmux new -s $TMUX_SESSION java -XX:InitialRAMPercentage=${JAVA_MIN_PERCENTAGE} -XX:MaxRAMPercentage=${JAVA_MAX_PERCENTAGE} $(echo $args) -jar $executable $jarArgs
}
console_command() {
    COMMAND=$@
    if [ "$TERM" == "dumb" ]; then
        >&2 echo "Console command not supported on a dumb term."
        exit 1
    else
        echo "Executing console command: ${COMMAND[@]}"
        tmux send -l -t $TMUX_SESSION "${COMMAND[@]}"
        tmux send -t $TMUX_SESSION ENTER
    fi
}


safe_shutdown(){
    console_command "${SERVER_STOP_COMMAND}"
}

trap safe_shutdown EXIT
copyDependencies
runServer