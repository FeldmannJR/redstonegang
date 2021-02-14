FROM redstonegang-java
#redstonegang-spigot

# Sobreescrevendo os envs
ENV JAVA_JAR_FILE="spigot-1.8.8-R0.1-SNAPSHOT.jar"
ENV JAVA_ARGUMENTS="-XX:+AlwaysPreTouch -XX:+DisableExplicitGC -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=45 -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCLiveThresholdPercent=50 -XX:+AggressiveOpts"

# Copiando arquivos do servidor
COPY files/ /server/
COPY plugins/ /server/available-plugins/

## Spigot specific settings
# Se vai usar o plugin world guard
ENV PLUGIN_WORLD_GUARD=false
# ||
ENV PLUGIN_WORLD_EDIT=false

ENV PLUGIN_MAPPER_PLUGINS=false
# Plugin pra gerar o mapa
ENV PLUGIN_WORLD_BORDER=false


ENV PROPERTIES_LEVEL_NAME="world"
ENV PROPERTIES_LEVEL_SEED=""
ENV PROPERTIES_DIFFICULTY="1"
ENV PROPERTIES_LEVEL_TYPE="DEFAULT"
ENV PROPERTIES_GAMEMODE="0"
ENV PROPERTIES_GENERATOR_SETTINGS=""
ENV PROPERTIES_SPAWN_ANIMALS="true"

# O nome do game que vai iniciar 
ENV SPIGOT_GAME_NAME=""


EXPOSE 25565