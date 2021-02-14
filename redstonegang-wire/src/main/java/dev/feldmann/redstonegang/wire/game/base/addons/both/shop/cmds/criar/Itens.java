package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopItemStack;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Itens extends RedstoneCmd {
    public static IntegerArgument PRECO = new IntegerArgument("preco", false, 0, Integer.MAX_VALUE);
    public static StringArgument NOME = new RemainStringsArgument("nome", false);


    ShopAddon addon;

    public Itens(ShopAddon addon) {
        super("itens", "cria um pacote de itens", PRECO, NOME);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ShopItemStack pacote = new ShopItemStack();
        pacote.preco = args.get(PRECO);
        pacote.nome = args.get(NOME);
        pacote.addon = addon;
        pacote.selectItens((Player) sender, true);
        C.info(sender, "Pacote criado com sucesso!");
    }
}
