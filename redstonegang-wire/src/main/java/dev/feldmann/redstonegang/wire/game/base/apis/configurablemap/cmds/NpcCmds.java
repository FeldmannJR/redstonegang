package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.menus.SelectNpcMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NpcCmds extends RedstoneCmd {
    ConfigurableMapAPI api;

    public NpcCmds(ConfigurableMapAPI api) {
        super("npcs", "ve os npcs para setar");
        this.api = api;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new SelectNpcMenu(api).open((Player) sender);
    }
}
