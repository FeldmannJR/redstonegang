package dev.feldmann.redstonegang.wire.permissions.user;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.permissions.PermissionBaseRG;
import dev.feldmann.redstonegang.wire.permissions.events.PlayerChangeGroupEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class SetGroup extends RedstoneCmd {

    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("user", false);
    private static final StringArgument GROUP = new StringArgument("group", false);


    public SetGroup() {
        super("setgroup", "Seta um grupo para um jogador", PLAYER, GROUP);
    }

    @Override
    public void command(CommandSender sender, Arguments arg) {
        User pl = arg.getValue(PLAYER);
        String group = arg.getValue(GROUP);
        Group old = pl.permissions().getGroup();

        if (pl.permissions().setGroup(group)) {
            Group newgroup = pl.permissions().getGroup();
            Wire.callEvent(new PlayerChangeGroupEvent(pl, old, newgroup));
            C.send(sender, MsgType.INFO, "Setado grupo %% para %%!", group, pl);
            if (Bukkit.getPlayer(pl.getUuid()) != null) {
                Player p = Bukkit.getPlayer(pl.getUuid());
                PermissionBaseRG.updateOp(p);
            }
        } else {
            C.error(sender, "Grupo %% não existe neste servidor!", group);
        }
    }


}
