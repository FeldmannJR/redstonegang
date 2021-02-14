package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPacoteAleatorio;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Aleatorio extends RedstoneCmd {
    public static IntegerArgument PRECO = new IntegerArgument("preco", false, 0, Integer.MAX_VALUE);
    public static StringArgument NOME = new RemainStringsArgument("nome", false);


    ShopAddon addon;

    public Aleatorio(ShopAddon addon) {
        super("aleatorio", "cria um pacote que vem outro pacote aleatorio", PRECO, NOME);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ShopPacoteAleatorio pacote = new ShopPacoteAleatorio();
        pacote.preco = args.get(PRECO);
        pacote.nome = args.get(NOME);
        pacote.addon = addon;
        pacote.selectKits((Player) sender);
        addon.add(pacote);
        C.info(sender, "Pacote criado com sucesso!");
    }
}
