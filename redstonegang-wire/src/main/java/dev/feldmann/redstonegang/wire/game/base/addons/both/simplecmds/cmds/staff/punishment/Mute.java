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
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Mute extends SimpleCmd
{
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);
    private static final OneOfThoseArgument REASON = new OneOfThoseArgument("motivo", false, Ban.getReasons(Punish.Reason.MUTE));
    private static final StringArgument NOTE = new RemainStringsArgument("nota", true);

    public Mute()
    {
        super("mute", "Gera uma punição de mudo para o jogador.", "mute", PLAYER, REASON, NOTE);
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

        PunishmentReason reason = Ban.whatIsThisReason(Punish.Reason.MUTE, reason_id);
        if (reason == null)
        {
            C.error(p, "Motivo Inválido.");
            return;
        }

        Punishment punishment = RedstoneGang.instance().user().getPunishment().newPunishment(
                Punish.MUTE,
                punishedUser,
                "",
                opUser,
                reason,
                note
        );

        if(punishedUser.isOnline())
        {
            punishedUser.sendMessage(ChatColor.RED + "[MUTE] Você foi mutado por " + punishment.getReason().getData().getNote() + ".");
        }

        C.info(p, "[MUTE] O jogador %% foi mutado por %%.", punishedUser.getName(), punishment.getReason().getData().getNote());
    }
}