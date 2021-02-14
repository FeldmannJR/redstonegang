package dev.feldmann.redstonegang.repeater.commands;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.common.punishment.PunishmentReason;
import dev.feldmann.redstonegang.common.punishment.PunishmentReasonDuration;
import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class TesteCommand extends Command
{
    public TesteCommand()
    {
        super("teste");
    }

    @Override
    public void execute(CommandSender cs, String[] strings)
    {
        if (cs instanceof ProxiedPlayer)
        {
            ProxiedPlayer pp = (ProxiedPlayer) cs;
            User user = RedstoneGangBungee.getPlayer(pp);

            List<PunishmentReason> banReasons =  RedstoneGangBungee.instance().user().getPunishment().getBanReasons();
            for (PunishmentReason br : banReasons)
            {
                RedstoneGangBungee.instance().debug(br.getData().toString());
                for (PunishmentReasonDuration brd : br.getDurations())
                {
                    RedstoneGangBungee.instance().debug(brd.getData().toString());
                }
            }

            List<Punishment> punishments =  RedstoneGangBungee.instance().user().getPunishment().getPunishments(user);
            for (Punishment pm : punishments)
            {
                RedstoneGangBungee.instance().debug(pm.getData().toString());
                RedstoneGangBungee.instance().debug(pm.getReason().toString());
            }
        }
    }
}
