package dev.feldmann.redstonegang.common.player;

import java.sql.Timestamp;

public class PlayerStats {

    Timestamp lastLogin;
    Timestamp firstLogin;
    int onlineMinutes = 0;


    private User player;

    public PlayerStats(User player) {
        this.player = player;
    }

    public User getPlayer() {
        return player;
    }
}
