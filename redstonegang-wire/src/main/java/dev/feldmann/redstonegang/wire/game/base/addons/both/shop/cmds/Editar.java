package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuListPacotes;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Editar extends RedstoneCmd {
    ShopAddon addon;

    public Editar(ShopAddon addon) {
        super("editar", "abre um menu para editar os pacotes e os slots");
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new MenuListPacotes(addon).open((Player) sender);
    }
}
