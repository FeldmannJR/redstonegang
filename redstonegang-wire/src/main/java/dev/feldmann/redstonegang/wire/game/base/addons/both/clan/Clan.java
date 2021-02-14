package dev.feldmann.redstonegang.wire.game.base.addons.both.clan;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Clan {

    private ClanAddon manager;

    private String tag;
    private String colorTag;
    private String name;

    private Timestamp founded;
    private int founder;
    private BungeeLocation home = null;
    private List<Integer> members = new ArrayList();
    private ClanProperties props = new ClanProperties();
    private Integer maxMembers = null;
    private Boolean canUseTag = null;

    //private List<String> allies = new ArrayList<>();
    //private List<String> enemies = new ArrayList<>();


    public boolean valid = true;

    public Clan(String tag, String colorTag, String name) {
        this.tag = tag;
        this.colorTag = colorTag;
        this.name = name;
    }

    public void addMember(int member) {
        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }


    /*
        public void addAlly(String tag) {
            if (!allies.contains(tag)) {
                allies.add(tag);
            }
        }

        public void addEnemy(String tag) {
            if (!enemies.contains(tag)) {
                enemies.remove(tag);
            }
        }

        public void setRelation(String tag, ClanRelationType type) {
            if (type == ClanRelationType.NEUTRAL) {
                enemies.remove(tag);
                allies.remove(tag);
            }
            if (type == ClanRelationType.ALLY) {
                enemies.remove(tag);
                allies.add(tag);
            }

            if (type == ClanRelationType.ENEMY) {
                enemies.add(tag);
                allies.remove(tag);
            }
        }
    */


    public boolean canInvite() {
        return members.size() >= getMaxMembers();
    }

    public int getMaxMembers() {
        if (maxMembers == null) {
            maxMembers = (int) RedstoneGangSpigot.getPlayer(founder).getOption(manager.MAX_MEMBERS);
        }
        return maxMembers;
    }

    public boolean canUseTag() {
        if (canUseTag == null) {
            canUseTag = RedstoneGangSpigot.getPlayer(founder).permissions().hasPermission(ClanAddon.CAN_USE_CLAN_SUFFIX);
        }
        return canUseTag;
    }

    public boolean removeMember(int member) {
        return this.members.remove((Object) member);
    }

    public int getFounder() {
        return founder;
    }

    public void setFounded(Timestamp founded) {
        this.founded = founded;
    }

    public void setFounder(int founder) {
        this.founder = founder;

    }


    public String getName() {
        return name;
    }

    public Timestamp getFounded() {
        return founded;
    }

    public String getColorTag() {
        return colorTag;
    }

    public String getTag() {
        return tag;
    }

    public boolean isMember(int pid) {
        return members.contains(pid);
    }

    public ClanProperties getProps() {
        return props;
    }

    public void setProps(ClanProperties props) {
        this.props = props;
    }

    public void setColorTag(String colorTag) {
        this.colorTag = colorTag;
    }

    public BungeeLocation getHome() {
        return home;
    }

    public void setHome(BungeeLocation home) {
        this.home = home;
    }

    public void setManager(ClanAddon manager) {
        this.manager = manager;
    }

    public void sendMessage(String message, Object... obj) {
        manager.messages.sendClanMessage(this, C.msgText(ClanAddon.CLAN_INFO, message, obj));
    }

    public void sendChatMessage(Player cs, String msg) {
        manager.messages.sendClanMessage(this, C.msgText(ClanAddon.CLAN_CHAT, "%% > %%", RedstoneGangSpigot.getPlayer(cs.getUniqueId()), msg));
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public List<Player> getOnlinePlayers() {
        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (manager.getCache().getMember(p).getClanTag().equals(tag)) {
                players.add(p);
            }
        }
        return players;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Clan) {
            return ((Clan) obj).getTag().equals(tag);
        }
        return false;
    }

    public void updateFrom(Clan c) {
        name = c.name;
        colorTag = c.colorTag;
        founded = c.founded;
        founder = c.founder;
        home = c.home;
        props = c.props;
    }

    public void clearCache() {
        this.canUseTag = null;
        this.maxMembers = null;
    }
}
