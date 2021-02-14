package dev.feldmann.redstonegang.common.network;

import java.util.ArrayList;
import java.util.List;

public class NetworkMessageBuilder {
    List<String> values = new ArrayList();

    public NetworkMessageBuilder add(Object... b) {
        for (int x = 0; x < b.length; x++) {
            values.add(b[x].toString());
        }
        return this;
    }

    public NetworkMessage build() {
        String[] val = new String[values.size()];
        for (int x = 0; x < val.length; x++) {
            val[x] = values.get(x);
        }
        return new NetworkMessage(val);
    }
}
