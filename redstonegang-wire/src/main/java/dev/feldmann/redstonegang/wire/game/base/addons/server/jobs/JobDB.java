package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.UserDB;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.HashMap;

public class JobDB extends Database {
    private JobsAddon addon;

    private static Field<Integer> PLAYER_ID = DSL.field("playerId", SQLDataType.INTEGER.nullability(Nullability.NOT_NULL));
    private static Table TABLE = DSL.table("jobs");


    public JobDB(String database, JobsAddon addon) {
        super(database);
        this.addon = addon;
    }

    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(PLAYER_ID)
                .constraint(DSL.constraint().primaryKey(PLAYER_ID))
                .constraint(DSL.constraint().foreignKey(PLAYER_ID).references(UserDB.TABLE, UserDB.ID))
                .execute();
    }

    public void addColumn(Job job) {
        createColumnIfNotExists(TABLE, job.getDatabaseField());
    }

    public JobPlayer loadPlayer(int playerId) {
        Record record = dsl().selectFrom(TABLE).where(PLAYER_ID.eq(playerId)).fetchOne();
        if (record != null) {
            JobPlayer pl = new JobPlayer(playerId);
            for (Job job : addon.getJobs()) {
                Long lvalue = record.get(job.getDatabaseField());
                if (lvalue != null) {
                    pl.xps.put(job, lvalue);
                }
            }
            return pl;
        }
        return new JobPlayer(playerId);
    }

    public HashMap<Integer, Long> getTop(Job job, int limit) {
        HashMap<Integer, Long> top = new HashMap<>();
        Result<Record2<Integer, Long>> rs = dsl().select(PLAYER_ID, job.getDatabaseField()).from(TABLE).orderBy(job.getDatabaseField().desc()).limit(limit).fetch();
        for (Record2<Integer, Long> r : rs) {
            top.put(r.get(PLAYER_ID), r.get(job.getDatabaseField()));
        }
        return top;
    }

    public void addXp(int playerId, Job job, long xp) {
        dsl().insertInto(TABLE)
                .columns(PLAYER_ID, job.getDatabaseField())
                .values(playerId, xp)
                .onDuplicateKeyUpdate()
                .set(job.getDatabaseField(), job.getDatabaseField().add(xp))
                .executeAsync();
    }


}
