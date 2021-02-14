package dev.feldmann.redstonegang.wire.game.base.addons.both.clan;

import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.db.ClanDB;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;

public class ClanCache {


    private HashMap<String, Clan> clans = new HashMap();
    private HashMap<Integer, ClanMember> members = new HashMap();

    private ClanDB db;
    private ClanAddon manager;

    public ClanCache(ClanAddon manager) {
        this.manager = manager;
        db = new ClanDB(manager.databaseName);
    }

    public Clan getClan(String tag) {
        if (!clans.containsKey(tag)) {
            Clan clan = db.loadClan(tag);
            if (clan != null)
                clan.setManager(manager);
            clans.put(tag, clan);
        }
        return clans.get(tag);
    }

    public void addToClan(ClanMember member, Clan c) {
        member.setClan(c.getTag());
        c.addMember(member.getPlayerId());
        saveMember(member);
    }

    public ClanDB getDb() {
        return db;
    }

    public void updateLastActive(String tag) {
        db.updateLastActive(tag);
    }

    public boolean changeTag(Clan c, String tag) {
        String colorless = ChatColor.stripColor(tag).toLowerCase();
        if (this.existsTag(tag) && !colorless.equalsIgnoreCase(c.getTag())) {
            return false;
        }

        String old = c.getTag();
        db.changeTag(c, colorless, tag);
        manager.network("changetag", old, tag);
        handleChangeTag(old, tag);
        return true;
    }

    public void handleChangeTag(String oldTag, String newTag) {
        String colorless = ChatColor.stripColor(newTag).toLowerCase();
        if (clans.containsKey(oldTag)) {
            Clan c = clans.remove(oldTag);
            clans.put(colorless, c);
            c.setTag(colorless);
            c.setColorTag(newTag);
        }
        for (ClanMember member : members.values()) {
            if (member.getClanTag() != null)
                if (member.getClanTag().equalsIgnoreCase(oldTag)) {
                    member.setClan(colorless);
                }
        }
    }

    public void removeFromClan(ClanMember member) {
        if (clans.containsKey(member.getClanTag())) {
            clans.get(member.getClanTag()).removeMember(member.getPlayerId());
        }
        member.setClan(null);
        saveMember(member);
    }


    public void handleDelete(String tag) {
        clans.remove(tag);
        for (ClanMember member : members.values()) {
            if (member.getClanTag() != null)
                if (member.getClanTag().equalsIgnoreCase(tag)) {
                    member.setClan(null);
                }
        }
    }

    public void deleteClan(Clan c) {
        db.deleteClan(c);
        handleDelete(c.getTag());
        manager.network("deleteclan", c.getTag());
    }


    public void saveClan(Clan c) {
        if (!clans.containsKey(c.getTag())) {
            c.setManager(manager);
        }
        db.saveClan(c);
        clans.put(c.getTag(), c);
        manager.network("updateclan", c.getTag());
    }

    private void reloadClan(String tag) {
        if (clans.containsKey(tag)) {
            Clan c = clans.get(tag);
            c.updateFrom(db.loadClan(tag));
        }
    }


    public boolean existsName(String name) {
        return db.existClanWithName(name);
    }

    public boolean existsTag(String tag) {
        return db.existClanWithTag(ChatColor.stripColor(tag).toLowerCase());
    }


    public boolean hasClan(Player p) {
        return hasClan(manager.getPlayerId(p));
    }

    public boolean hasClan(int id) {
        ClanMember member = getMember(id);
        return member != null && member.getClanTag() != null;
    }

    public ClanMember getMember(int playerId) {
        if (!members.containsKey(playerId)) {
            ClanMember m = db.loadMember(playerId);
            m.setAddon(manager);
            members.put(playerId, m);
        }
        return members.get(playerId);
    }

    private void updatePlayer(int pid) {
        if (members.containsKey(pid)) {
            members.get(pid).updateFrom(db.loadMember(pid));
        }
    }

    public ClanMember getMember(Player p) {
        return getMember(manager.getPlayerId(p));
    }

    public void saveMember(ClanMember member) {
        members.put(member.getPlayer().getId(), member);
        db.saveMember(member);
        manager.network("updateplayer", member.getPlayerId());
    }

    @EventHandler
    public void message(NetworkMessageEvent ev) {
        if (ev.getId().equals("clan")) {
            if (!ev.get(0).equalsIgnoreCase(manager.getIdentifier())) {
                return;
            }
            String action = ev.get(1);
            switch (action) {
                case "updateclan":
                    String clan = ev.get(2);
                    if (clans.containsKey(clan)) {
                        reloadClan(clan);
                    }
                    break;
                case "deleteclan":
                    String c = ev.get(2);
                    if (clans.containsKey(c)) {
                        clans.remove(c).valid = false;
                    }
                    break;
                case "updateplayer":
                    int pid = ev.getInt(2);
                    if (members.containsKey(pid)) {
                        members.get(pid).updateFrom(db.loadMember(pid));
                    }
                    break;
                case "changetag":
                    String old = ev.get(2);
                    String newtag = ev.get(3);
                    handleChangeTag(old, newtag);
                    break;

            }
        }
    }


}
