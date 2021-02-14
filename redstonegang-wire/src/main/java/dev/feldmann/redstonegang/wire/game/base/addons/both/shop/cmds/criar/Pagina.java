package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPage;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Pagina extends RedstoneCmd {
    public static IntegerArgument LINHAS = new IntegerArgument("linhas", false, 1, 6);
    public static StringArgument NOME = new RemainStringsArgument("nome", false);


    ShopAddon addon;

    public Pagina(ShopAddon addon) {
        super("pagina", "Cria uma pagina da loja", LINHAS, NOME);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ShopPage page = new ShopPage();
        page.nome = args.get(NOME);
        page.lines = args.get(LINHAS);
        addon.add(page);
        C.info(sender, "Pagina criada com sucesso!");
    }
}
