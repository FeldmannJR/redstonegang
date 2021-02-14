package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.scoreboard;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanChangeTagEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanJoinEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.PvPAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event.PlayerAfterStartFreePvpEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.event.PlayerStopFreePvpEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.SafeTimeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.event.PlayerSafeTimeEndEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.event.PlayerSafeTimeStartEvent;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerChangeConfigEvent;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.modulos.scoreboard.ScoreboardManager;
import dev.feldmann.redstonegang.wire.permissions.events.PlayerChangeGroupEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class SurvivalScoreboard extends Addon {


    SideBar sidebar;
    public BooleanConfig USE_SIDEBAR;

    public SurvivalScoreboard() {
        this.sidebar = new SideBar(this);
    }

    @Override
    public void onEnable() {
        USE_SIDEBAR = new BooleanConfig("sidebar", true);
        addConfig(USE_SIDEBAR);
    }

    @Override
    public void onStart() {
        sidebar.setup();
        registerListener(sidebar);
    }

    public SideBar getSidebar() {
        return sidebar;
    }

    @EventHandler
    private void joinServer(PlayerJoinServerEvent ev) {
        ScoreboardManager.registerScoreboard(ev.getPlayer());
        update(ev.getPlayer());
        if (ev.getUser().getConfig(USE_SIDEBAR))
            this.sidebar.display(ev.getPlayer());

//        scheduler().runAfter(() -> {
//            if (ev.getPlayer() != null) {
//                ScoreboardManager.makeScore(ev.getPlayer(), DisplaySlot.BELOW_NAME, ev.getPlayer().getName(),
//                        (int) ev.getPlayer().getHealth());
//            }
//        }, 40);
    }

    public void update(Player p) {
        Clan c = a(ClanAddon.class).getCache().getMember(p).getClan();
        update(p, c != null && getUser(p).getConfig(a(ClanAddon.class).SUFFIX_CONFIG) && c.canUseTag());
    }

    public void update(Player p, boolean useClanTag) {
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            //Player que ja tava online é o viewer
            view(pOn, p, useClanTag);
            view(p, pOn, useClanTag);
        }
    }

    @EventHandler
    public void changeConfig(PlayerChangeConfigEvent ev) {
        if (ev.getConfig() == a(ClanAddon.class).SUFFIX_CONFIG) {
            update(ev.getPlayer(), (Boolean) ev.getNewvalue());
        }
    }

    @EventHandler
    public void clanJoin(ClanJoinEvent ev) {
        scheduler().runAfter(() -> {
            update(ev.getPlayer());
        }, 1);
    }

    @EventHandler
    public void changeGroup(PlayerChangeGroupEvent ev) {
        Player online = RedstoneGangSpigot.getOnlinePlayer(ev.getPlayer().getId());
        if (online != null) {
            update(online);
        }
    }

    @EventHandler
    public void changeTag(ClanChangeTagEvent ev) {
        for (Player p : ev.getClan().getOnlinePlayers()) {
            update(p);
        }
    }

    @EventHandler
    public void freePvpStart(PlayerAfterStartFreePvpEvent ev) {
        update(ev.getPlayer());
    }

    @EventHandler
    public void freePvpEnd(PlayerStopFreePvpEvent ev) {
        update(ev.getPlayer());
    }

    @EventHandler
    public void safeTimeEnd(PlayerSafeTimeEndEvent ev) {
        update(ev.getPlayer());
    }

    @EventHandler
    public void safeTimeStart(PlayerSafeTimeStartEvent ev) {
        update(ev.getPlayer());
    }

    private String getSorter(Player player, Player viewer) {
        User user = getUser(player);
        if (viewer == player) {
            return "a";
        }
        if (user.isStaff()) {
            return "f";
        }
        if (user.hasVip3()) {
            return "g";
        }
        if (user.hasVip2()) {
            return "h";
        }
        if (user.hasVip1()) {
            return "i";
        }
        if (hasAddon(SafeTimeAddon.class)) {
            if (a(SafeTimeAddon.class).isInSafetime(player)) {
                return "j";
            }
        }
        Clan playerClan = a(ClanAddon.class).getCache().getMember(player).getClan();
        Clan viewerClan = a(ClanAddon.class).getCache().getMember(viewer).getClan();
        if (playerClan != null && viewerClan != null) {
            if (playerClan.equals(viewerClan)) {
                return "k";
            }
        }
        return "z";
    }

    private String getNameColor(Player player, Player viewer) {
        User user = getUser(player);
        if (hasAddon(SafeTimeAddon.class)) {
            if (a(SafeTimeAddon.class).isInSafetime(player)) {
                return ChatColor.DARK_GREEN.toString();
            }
        }
        if (a(PvPAddon.class).isFreePvp(player)) {
            return ChatColor.DARK_RED.toString();
        }
        if (viewer == player) {
            return ChatColor.AQUA.toString();
        }
        Clan playerClan = a(ClanAddon.class).getCache().getMember(player).getClan();
        Clan viewerClan = a(ClanAddon.class).getCache().getMember(viewer).getClan();
        if (playerClan != null && viewerClan != null) {
            if (playerClan.equals(viewerClan)) {
                return ChatColor.BLUE.toString();
            }
        }
        if (user.isStaff()) {
            return ChatColor.GOLD.toString();
        }
        return ChatColor.WHITE.toString();
    }

    private void view(Player viewer, Player view, boolean useClanTag) {
        Clan c = a(ClanAddon.class).getCache().getMember(view).getClan();
        String suffix = (c != null && useClanTag) ? c.getColorTag() : "";
        String prefix = getUser(view).getPrefix();
        String color = getNameColor(view, viewer);
        String sorter = getSorter(view, viewer);
        String teamName = (view.getName().length() >= 16 ? view.getName().substring(0, 15) : view.getName()) + sorter;

        if (prefix.length() + color.length() > 16) {
            if (prefix.charAt(prefix.length() - 1) == ' ') {
                prefix = prefix.substring(0, 16 - color.length() - 1);
            } else {
                prefix = prefix.substring(0, 16 - color.length());
            }
        }
        if (!suffix.isEmpty()) {
            if (!suffix.startsWith("§")) {
                suffix = "§r" + suffix;
            }
            suffix = " " + suffix;
            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }
        }
        prefix += color;
//        RedstoneGang.instance().debug("Prefixo para " + view.getName() + " igual a " + prefix + "(" + prefix.length() + ")");
//        RedstoneGang.instance().debug("Suffixo para " + view.getName() + " igual a " + suffix + "(" + suffix.length() + ")");
        ScoreboardManager.addToTeam(viewer, view.getName(), teamName, prefix, suffix, false);
    }


}
