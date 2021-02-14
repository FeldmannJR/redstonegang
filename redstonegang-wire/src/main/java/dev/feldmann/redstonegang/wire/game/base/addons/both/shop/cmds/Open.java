package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Open extends RedstoneCmd {
    ShopAddon addon;

    public Open(ShopAddon addon) {
        super("open", "abre a loja");
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        addon.openPlayer((Player) sender, -1);
    }
}
