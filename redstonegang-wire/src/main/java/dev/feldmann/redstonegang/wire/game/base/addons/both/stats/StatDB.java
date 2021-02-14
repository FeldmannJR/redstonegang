package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.UserDB;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StatDB extends Database {

    private static Table TABLE = DSL.table(DSL.name("stats"));
    private static Field<Integer> USER = DSL.field(DSL.name("user"), SQLDataType.INTEGER.nullable(false));


    public StatDB() {
        super();
    }


    private ConcurrentHashMap<Integer, PendingStatAction> pending = new ConcurrentHashMap<Integer, PendingStatAction>();


    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(USER)
                .constraint(DSL.primaryKey(USER))
                .constraint(DSL.foreignKey(USER).references(UserDB.TABLE, UserDB.ID))
                .execute();
    }

    public void createColumns(List<PlayerStat> stats) {
        for (PlayerStat st : stats) {
            createColumnIfNotExists(TABLE, st.getField());
            st.columnCreated = true;
        }
    }

    public void addPontos(int pId, PlayerStat r, long pontos) {
        if (!pending.containsKey(pId)) {
            pending.put(pId, new PendingStatAction(pId));
        }
        pending.get(pId).addPoints(r, pontos);
    }

    public void executePending() {
        Collection<PendingStatAction> values = new ArrayList<>(pending.values());
        pending.clear();
        for (PendingStatAction ac : values) {
            //Faço uma query por player independente de quantos stats foram modificados pra ficar + leve
            List<Field<?>> fields = new ArrayList<>();
            List<Object> fvalues = new ArrayList<>();
            fields.add(USER);
            fvalues.add(ac.pid);
            HashMap<Field<Long>, Field<Long>> fmap = new HashMap<>();
            for (PlayerStat stat : ac.map.keySet()) {
                fields.add(stat.getField());
                fvalues.add(ac.map.get(stat));
                fmap.put(stat.getField(), stat.getField().add(ac.map.get(stat)));
            }
            InsertOnDuplicateSetMoreStep a = dsl().insertInto(TABLE)
                    .columns(fields)
                    .values(fvalues)
                    .onDuplicateKeyUpdate()
                    .set(fmap);
            a.execute();
        }

    }


    public PlayerStatCache loadPlayer(List<PlayerStat> stats, int pId) {
        Record record = dsl().selectFrom(TABLE).where(USER.eq(pId)).fetchOne();
        PlayerStatCache c = new PlayerStatCache(pId);
        if (record != null) {
            for (PlayerStat st : stats) {
                if (st.columnCreated) {
                    c.setValue(st, record.get(st.getField()));
                }
            }
        }
        return c;
    }

    public void loadTop(PlayerStat r, int limit) {
        Result<Record2<Integer, Long>> fetch = dsl().select(USER, r.getField()).from(TABLE)
                .orderBy(r.order(USER))
                .limit(limit)
                .fetch();

        HashMap<Integer, Long> map = new HashMap<>();
        fetch.forEach((a) -> {
            map.put(a.value1(), a.value2());
        });
        r.setTop(map);

    }


}
