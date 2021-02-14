package dev.feldmann.redstonegang.wire.game.base.addons.server.land;


import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.*;

import dev.feldmann.redstonegang.common.db.Database;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.LandFlagStorage;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.TerrenosRecord;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.HashMap;

public class LandDB extends Database {
    LandAddon manager;

    public LandDB(String databaseName, LandAddon manager) {
        super(databaseName);
        this.manager = manager;
    }

    @Override
    public void createTables() {
        safeExecute("CREATE TABLE IF NOT EXISTS terrenos (" +
                " id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                " owner INTEGER," +
                " minX INTEGER," +
                " maxX INTEGER," +
                " minZ INTEGER," +
                " maxZ INTEGER, " +
                " world VARCHAR(30), " +
                " properties TEXT" +
                ")");

        safeExecute("CREATE TABLE IF NOT EXISTS terrenos_players (" +
                " id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                " terreno_id INTEGER," +
                " type BIT, " +
                " player_id INTEGER," +
                " properties TEXT, " +
                " CONSTRAINT terreno_entry UNIQUE ( `terreno_id`,`type`,`player_id`)" +
                ")");
    }

    public boolean insertTerreno(Land t) {
        Hitbox2D r = t.getRegion();
        TerrenosRecord rec = dsl().insertInto(TERRENOS)
                .columns(TERRENOS.OWNER, TERRENOS.MINX, TERRENOS.MINZ, TERRENOS.MAXX, TERRENOS.MAXZ, TERRENOS.WORLD, TERRENOS.PROPERTIES)
                .values(t.getOwnerId(), r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY(), t.getWorldName(), t.getFlags().toJson())
                .returning(TERRENOS.ID)
                .fetchOne();
        if (rec != null) {
            t.setId(rec.getValue(TERRENOS.ID));
            return true;
        }
        return false;
    }

    public void saveProperties(Land t) {
        dsl().update(TERRENOS).set(TERRENOS.PROPERTIES, t.flags.toJson()).where(TERRENOS.ID.eq(t.getId())).execute();
    }

    public void updateRegion(Land t) {
        dsl().update(TERRENOS)
                .set(TERRENOS.MINX, t.getRegion().getMinX())
                .set(TERRENOS.MINZ, t.getRegion().getMinY())
                .set(TERRENOS.MAXX, t.getRegion().getMaxX())
                .set(TERRENOS.MAXZ, t.getRegion().getMaxY())
                .where(TERRENOS.ID.eq(t.getId()))
                .execute();

    }

    public HashMap<Integer, Land> loadAllTerrenos() {
        HashMap<Integer, Land> terrenos = new HashMap();
        Result<Record> res = dsl()
                .select()
                .from(TERRENOS)
                .fetch();
        for (Record r : res) {
            Land t = new Land(manager,
                    r.getValue(TERRENOS.ID),
                    r.getValue(TERRENOS.OWNER),
                    new Hitbox2D(
                            r.getValue(TERRENOS.MINX),
                            r.getValue(TERRENOS.MINZ),
                            r.getValue(TERRENOS.MAXX),
                            r.getValue(TERRENOS.MAXZ)),
                    r.getValue(TERRENOS.WORLD),
                    LandFlagStorage.fromJson(this.manager, r.getValue(TERRENOS.PROPERTIES)));
            terrenos.put(t.getId(), t);
        }
        return terrenos;
    }

    public void deleteTerreno(int id) {
        dsl().deleteFrom(TERRENOS)
                .where(TERRENOS.ID.eq(id))
                .execute();
        dsl().deleteFrom(TERRENOS_PLAYERS)
                .where(TERRENOS_PLAYERS.TERRENO_ID.eq(id))
                .and(TERRENOS_PLAYERS.TYPE.eq(PlayerProperties.TERRENO))
                .execute();
    }

    public HashMap<Integer, PlayerProperties> loadGlobal(int owner) {
        HashMap<Integer, PlayerProperties> properties = new HashMap();
        Result<Record2<Integer, String>> rs = dsl()
                .select(TERRENOS_PLAYERS.PLAYER_ID, TERRENOS_PLAYERS.PROPERTIES)
                .from(TERRENOS_PLAYERS)
                .where(TERRENOS_PLAYERS.TERRENO_ID.eq(owner))
                .and(TERRENOS_PLAYERS.TYPE.eq(PlayerProperties.PLAYER))
                .fetch();
        for (Record2<Integer, String> r : rs) {
            int pid = r.getValue(TERRENOS_PLAYERS.PLAYER_ID);
            properties.put(pid, PlayerProperties.fromJson(manager, pid, PlayerProperties.PLAYER, owner, r.getValue(TERRENOS_PLAYERS.PROPERTIES)));
        }
        return properties;
    }

    public void savePlayerProperty(PlayerProperties pl) {
        dsl()
                .insertInto(TERRENOS_PLAYERS)
                .columns(TERRENOS_PLAYERS.PLAYER_ID, TERRENOS_PLAYERS.TERRENO_ID, TERRENOS_PLAYERS.TYPE, TERRENOS_PLAYERS.PROPERTIES)
                .values(pl.getPlayerId(), pl.getOwnerId(), pl.getType(), pl.toJson())
                .onDuplicateKeyUpdate()
                .set(TERRENOS_PLAYERS.PROPERTIES, pl.toJson())
                .execute();
    }

    public void deletePlayerProperty(PlayerProperties pl) {
        dsl().deleteFrom(TERRENOS_PLAYERS)
                .where(TERRENOS_PLAYERS.TERRENO_ID.eq(pl.getOwnerId()))
                .and(TERRENOS_PLAYERS.PLAYER_ID.eq(pl.getPlayerId()))
                .and(TERRENOS_PLAYERS.TYPE.eq(pl.getType()))
                .execute();
    }

    public HashMap<Integer, PlayerProperties> loadProperties(Land t) {
        HashMap<Integer, PlayerProperties> properties = new HashMap();
        Result<Record2<Integer, String>> rs = dsl()
                .select(TERRENOS_PLAYERS.PLAYER_ID, TERRENOS_PLAYERS.PROPERTIES)
                .from(TERRENOS_PLAYERS)
                .where(TERRENOS_PLAYERS.TERRENO_ID.eq(t.getId()))
                .and(TERRENOS_PLAYERS.TYPE.eq(PlayerProperties.TERRENO))
                .fetch();
        for (Record2<Integer, String> r : rs) {
            int pid = r.getValue(TERRENOS_PLAYERS.PLAYER_ID);
            properties.put(pid, PlayerProperties.fromJson(manager, pid, PlayerProperties.TERRENO, t.getId(), r.getValue(TERRENOS_PLAYERS.PROPERTIES)));
        }
        return properties;
    }


}
