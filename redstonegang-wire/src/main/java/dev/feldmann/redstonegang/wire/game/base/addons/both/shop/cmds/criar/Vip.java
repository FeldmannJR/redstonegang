package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopVIP;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Vip extends RedstoneCmd {
    public static StringArgument LINK = new StringArgument("link", false);
    public static StringArgument NOME = new RemainStringsArgument("nome", false);

    ShopAddon addon;

    public Vip(ShopAddon addon) {
        super("vip", "cria um vip", LINK, NOME);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ShopVIP pacote = new ShopVIP();
        pacote.link = args.get(LINK);
        pacote.nome = args.get(NOME);
        pacote.addon = addon;
        addon.add(pacote);
        C.info(sender, "Pacote adicionado!");
    }

}
