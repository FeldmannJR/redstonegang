package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Listar extends LojaSubCmd {


    public Listar() {
        super("listar", "lista do os shops", false);
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        for (LojaNPC l : getManager().getLojas()) {
            BaseComponent[] base = new ComponentBuilder(l.getNPC().getId() + ". ").color(ChatColor.YELLOW).append(l.getNPC().getName() + "").color(ChatColor.WHITE).create();
            Location loc = l.getNPC().getStoredLocation();
            for (BaseComponent b : base) {
                b.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tppos " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ()));
            }
            ((Player) sender).spigot().sendMessage(base);
        }

    }
}
