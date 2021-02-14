package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.subs;

import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUnit;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.TimeArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.menus.CriarKitMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Criar extends RedstoneCmd {

    private static final StringArgument NAME = new StringArgument("nome kit", false);
    private static final TimeArgument CD = new TimeArgument("cooldown em min", TimeUnit.MINUTES);

    KitsAddon manager;

    public Criar(KitsAddon manager) {
        super("criar", "comando utilizado para criar um kit", NAME, CD);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        if (!StringUtils.isOnlyNumbersAndLetters(args.get(NAME))) {
            C.error(sender, "Não pode usar caracter especiais!");
            return;
        }
        if (manager.getKits().contains(args.get(NAME))) {
            C.error(sender, "Já existe um kit com o nome %%", args.get(NAME));
            return;
        }

        new CriarKitMenu(manager, args.get(NAME), args.get(CD)).open((Player) sender);
    }
}
