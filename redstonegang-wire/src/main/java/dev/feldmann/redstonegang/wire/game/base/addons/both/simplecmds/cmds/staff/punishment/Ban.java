package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.punishment;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsReasonsRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.punishment.Punish;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.common.punishment.PunishmentReason;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Ban extends SimpleCmd
{
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);
    private static final OneOfThoseArgument REASON = new OneOfThoseArgument("motivo", false, getReasons(Punish.Reason.BAN));
    private static final StringArgument NOTE = new RemainStringsArgument("nota", true);

    public Ban()
    {
        super("ban", "Gera uma punição de banimento para o jogador.", "ban", PLAYER, REASON, NOTE);
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

        PunishmentReason reason = whatIsThisReason(Punish.Reason.BAN, reason_id);
        if (reason == null)
        {
            C.error(p, "Motivo Inválido.");
            return;
        }

        Punishment punishment = RedstoneGang.instance().user().getPunishment().newPunishment(
                Punish.BAN,
                punishedUser,
                "",
                opUser,
                reason,
                note
        );

        if(punishedUser.isOnline())
        {
            punishedUser.kick(new TextComponent(
                    TextComponent.fromLegacyText(
                            RedstoneGang.instance().user().getPunishment().getKickMsg(punishment)
                    )));
        }

        C.info(p, "[BAN] O jogador %% foi banido por %%.", punishedUser.getName(), reason.getData().getNote());
    }

    public static List<String> getReasons(Punish.Reason type)
    {
        List<String> reasons_id = new ArrayList();
        List<PunishmentReason> reasons = RedstoneGang.instance().user().getPunishment().getReasons(type);
        for (PunishmentReason reason : reasons)
        {
            String name = reason.getData().getName().replace(" ", "_");
            if (name.length() > 20)
            {
                name.substring(0, 20);
                name = name + "...";
            }
            reasons_id.add(reason.getData().getId() + "-" + name);
        }
        return reasons_id;
    }

    public static PunishmentReason whatIsThisReason(Punish.Reason type, String reason_name)
    {
        List<PunishmentReason> reasons = RedstoneGang.instance().user().getPunishment().getReasons(type);
        for (PunishmentReason reason : reasons)
        {
            int name_id = reason.getData().getId();
            int reason_id = Integer.valueOf(reason_name.split("-")[0]);
            if (name_id == reason_id)
            {
                return reason;
            }
        }
        return null;
    }
}
