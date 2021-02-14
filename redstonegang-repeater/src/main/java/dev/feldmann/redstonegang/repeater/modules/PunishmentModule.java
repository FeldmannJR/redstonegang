package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import dev.feldmann.redstonegang.repeater.events.PlayerLoadEvent;
import net.md_5.bungee.event.EventHandler;

public class PunishmentModule extends Module
{
    @EventHandler
    public void postLogin(PlayerLoadEvent ev)
    {
        User user = ev.getPlayer();
        Punishment punishment = RedstoneGangBungee.instance().user().getPunishment().hasBanned(user);
        if (punishment != null)
        {

            if(punishment.getTimeLeft().getMinutes() == 0
                    && punishment.getData().getEnd() != null)
            {
                return;
            }

            ev.deny(RedstoneGangBungee.instance().user().getPunishment().getKickMsg(punishment));
        }
    }

}
