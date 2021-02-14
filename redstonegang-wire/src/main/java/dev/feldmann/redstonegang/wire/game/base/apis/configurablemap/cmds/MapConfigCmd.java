package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import org.bukkit.command.CommandSender;

public class MapConfigCmd extends RedstoneCmd {
    ConfigurableMapAPI api;

    public MapConfigCmd(ConfigurableMapAPI api) {
        super("mapconfig", "edita as configs do mapa");
        addCmd(new NpcCmds(api));
        addCmd(new BlockCmds(api));
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
