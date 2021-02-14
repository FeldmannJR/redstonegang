package dev.feldmann.redstonegang.wire.game.base.addons.server.restart;

import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.server.restart.cmd.RestartCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.server.restart.event.ServerRestartEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ServerRestartAddon extends Addon {

    private boolean restarting = false;
    private int currentTimer = 0;
    private int defaultRestart = 60 * 5;
    private MsgType RESTART = new MsgType("§a§l[Reinicio] §f", ChatColor.WHITE, ChatColor.YELLOW, ChatColor.YELLOW, ChatColor.YELLOW, ChatColor.YELLOW, ChatColor.YELLOW);


    @Override
    public void onEnable() {
        registerCommand(new RestartCmd(this));
    }

    public boolean restart() {
        return restart(defaultRestart);
    }

    public boolean restart(int time) {
        if (this.restarting) return false;
        this.currentTimer = time;
        this.restarting = true;
        return true;
    }

    public int getCurrentTimer() {
        return currentTimer;
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_1) {
            if (restarting) {
                if (currentTimer >= -5) {
                    onTimer(currentTimer);
                    currentTimer--;
                }
            }
        }
    }

    public boolean isRestarting() {
        return restarting;
    }

    public void teleportAll() {
        if (Wire.callEvent(new ServerRestartEvent())) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("§aServidor reiniciando :D");
        }
    }

    public void onTimer(int current) {
        if (current == 0) {
            teleportAll();
        }
        if (current == -5) {
            Bukkit.shutdown();
            currentTimer = 0;
            restarting = false;
        }
        if (current > 0) {
            if (current == 5 || current == 10 || current % 30 == 0) {
                String when;
                if (current % 60 == 0) {
                    when = (current / 60) + " minuto(s)";
                } else if (current >= 60 && current % 30 == 0) {
                    when = (current / 60) + " minuto(s) e meio";
                } else {
                    when = current + " segundos";
                }
                C.broadcast(this.RESTART, "O servidor irá reiniciar em %% !", when);
            }
        }
    }


    public void cancelRestart() {
        this.currentTimer = 0;
        this.restarting = false;
    }
}
