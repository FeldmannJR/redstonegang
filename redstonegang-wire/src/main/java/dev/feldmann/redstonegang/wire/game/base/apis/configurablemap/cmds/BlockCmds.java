package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.menus.SelectBlockMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockCmds extends RedstoneCmd {
    ConfigurableMapAPI api;

    public BlockCmds(ConfigurableMapAPI api) {
        super("blocks", "ve os blocos pra setar");
        this.api = api;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new SelectBlockMenu(api).open((Player) sender);
    }
}
