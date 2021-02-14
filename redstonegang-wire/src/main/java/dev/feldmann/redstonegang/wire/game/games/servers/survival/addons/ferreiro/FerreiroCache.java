package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro;

import java.util.*;

public class FerreiroCache {

    HashMap<Long, FerreiroItem> itens = new HashMap();

    public FerreiroCache(HashMap<Long, FerreiroItem> itens) {
        this.itens = itens;
    }

    public Collection<FerreiroItem> getItens() {
        return itens.values();
    }

    public void remove(long id) {
        itens.remove(id);
    }

    public void add(FerreiroItem item) {
        itens.put(item.id, item);
    }

    public Collection<FerreiroItem> sorted() {
        List<FerreiroItem> itens = new ArrayList<>(getItens());
        Collections.sort(itens, new Comparator<FerreiroItem>() {
            @Override
            public int compare(FerreiroItem o1, FerreiroItem o2) {
                if (o1.getReadyTime().after(o2.getReadyTime())) {
                    return 1;
                }
                return -1;
            }
        });
        return itens;

    }
}
