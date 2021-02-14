#!/bin/sh
copyDefaultPlugins(){
    for jar in "Wire" "PacketListenerAPI_*" "citizens-*" ;do
        copyPlugin $jar
    done
}

copyWorldGuard(){
    copyPlugin "worldguard-*"
}

copyMapperPlugins(){
    cp -Lvfr /server/available-plugins/mapper/* /server/plugins/
}

copyWorldEdit(){
    copyPlugin "worldedit-*"
}

copySpigot(){
    if [ -f "${JAVA_JAR_FILE}" ]; then
    rm -rf /server/spigot.jar
    fi
    cp -Lvf /builds/jars/${JAVA_JAR_FILE} /server/
}


replaceProperty(){
    sed -i "s/\(${1}=\).*\$/\1${2}/" /server/server.properties
}


replaceProperties(){
     replaceProperty "level-name" $PROPERTIES_LEVEL_NAME
     replaceProperty "level-seed" $PROPERTIES_LEVEL_SEED
     replaceProperty "level-type" $PROPERTIES_LEVEL_TYPE
     replaceProperty "generator-settings" $PROPERTIES_GENERATOR_SETTINGS
     replaceProperty "spawn-animals" $PROPERTIES_SPAWN_ANIMALS
     replaceProperty "difficulty" $PROPERTIES_DIFFICULTY
     replaceProperty "gamemode" $PROPERTIES_GAMEMODE
     

}


copySpigot
copyDefaultPlugins
replaceProperties

if [ "${PLUGIN_WORLD_EDIT}" == "1" ] || [ "${PLUGIN_WORLD_EDIT}" == "true" ]; then
    copyWorldEdit
fi

if [ "$PLUGIN_WORLD_GUARD" == "1" ] || [ "$PLUGIN_WORLD_GUARD" == "true" ]; then
    copyWorldGuard
fi

if [ "$PLUGIN_MAPPER_PLUGINS" == "1" ] || [ "$PLUGIN_MAPPER_PLUGINS" == "true" ]; then
    copyMapperPlugins
fi

if [ "$PLUGIN_WORLD_BORDER"  == "1" ] || [ "$PLUGIN_WORLD_BORDER"  == "true" ]; then
    copyPluginAbsolute /server/available-plugins/WorldBorder
fi
if [ "$PLUGIN_MULTIVERSE"  == "1" ] || [ "$PLUGIN_MULTIVERSE"  == "true" ]; then
    copyPluginAbsolute /server/available-plugins/Multiverse
fi
if [ "$PLUGIN_HOLOGRAPHIC_DISPLAYS"  == "1" ] || [ "$PLUGIN_HOLOGRAPHIC_DISPLAYS"  == "true" ]; then
    copyPluginAbsolute /server/available-plugins/HolographicDisplays
fi



