FROM redstonegang-spigot

ENV PLUGIN_WORLD_EDIT=false
ENV PLUGIN_WORLD_GUARD=false
ENV PLUGIN_HOLOGRAPHIC_DISPLAYS=false
#
ENV PLUGIN_WORLD_BORDER=true
#
ENV SPIGOT_GAME_NAME=WORLDGEN
#
ENV PROPERTIES_LEVEL_NAME=world
ENV PROPERTIES_DIFFICULTY=0
#
COPY default-plugins /server/plugins/

ADD autoexec_world.sh /server/
ADD world/ /server/default-world

