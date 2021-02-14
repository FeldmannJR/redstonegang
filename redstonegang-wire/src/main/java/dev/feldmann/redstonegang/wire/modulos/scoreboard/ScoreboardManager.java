package dev.feldmann.redstonegang.wire.modulos.scoreboard;

/**
 * @author Gabriel
 */

import dev.feldmann.redstonegang.wire.modulos.fixes.NoNameNpcs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager {

    private static FakeScoreboard mainScoreboard = new FakeScoreboard();
    private static boolean useOfflinePlayer = true;
    private static boolean useOfflineTeams = true;

    static {
        try {
            Team.class.getDeclaredMethod("addEntry", String.class);
            useOfflineTeams = false;
        } catch (Exception ex) {
        }
        try {
            Objective.class.getDeclaredMethod("getScore", String.class);
            useOfflinePlayer = false;
        } catch (Exception ex) {
        }
    }

    public static void addToTeam(String player, String teamName, String teamPrefix, boolean seeFriendlyInvis) {

        addToTeam(player, teamName, teamPrefix, null, seeFriendlyInvis);
    }

    public static void addToTeam(String player, String teamName, String teamPrefix, String teamSuffix, boolean seeFriendlyInvis) {
        addToTeam(player, teamName, teamPrefix, teamSuffix, seeFriendlyInvis, NameTagVisibility.ALWAYS);
    }

    public static void addToTeam(String player, String teamName, String teamPrefix, String teamSuffix, boolean seeFriendlyInvis, NameTagVisibility visibility) {
        if (teamPrefix != null) {
            if (teamPrefix.length() >= 16) {
                teamPrefix = teamPrefix.substring(0, 16);
            }
        }
        if (teamSuffix != null) {
            if (teamSuffix.length() >= 16) {
                teamSuffix = teamSuffix.substring(0, 16);
            }
        }
        for (FakeTeam team : mainScoreboard.getFakeTeams()) {
            team.removePlayer(player);
        }
        FakeTeam team = mainScoreboard.getFakeTeam(teamName);
        if (team == null) {
            team = mainScoreboard.createFakeTeam(teamName);
            if (teamPrefix != null) {
                team.setPrefix(teamPrefix);
            }
            if (teamSuffix != null) {
                team.setSuffix(teamSuffix);
            }
            team.setSeeInvisiblePlayers(seeFriendlyInvis);
        } else {
            if (teamPrefix != null) {
                team.setPrefix(teamPrefix);
            }
            if (teamSuffix != null) {
                team.setSuffix(teamSuffix);
            }
            team.setSeeInvisiblePlayers(seeFriendlyInvis);
        }
        team.setVisibility(visibility);
        team.addPlayer(player);

        for (Player p : Bukkit.getOnlinePlayers()) {
            addToTeam(p, player, teamName, teamPrefix, teamSuffix, seeFriendlyInvis, visibility);
        }
    }

    public static void clearScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        for (Objective obj : board.getObjectives()) {
            obj.unregister();
        }
        if (useOfflineTeams) {
            for (OfflinePlayer p : board.getPlayers()) {
                board.resetScores(p);
            }
        } else {
            for (String p : board.getEntries()) {
                board.resetScores(p);
            }
        }
        for (Team team : board.getTeams()) {
            team.unregister();
        }
    }

    public static void clearScoreboard(Player player, DisplaySlot slot) {
        Scoreboard board = player.getScoreboard();
        for (Objective obj : board.getObjectives()) {
            if (obj.getDisplaySlot() == slot) {
                obj.unregister();
            }
        }
    }

    public static void clearScoreboards() {
        mainScoreboard = new FakeScoreboard();
        for (Player player : Bukkit.getOnlinePlayers()) {
            clearScoreboard(player);
        }
    }

    public static void addToTeam(final Player observer, final String player, String teamName, String teamPrefix, String teamSuffix,
                                 boolean seeFriendlyInvis) {

        addToTeam(observer, player, teamName, teamPrefix, teamSuffix, seeFriendlyInvis, NameTagVisibility.ALWAYS);
    }

    public static void addToTeam(final Player observer, final String player, String teamName, String teamPrefix, String teamSuffix,
                                 boolean seeFriendlyInvis, NameTagVisibility visibility) {

        Scoreboard board = observer.getScoreboard();
        boolean addToTeam = false;
        Team team = board.getTeam(teamName);
        if (useOfflineTeams) {
            if (team == null || !team.hasPlayer(Bukkit.getOfflinePlayer(player))) {
                removeFromTeam(observer, player);
                addToTeam = true;
            }
        } else if (team == null || !team.hasEntry(player)) {
            removeFromTeam(observer, player);
            addToTeam = true;
        }
        if (team == null) {
            team = board.registerNewTeam(teamName);
            if (teamPrefix != null) {
                team.setPrefix(teamPrefix);
            }
            if (teamSuffix != null) {
                team.setSuffix(teamSuffix);
            }
            team.setCanSeeFriendlyInvisibles(seeFriendlyInvis);
            team.setAllowFriendlyFire(!seeFriendlyInvis);

        } else {
            if (teamPrefix == null) {
                teamPrefix = "";
            }
            if (teamSuffix == null) {
                teamSuffix = "";
            }
            if (!teamPrefix.equals(team.getPrefix())) {
                team.setPrefix(teamPrefix);
            }
            if (!teamSuffix.equals(team.getSuffix())) {
                team.setSuffix(teamSuffix);
            }
            if (seeFriendlyInvis != team.canSeeFriendlyInvisibles()) {
                team.setCanSeeFriendlyInvisibles(seeFriendlyInvis);
            }
            if (visibility != team.getNameTagVisibility()) {
                team.setNameTagVisibility(visibility);
            }
            /**
             * Isso aqui tu controla por plugin
             */
            if (!team.allowFriendlyFire()) {
                team.setAllowFriendlyFire(true);
            }
        }
        if (addToTeam) {
            if (useOfflineTeams) {
                team.addPlayer(Bukkit.getOfflinePlayer(player));
            } else {

                team.addEntry(player);
            }
        }


    }

    public static Team getTeam(Player p, Scoreboard score) {
        for (Team t : score.getTeams()) {
            if (t.hasPlayer(p)) {
                return t;
            }
        }
        return null;
    }

    public static void setPrefix(Player p, String prefix) {
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            setPrefix(p, pOn, prefix);
        }
    }

    private static void setPrefix(Player p, Player viewer, String prefix) {
        Scoreboard score = viewer.getScoreboard();
        Team t = getTeam(p, score);
        if (t == null) {
            t = score.registerNewTeam(p.getName());
            t.addEntry(p.getName());
        }
        t.setPrefix(prefix);
    }


    public static void setSuffix(Player p, Player viewer, String suffix) {
        Scoreboard score = viewer.getScoreboard();
        Team t = getTeam(p, score);
        if (t == null) {
            t = score.registerNewTeam(p.getName());
            t.addEntry(p.getName());
        }
        t.setSuffix(suffix);

    }

    public static void setSuffix(Player p, String sufix) {
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            setSuffix(p, pOn, sufix);
        }
    }

    public static void addToTeam(Player observer, String player, String teamName, String teamPrefix, boolean seeFriendlyInvis) {
        addToTeam(observer, player, teamName, teamPrefix, null, seeFriendlyInvis);
    }

    public static void removeFromTeam(String player) {
        OfflinePlayer pl = (useOfflineTeams ? Bukkit.getOfflinePlayer(player) : null);
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            for (Team team : board.getTeams()) {
                if (useOfflineTeams) {
                    if (team.hasPlayer(pl)) {
                        team.removePlayer(pl);
                    }
                } else if (team.hasEntry(player)) {
                    team.removeEntry(player);
                }
            }
        }
        for (FakeTeam team : mainScoreboard.getFakeTeams()) {
            team.removePlayer(player);
        }
    }

    public static void removeFromTeam(Player observer, String player) {
        Scoreboard board = observer.getScoreboard();
        OfflinePlayer pl = (useOfflineTeams ? Bukkit.getOfflinePlayer(player) : null);
        for (Team team : board.getTeams()) {
            if (useOfflineTeams) {
                if (team.hasPlayer(pl)) {
                    team.removePlayer(pl);
                }
            } else if (team.hasEntry(player)) {
                team.removeEntry(player);
            }
        }
    }

    private static Objective getObjective(Scoreboard board, DisplaySlot slot) {
        if (slot.equals(DisplaySlot.BELOW_NAME)) {
            if (board.getObjective("showhealth") == null) {
                //Objective obj = board.registerNewObjective("showhealth", "health");
                Objective obj = board.registerNewObjective("showhealth", "health");
                obj.setDisplaySlot(slot);
                obj.setDisplayName(ChatColor.RED + "❤");
            }
            return board.getObjective("showhealth");
        }
        if (board.getObjective(slot.name()) == null) {
            Objective obj = board.registerNewObjective(slot.name(), slot.name());
            obj.setDisplaySlot(slot);
        }
        return board.getObjective(slot.name());
    }

    public static void hideScore(DisplaySlot slot, String name) {
        mainScoreboard.hideScore(slot, name);
        for (Player player : Bukkit.getOnlinePlayers()) {
            hideScore(player, slot, name);
        }
    }

    public static void hideScore(Player player, DisplaySlot slot, String name) {
        int max = 32;

        if (name.length() > max) {
            name = name.substring(0, max);
        }
        if (useOfflinePlayer) {
            player.getScoreboard().resetScores(Bukkit.getOfflinePlayer(name));
        } else {
            player.getScoreboard().resetScores(name);
        }
    }

    public static void makeScore(DisplaySlot slot, String name, int score) {
        mainScoreboard.makeScore(slot, name, score);
        for (Player player : Bukkit.getOnlinePlayers()) {
            makeScore(player, slot, name, score);
        }
    }

    public static void makeScore(Player player, DisplaySlot slot, String name, int score) {
        int max = 32;
        if (name.length() > max) {
            name = name.substring(0, max);
        }
        Objective obj = getObjective(player.getScoreboard(), slot);
        Score c = useOfflinePlayer ? obj.getScore(Bukkit.getOfflinePlayer(name)) : obj.getScore(name);
        if (!useOfflineTeams && score == 0 && slot == DisplaySlot.SIDEBAR && !c.isScoreSet()) {
            c.setScore(1);
        }
        if (c.getScore() != score) {
            c.setScore(score);
        }
    }

    public static void registerScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        mainScoreboard.setupObjectives(player);
        NoNameNpcs.criouTeam.remove(player.getUniqueId());
    }

    public static void removeFromTeam(String player, String teamName) {
        FakeTeam team = mainScoreboard.getFakeTeam(teamName);
        if (team != null) {
            team.removePlayer(player);
        }
        OfflinePlayer pl = useOfflineTeams ? Bukkit.getOfflinePlayer(player) : null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();
            if (board.getTeam(teamName) != null) {
                if (useOfflineTeams) {
                    if (board.getTeam(teamName).hasPlayer(pl)) {
                        board.getTeam(teamName).removePlayer(pl);
                    }
                } else if (board.getTeam(teamName).hasEntry(player)) {
                    board.getTeam(teamName).removeEntry(player);
                }
            }
        }
    }

    public static void setDisplayName(DisplaySlot slot, String string) {
        mainScoreboard.setDisplayName(slot, string);
        for (Player player : Bukkit.getOnlinePlayers()) {
            setDisplayName(player, slot, string);
        }
    }

    public static void removeSlot(Player p, DisplaySlot slot) {
        Objective objective = p.getScoreboard().getObjective(slot.name());
        if (objective != null) objective.unregister();
        p.getScoreboard().clearSlot(slot);
    }

    public static void setDisplayName(Player player, DisplaySlot slot, String string) {
        getObjective(player.getScoreboard(), slot).setDisplayName(string);
    }

    public static void addToTeam(Player player, String teamName, String teamPrefix, boolean seeFriendlyInvis) {
        addToTeam(player.getName(), teamName, teamPrefix, seeFriendlyInvis);
    }

    public static void addToTeam(Player p, Player p2, String teamName, String teamPrefix, boolean seeFriendlyInvis) {
        addToTeam(p, p2.getName(), teamName, teamPrefix, seeFriendlyInvis);
    }

    public static void removeFromTeam(Player player) {
        removeFromTeam(player.getName());
    }

    public static HashMap<UUID, HashMap<Integer, String>> ScoreCache = new HashMap<UUID, HashMap<Integer, String>>();

    private static HashMap<Integer, String> getScoreCache(Player p) {
        if (!ScoreCache.containsKey(p.getUniqueId())) {
            ScoreCache.put(p.getUniqueId(), new HashMap<Integer, String>());
        }
        return ScoreCache.get(p.getUniqueId());
    }

    public static String getScoreLine(Player p, int line) {
        return getScoreCache(p).get(line);
    }

    public static void removeLine(int line) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getScoreLine(p, line) != null) {
                ScoreboardManager.hideScore(p, DisplaySlot.SIDEBAR, getScoreLine(p, line));
            }
        }
    }

    public static void setScoreLine(Player p, int line, String score) {
        if (getScoreLine(p, line) != null) {
            ScoreboardManager.hideScore(p, DisplaySlot.SIDEBAR, getScoreLine(p, line));
        }
        getScoreCache(p).put(line, score);
        ScoreboardManager.makeScore(p, DisplaySlot.SIDEBAR, score, line);
    }

    public static void SetScoreLine(int line, String score) {
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            setScoreLine(pOn, line, score);
        }
    }
}
