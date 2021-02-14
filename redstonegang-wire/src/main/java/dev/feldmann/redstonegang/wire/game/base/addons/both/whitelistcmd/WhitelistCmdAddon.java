package dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd;

import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.cmd.CmdWhitelistCmd;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;

public class WhitelistCmdAddon extends Addon {
    WhitelistCmdDB database;

    HashSet<String> allowed;

    public WhitelistCmdAddon(String database) {
        this.database = new WhitelistCmdDB(database);
    }

    @Override
    public void onEnable() {
        allowed = database.loadAllowed();
        registerCommand(new CmdWhitelistCmd(this));
    }

    public boolean isWhitelisted(String cmd) {
        return allowed.contains(cmd);
    }

    public HashSet<String> getWhitelisted() {
        return allowed;
    }

    public void addCommand(String command) {
        database.addAllowedCommand(command);
        allowed.add(command);
    }

    public void removeCommand(String command) {
        database.removeAllowedCommand(command);
        allowed.remove(command);
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.MIN_5) {
            // A cada 5 minutos recarrega os permitidos é leve foda-se
            allowed = database.loadAllowed();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void whitelist(PlayerCommandPreprocessEvent ev) {
        if (ev.isCancelled()) return;
        String message = ev.getMessage();
        if (message.length() <= 1 || message.charAt(0) != '/') {
            return;
        }
        String command = message.substring(1).toLowerCase();
        if (command.contains(" ")) {
            command = command.substring(0, message.indexOf(' ')).trim();
        }
        if (command.equals("help")) {
            ev.setMessage("/ajuda");
            return;
        }
        if (!isWhitelisted(command) && !ev.getPlayer().isOp()) {
            C.error(ev.getPlayer(), "Comando não encontrado use %cmd% !", "/ajuda");
            ev.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerTabComplete(PlayerChatTabCompleteEvent ev) {
        // Desabilitado no spigot.yml
    }
}
