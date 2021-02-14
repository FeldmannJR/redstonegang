package dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ServersRecord;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdMinerar extends RedstoneCmd {
    Survival surv;

    public CmdMinerar(Survival surv) {
        super("minerar", "vai pro minerar");
        this.surv = surv;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ServersRecord minerar = RedstoneGang.instance().servers().database().get("Survival-Minerar");
        if (minerar != null) {
            if (minerar.getOnline() == 1) {
                surv.teleportToServer((Player) sender, "Survival-Minerar");
                return;
            }
        }
        C.error(sender, "Minerar reiniciando!");
    }
}
