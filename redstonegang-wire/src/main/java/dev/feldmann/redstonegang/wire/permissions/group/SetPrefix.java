package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SetPrefix extends RedstoneCmd {

    private static final StringArgument NOME = new StringArgument("nome");
    private static final StringArgument PREFIX = new StringArgument("prefix", 1, 16);


    public SetPrefix() {
        super("setprefix", "", NOME, PREFIX);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String nome = args.get(NOME);

        Group g = RedstoneGang.instance.user().getPermissions().getGroupByName(nome);
        if (g == null) {
            C.error(sender, "Grupo não existe!");
            return;
        }
        g.setPrefix(ChatColor.translateAlternateColorCodes('&', args.get(PREFIX)));
        C.info(sender, "Prefixo setado para %%", g.getPrefix());


    }
}
