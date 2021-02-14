package dev.feldmann.redstonegang.wire.integrations;

import dev.feldmann.redstonegang.wire.utils.player.Title;
import com.google.common.collect.Sets;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;


public class TitleGreetingHandler extends Handler {

    public static WorldGuardPlugin plugin;
    public static final Factory FACTORY = new Factory();

    public static class Factory extends Handler.Factory<TitleGreetingHandler> {
        @Override
        public TitleGreetingHandler create(Session session) {
            return new TitleGreetingHandler(session);
        }
    }

    private Set<String> lastMessageStackTitle = Collections.emptySet();


    public TitleGreetingHandler(Session session) {
        super(session);
    }


    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        LocalPlayer local = WorldGuardPlugin.inst().wrapPlayer(player);
        Collection<String> messages = toSet.queryAllValues(local, CustomFlags.GREETING_TITLE);
        String title = null;
        String subtitle = null;
        for (String message : messages) {
            if (!lastMessageStackTitle.contains(message)) {
                String effective = ChatColor.translateAlternateColorCodes('&', message);
                String[] m = effective.replaceAll("\\\\n", "\n").split("\\n");
                if (m.length >= 1) {
                    title = WorldGuardPlugin.inst().replaceMacros(player, m[0]);
                }
                if (m.length >= 2) {
                    subtitle = WorldGuardPlugin.inst().replaceMacros(player, m[1]);
                }
                break;
            }
        }
        if (title != null || subtitle != null)
            Title.sendTitle(player, title, subtitle, 5, 30, 5);

        lastMessageStackTitle = Sets.newHashSet(messages);
        if (!lastMessageStackTitle.isEmpty()) {
            // Due to flag priorities, we have to collect the lower
            // priority flag values separately
            for (ProtectedRegion region : toSet) {
                String message = region.getFlag(CustomFlags.GREETING_TITLE);
                if (message != null) {
                    lastMessageStackTitle.add(message);
                }
            }
        }

        return true;
    }


}