package dev.feldmann.redstonegang.wire.integrations;

import dev.feldmann.redstonegang.wire.utils.player.Title;
import com.google.common.collect.Sets;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.commands.CommandUtils;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;


public class TitleFarewellHandler extends Handler {

    public static final Factory FACTORY = new Factory();

    public static class Factory extends Handler.Factory<TitleFarewellHandler> {
        @Override
        public TitleFarewellHandler create(Session session) {
            return new TitleFarewellHandler(session);
        }
    }

    private Set<String> lastTitleStack = Collections.emptySet();

    public TitleFarewellHandler(Session session) {
        super(session);
    }

    private Set<String> getMessages(LocalPlayer player, ApplicableRegionSet set, Flag<String> flag) {
        return Sets.newLinkedHashSet(set.queryAllValues(player, flag));
    }

    @Override
    public void initialize(Player player, Location current, ApplicableRegionSet set) {
        LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);

        lastTitleStack = getMessages(lplayer, set, CustomFlags.FAREWELL_TITLE);
    }

    @Override
    public boolean onCrossBoundary(Player bplayer, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        lastTitleStack = collectAndSend(bplayer, toSet, CustomFlags.FAREWELL_TITLE, lastTitleStack, TitleFarewellHandler::sendStringToTitle);
        return true;
    }


    private Set<String> collectAndSend(Player player, ApplicableRegionSet toSet, Flag<String> flag,
                                       Set<String> stack, BiConsumer<Player, String> msgFunc) {
        LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);

        Set<String> messages = getMessages(lplayer, toSet, flag);

        if (!messages.isEmpty()) {
            // Due to flag priorities, we have to collect the lower
            // priority flag values separately
            for (ProtectedRegion region : toSet) {
                String message = region.getFlag(flag);
                if (message != null) {
                    messages.add(message);
                }
            }
        }

        for (String message : stack) {
            if (!messages.contains(message)) {
                msgFunc.accept(player, message);
                break;
            }
        }
        return messages;
    }

    public static void sendStringToTitle(Player player, String message) {
        String[] parts = message.replaceAll("\\\\n", "\n").split("\\n", 2);
        String title = CommandUtils.replaceColorMacros(parts[0]);
        title = WorldGuardPlugin.inst().replaceMacros(player, title);

        String subtitle = "";
        if (parts.length > 1) {
            subtitle = CommandUtils.replaceColorMacros(parts[1]);
            subtitle = WorldGuardPlugin.inst().replaceMacros(player, subtitle);
        }
        Title.sendTitle(player, title, subtitle, 5, 30, 5);

    }


}