/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dev.feldmann.redstonegang.wire.modulos.scoreboard;

import org.bukkit.scoreboard.NameTagVisibility;

import java.util.ArrayList;

/**
 * @author Gabriel
 */

public class FakeTeam {
    private ArrayList<String> players = new ArrayList<String>();
    private String prefix;
    private boolean seeInvisibles;
    private String teamName;
    private String suffix;
    private NameTagVisibility visibility;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public FakeTeam(String teamName) {
        this.teamName = teamName;
    }

    public void addPlayer(String player) {
        players.add(player);
    }

    public boolean canSeeInvisiblePlayers() {
        return seeInvisibles;
    }

    public NameTagVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(NameTagVisibility visibility) {
        this.visibility = visibility;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTeamName() {
        return teamName;
    }

    public void removePlayer(String player) {
        players.remove(player);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSeeInvisiblePlayers(boolean seeInvisibles) {
        this.seeInvisibles = seeInvisibles;
    }

}
