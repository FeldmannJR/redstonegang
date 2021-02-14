package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import org.bukkit.command.CommandSender;

public class Criar extends RedstoneCmd {
    ShopAddon addon;

    public Criar(ShopAddon addon) {
        super("criar", "cria um pacote novo");
        this.addon = addon;
        addCmd(new Itens(addon));
        addCmd(new Vip(addon));
        addCmd(new Aleatorio(addon));
        addCmd(new Coins(addon));
        addCmd(new Permissions(addon));
        addCmd(new Pagina(addon));
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
