package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.punishment;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.common.punishment.PunishmentReason;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

import java.util.List;

public class BanInfo extends SimpleCmd
{
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);

    public BanInfo()
    {
        super("baninfo", "Mostra as punições de banimento que o jogador possui.", "baninfo", PLAYER);
    }

    @Override
    public void command(Player p, Arguments a)
    {
        User punishedUser = a.get(PLAYER);
        List<Punishment> bans =  RedstoneGang.instance().user().getPunishment().getBansPunishments(punishedUser);

        if(bans.isEmpty())
        {
            C.info(p, "Nenhum ban foi encontrado.");
            return;
        }

        C.info(p,"-=-=-=-=-=-=-=-=-=-");
        for(Punishment punishment : bans)
        {
            MsgType msgType = MsgType.INFO;
            if (punishment.isActive())
            {
                msgType = MsgType.ERROR;
            }
            C.send(p, msgType,"%%: %% (%%) [%%]: %%",
                    punishment.getData().getId(),
                    RedstoneGang.getPlayer(punishment.getData().getOpPunishmentId()),
                    punishment.getData().getStart().toString(),
                    punishment.getReason().getData().getName(),
                    punishment.getData().getPunishmentNote()
            );
        }
        C.info(p, "-=-=-=-=-=-=-=-=-=-");
    }
}
