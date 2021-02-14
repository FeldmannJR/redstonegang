package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopCoins;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Coins extends RedstoneCmd {
    public static IntegerArgument PRECO = new IntegerArgument("preco", false, 0, Integer.MAX_VALUE);
    public static StringArgument NOME = new RemainStringsArgument("nome", false);
    public static IntegerArgument COINS = new IntegerArgument("coins", false, 1, Integer.MAX_VALUE);


    ShopAddon addon;

    public Coins(ShopAddon addon) {
        super("coins", "cria um pacote de coins", PRECO, COINS, NOME);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ShopCoins pacote = new ShopCoins();
        pacote.preco = args.get(PRECO);
        pacote.nome = args.get(NOME);
        pacote.coins = args.get(COINS);
        pacote.addon = addon;
        addon.add(pacote);
        C.info(sender, "Pacote criado com sucesso!");
    }
}
