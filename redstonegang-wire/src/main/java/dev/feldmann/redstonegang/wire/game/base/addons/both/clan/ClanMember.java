package dev.feldmann.redstonegang.wire.game.base.addons.both.clan;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;

public class ClanMember {

    private int playerId;
    private String clan;
    private int role;
    private ClanAddon addon;

    public ClanMember(int player, String clan, int role) {
        this.playerId = player;
        this.clan = clan;
        this.role = role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public User getPlayer() {
        return RedstoneGangSpigot.getPlayer(playerId);
    }

    public String getClanTag() {
        return clan;
    }

    public int getRole() {
        return role;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public void setAddon(ClanAddon addon) {
        this.addon = addon;
    }

    public Clan getClan() {
        if (clan != null) {
            return addon.cache.getClan(clan);
        }
        return null;
    }

    public void updateFrom(ClanMember clanMember) {
        clan = clanMember.clan;
        role = clanMember.role;
    }
}
