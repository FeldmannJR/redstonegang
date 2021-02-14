package dev.feldmann.redstonegang.wire.game.games.other.login;

public class LoginData {
    public int playerId;
    public int attempts = 0;
    public long joinedTime;
    public boolean loggedIn = false;
    public boolean logging = false;

    public LoginData(int playerId) {
        this.playerId = playerId;
        joinedTime = System.currentTimeMillis();
    }

    public boolean expired() {
        return joinedTime + 60000 < System.currentTimeMillis();

    }
}
