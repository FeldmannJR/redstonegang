package dev.feldmann.redstonegang.common.network;

import java.util.Optional;

public class NetworkMessage {

    private String[] msg;

    public NetworkMessage(String... message) {
        this.msg = message;
    }


    public String get(int index) {
        if (msg.length <= index) {
            return null;
        }
        return msg[index];
    }

    public boolean has(int index) {
        return msg.length > index;
    }


    public Optional<Integer> getInt(int index) {
        if (has(index)) {
            try {
                return Optional.of(Integer.valueOf(msg[index]));
            } catch (NumberFormatException ex) {

            }

        }
        return Optional.empty();
    }

    public int getLength() {
        return msg.length;
    }

    @Override
    public String toString() {
        String s = "{length=" + msg.length;
        for (int x = 0; x < msg.length; x++) {
            s += ", " + x + "=" + msg[x];
        }
        s += "}";
        return s;

    }
}
