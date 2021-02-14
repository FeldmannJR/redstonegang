package dev.feldmann.redstonegang.common.db.money;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.currencies.Currency;
import dev.feldmann.redstonegang.common.player.UserDB;
import dev.feldmann.redstonegang.common.utils.SyncUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables.USERS;

import java.util.ArrayList;
import java.util.List;


public interface CurrencyDatabase<T extends Number & Comparable<T>> extends Currency<T> {

    String getTableName();

    T getDefaultValue();

    DSLContext getDsl();

    default Table getTable() {
        return DSL.table(getTableName());
    }

    Field<T> getValueField();

    default Field<Integer> getPlayerField() {
        return DSL.field("playerId", SQLDataType.INTEGER.nullable(false));
    }


    default void createMoneyTable() {
        getDsl().createTableIfNotExists(getTable())
                .columns(getPlayerField(), getValueField())
                .constraint(DSL.constraint().primaryKey(getPlayerField()))
                .constraint(
                        DSL.foreignKey(getPlayerField()).references(UserDB.TABLE, UserDB.ID).onDeleteCascade())
                .execute();
    }

    default List<MoneyTopEntry> getTop(int limit) {
        Result<Record3<Integer, T, String>> result = getDsl()
                .select(getPlayerField(), getValueField(), USERS.NAME)
                .from(getTable())
                .innerJoin(USERS)
                .on(USERS.ID.eq(getPlayerField()))
                .orderBy(getValueField().desc())
                .limit(limit).fetch();
        ArrayList<MoneyTopEntry> list = new ArrayList<>();
        for (Record3<Integer, T, String> record : result) {
            MoneyTopEntry<T> entry = new MoneyTopEntry<T>(record.get(getPlayerField()), record.get(USERS.NAME), record.get(getValueField()));
            list.add(entry);
        }
        return list;
    }

    @Override
    default boolean remove(int playerId, T qtd) {
        synchronized (SyncUtils.getLock(playerId + "-money-" + getTableName())) {

            boolean success = getDsl()
                    .update(getTable()).
                            set(getValueField(), DSL.greatest(getValueField().sub(qtd), DSL.val(0)))
                    .where(getPlayerField().eq(playerId))
                    .execute() >= 0;

            RedstoneGang.instance().currencies().notifyHooks(playerId, this, CurrencyChangeType.REMOVE);
            return success;
        }
    }

    default boolean set(int playerId, T value) {
        synchronized (SyncUtils.getLock(playerId + "-money-" + getTableName())) {
            boolean success = getDsl().insertInto(getTable())
                    .columns(getPlayerField(), getValueField())
                    .values(playerId, value)
                    .onDuplicateKeyUpdate().set(getValueField(), value).execute() >= 0;
            RedstoneGang.instance().currencies().notifyHooks(playerId, this, CurrencyChangeType.SET);
            return success;
        }
    }

    default boolean add(int playerId, T value) {
        synchronized (SyncUtils.getLock(playerId + "-money-" + getTableName())) {
            boolean success = getDsl().insertInto(getTable())
                    .columns(getPlayerField(), getValueField())
                    .values(playerId, value)
                    .onDuplicateKeyUpdate()
                    .set(getValueField(), DSL.greatest(getValueField().add(value), DSL.val(0))).execute() >= 0;
            RedstoneGang.instance().currencies().notifyHooks(playerId, this, CurrencyChangeType.ADD);
            return success;
        }
    }

    @Override
    default T get(int playerId) {
        synchronized (SyncUtils.getLock(playerId + "-money-" + getTableName())) {

            Record1<T> cache = getDsl().select(getValueField()).from(getTable()).where(getPlayerField().eq(playerId)).fetchOne();
            if (cache != null) {
                return cache.getValue(getValueField());
            }
            return getDefaultValue();
        }
    }
}
