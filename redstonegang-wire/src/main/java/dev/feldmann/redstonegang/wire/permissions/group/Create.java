package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Create extends RedstoneCmd {

    private static final StringArgument NOME = new StringArgument("nome");
    private static final StringArgument PREFIX = new StringArgument("prefix", true, 1, 16);
    private static final StringArgument SUFFIX = new StringArgument("suffix", true, 1, 16);


    public Create() {
        super("create", "cria um grupo", NOME, PREFIX, SUFFIX);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String nome = args.get(NOME);
        String prefix = args.isPresent(PREFIX) ? ChatColor.translateAlternateColorCodes('&', args.getValue(PREFIX)) : null;
        String suffix = args.isPresent(SUFFIX) ? ChatColor.translateAlternateColorCodes('&', args.get(SUFFIX)) : "";

        if (RedstoneGang.instance.user().getPermissions().getDb().createGroup(nome, prefix, suffix, false)) {
            C.info(sender, "Grupo %% criado !", nome);
        } else {
            C.error(sender, "Grupo com o nome `%%` já existe !", nome);
        }
    }
}
