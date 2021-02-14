package dev.feldmann.redstonegang.wire.permissions.group;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.DoubleArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class SetOption extends RedstoneCmd {

    private static final StringArgument GROUP = new StringArgument("group");
    private static final StringArgument OPTION = new StringArgument("option");
    private static final DoubleArgument VALUE = new DoubleArgument("value", false, -1, Double.MAX_VALUE);


    public SetOption() {
        super("setoption", "Set uma option para o grupo", GROUP, OPTION, VALUE);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        String nome = args.get(GROUP);
        Group g = RedstoneGang.instance.user().getPermissions().getGroupByName(nome);
        if (g == null) {
            C.error(sender, "Grupo não existe!");
            return;
        }
        g.setOption(args.get(OPTION), args.get(VALUE));
        C.info(sender, "Option %% para %% setada!", args.get(OPTION), args.get(VALUE));

    }
}
