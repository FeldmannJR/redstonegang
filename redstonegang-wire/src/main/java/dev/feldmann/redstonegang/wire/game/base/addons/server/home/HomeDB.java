package dev.feldmann.redstonegang.wire.game.base.addons.server.home;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.*;

public class HomeDB extends Database {
    private static final Table TABLE = table("homes");
    private static final Field<Integer> OWNER = field("owner", SQLDataType.INTEGER.nullable(false));
    private static final Field<String> NAME = field("name", SQLDataType.VARCHAR(200).nullable(false));
    private static final Field<String> LOCATION = field("location", SQLDataType.VARCHAR(1000).nullable(false));
    private static final Field<Integer> TYPE = field("type", SQLDataType.INTEGER.nullable(false));


    public HomeDB(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        dsl()
                .createTableIfNotExists(TABLE)
                .columns(OWNER, NAME, LOCATION, TYPE)
                .constraint(constraint().primaryKey(OWNER, NAME))
                .execute();
    }

    public HomeEntry loadHome(int owner, String home) {
        Record3<String, String, Integer> r = dsl().select(NAME, LOCATION, TYPE).from(TABLE).where(OWNER.eq(owner)).and(NAME.eq(home)).fetchOne();
        if (r != null)
            return new HomeEntry(owner, r.get(NAME), BungeeLocation.fromString(r.get(LOCATION)), HomeType.values()[r.get(TYPE)]);
        return null;
    }

    public List<HomeEntry> loadHomes(int owner) {
        List<HomeEntry> home = new ArrayList();
        Result<Record3<String, String, Integer>> records = dsl().select(NAME, LOCATION, TYPE).from(TABLE).where(OWNER.eq(owner)).fetch();
        for (Record3<String, String, Integer> r : records) {
            HomeEntry h = new HomeEntry(owner, r.get(NAME), BungeeLocation.fromString(r.get(LOCATION)), HomeType.values()[r.get(TYPE)]);
            home.add(h);
        }
        return home;
    }


    public void saveHome(HomeEntry en) {
        dsl().insertInto(TABLE)
                .columns(OWNER, NAME, LOCATION, TYPE)
                .values(en.getOwner(), en.getName(), en.getLocation().toString(), en.getType().ordinal())
                .onDuplicateKeyUpdate().set(LOCATION, en.getLocation().toString()).set(TYPE, en.getType().ordinal()).execute();
    }

    public void delete(HomeEntry h) {
        dsl().deleteFrom(TABLE).where(OWNER.eq(h.getOwner())).and(NAME.eq(h.getName())).execute();

    }

    public int countHomes(int pid) {
        return dsl().select(count().as("c")).from(TABLE).where(OWNER.eq(pid)).fetchOne().<Integer>getValue("c", Integer.class);
    }
}
