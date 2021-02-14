package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.punishment;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.punishment.Punish;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.common.punishment.PunishmentReason;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

import java.util.List;

public class UnBan extends SimpleCmd
{
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);
    private static final OneOfThoseArgument REASON = new OneOfThoseArgument("motivo", false, Ban.getReasons(Punish.Reason.UNBAN));
    private static final StringArgument NOTE = new RemainStringsArgument("nota", true);

    public UnBan()
    {
        super("unban", "Remove uma punição de banimento do jogador.", "unban", PLAYER, REASON, NOTE);
    }

    @Override
    public void command(Player p, Arguments a)
    {
        User opUser = RedstoneGang.getPlayer(p.getUniqueId());
        User punishedUser = a.get(PLAYER);
        String reason_id = a.get(REASON);

        String note = "N/A";
        if (a.isPresent(NOTE))
        {
            note = a.get(NOTE);
        }

        PunishmentReason reason = Ban.whatIsThisReason(Punish.Reason.UNBAN, reason_id);
        if (reason == null)
        {
            C.error(p, "Motivo Inválido.");
            return;
        }

        if(RedstoneGang.instance().user().getPunishment().isBanned(punishedUser))
        {
            List<Punishment> punishments = RedstoneGang.instance().user().getPunishment().getBansPunishments(punishedUser);
            for (Punishment punishment : punishments)
            {
                if (punishment.isActive())
                {
                    boolean canRemove = punishment.getReason().getData().getCanRemove() != 0;
                    if (canRemove)
                    {
                        Punishment removedPunishment = RedstoneGang.instance().user().getPunishment().removePunishment(punishment, opUser, reason, note);
                        C.info(p, "- (%%) de %%, foi removido.", removedPunishment.getReason().getData().getNote(), punishedUser.getName());
                    }
                    else
                    {
                        C.error(p,"Este banimento não pode ser removido, contacte a Staff.");
                    }
                }
            }
        }
        else
        {
            C.info(p,"O jogador não está banido.");
        }
    }
}
