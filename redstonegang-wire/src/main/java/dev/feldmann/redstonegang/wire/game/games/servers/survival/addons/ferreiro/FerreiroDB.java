package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.SurvivalDatabase;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.db.tables.records.FerreiroItensRecord;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.jooq.Result;

import java.util.HashMap;


import static dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.db.Tables.*;

public class FerreiroDB extends SurvivalDatabase {
    @Override
    public void createTables() {
        dsl().execute("CREATE TABLE IF NOT EXISTS ferreiro_itens" +
                "(`id` BIGINT AUTO_INCREMENT PRIMARY KEY," +
                " `player` INTEGER, " +
                " `item` BLOB NOT NULL," +
                " `type` BOOLEAN, " +
                " `start` TIMESTAMP NULL DEFAULT NULL, " +
                " `ready` TIMESTAMP NULL DEFAULT NULL)");
    }

    public HashMap<Long, FerreiroItem> loadPlayer(int playerId) {
        HashMap<Long, FerreiroItem> itens = new HashMap<>();
        Result<FerreiroItensRecord> rs = dsl().selectFrom(FERREIRO_ITENS).where(FERREIRO_ITENS.PLAYER.eq(playerId)).fetch();
        for (FerreiroItensRecord r : rs) {
            FerreiroItem item = new FerreiroItem(r.getId(), ItemSerializer.deserializeItemStack(r.getItem()), r.getType() == 1, r.getPlayer(), r.getStart(), r.getReady());
            itens.put(item.id, item);
        }
        return itens;
    }

    public void delete(FerreiroItem item) {
        dsl().deleteFrom(FERREIRO_ITENS).where(FERREIRO_ITENS.ID.eq(item.id)).execute();
    }

    public void save(FerreiroItem item) {
        FerreiroItensRecord r = dsl().insertInto(FERREIRO_ITENS).columns(FERREIRO_ITENS.PLAYER, FERREIRO_ITENS.ITEM, FERREIRO_ITENS.START, FERREIRO_ITENS.READY, FERREIRO_ITENS.TYPE)
                .values(item.playerId, ItemSerializer.serializeItemStack(item.item), item.getStartTime(), item.getReadyTime(), (byte) (item.type ? 1 : 0)).returning(FERREIRO_ITENS.ID).fetchOne();
        item.id = r.getId();
    }
}
