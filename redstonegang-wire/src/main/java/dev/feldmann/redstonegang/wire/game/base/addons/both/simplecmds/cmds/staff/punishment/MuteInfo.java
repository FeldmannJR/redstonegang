package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff.punishment;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

import java.util.List;

public class MuteInfo extends SimpleCmd
{
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("jogador", false);

    public MuteInfo()
    {
        super("muteinfo", "Mostra as punições de mudo que o jogador possui.", "muteinfo", PLAYER);
    }

    @Override
    public void command(Player p, Arguments a)
    {
        User punishedUser = a.get(PLAYER);
        List<Punishment> mutes =  RedstoneGang.instance().user().getPunishment().getMutesPunishments(punishedUser);

        if(mutes.isEmpty())
        {
            C.info(p, "Nenhum mute foi encontrado.");
            return;
        }

        C.info(p,"-=-=-=-=-=-=-=-=-=-");
        for(Punishment punishment : mutes)
        {
            C.info(p,"%% - (%%) [%%]: %%",
                    punishment.getData().getId(),
                    punishment.getData().getStart().toString(),
                    punishment.getReason().getData().getName(),
                    punishment.getData().getPunishmentNote()
            );
        }
        C.info(p, "-=-=-=-=-=-=-=-=-=-");
    }
}
