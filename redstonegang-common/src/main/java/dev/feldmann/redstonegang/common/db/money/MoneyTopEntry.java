package dev.feldmann.redstonegang.common.db.money;

public class MoneyTopEntry<T> {
    int playerId;
    String playerName;
    T value;

    public MoneyTopEntry(int playerId, String playerName, T value) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.value = value;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public T getValue() {
        return value;
    }
}
