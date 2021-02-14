package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SetDisplayName extends RedstoneCmd {

    private static final StringArgument NOME = new StringArgument("grupo");
    private static final StringArgument DISPLAY = new StringArgument("displayname", 1, 16);


    public SetDisplayName() {
        super("setdisplay", "seta o display name do grupo", NOME, DISPLAY);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String nome = args.get(NOME);

        Group g = RedstoneGang.instance.user().getPermissions().getGroupByName(nome);
        if (g == null) {
            C.error(sender, "Grupo não existe!");
            return;
        }
        g.setDisplayName(ChatColor.translateAlternateColorCodes('&', args.get(DISPLAY)));
        C.info(sender, "Display name setado para %%", g.getDisplayName());


    }
}
