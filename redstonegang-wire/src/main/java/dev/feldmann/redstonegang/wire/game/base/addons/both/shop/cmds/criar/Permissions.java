package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.cmds.criar;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos.ShopPermission;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Permissions extends RedstoneCmd {
    public static IntegerArgument PRECO = new IntegerArgument("preco", false, 0, Integer.MAX_VALUE);
    public static StringArgument NOME = new RemainStringsArgument("nome", false);
    public static StringArgument PERMISSION = new StringArgument("permission", false);


    ShopAddon addon;

    public Permissions(ShopAddon addon) {
        super("permission", "cria uma permissão", PRECO, PERMISSION, NOME);
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ShopPermission pacote = new ShopPermission();
        pacote.preco = args.get(PRECO);
        pacote.nome = args.get(NOME);
        pacote.permission = args.get(PERMISSION);
        pacote.addon = addon;
        addon.add(pacote);
        C.info(sender, "Pacote criado com sucesso!");
    }
}
