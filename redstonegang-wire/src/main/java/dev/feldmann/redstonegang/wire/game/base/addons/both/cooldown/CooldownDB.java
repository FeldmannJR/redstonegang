package dev.feldmann.redstonegang.wire.game.base.addons.both.cooldown;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.utils.SyncUtils;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;

import static org.jooq.impl.DSL.*;

public class CooldownDB extends Database {

    public static long SEMANA = 1000 * 60 * 60 * 24 * 7;
    public static long DIA = 1000 * 60 * 60 * 24;
    public static long HORA = 1000 * 60 * 60;
    public static long MINUTO = 1000 * 60;

    private static final Table TABLE = table("cooldowns");
    private static final Field<Integer> USER = field("user", SQLDataType.INTEGER.nullable(false));
    private static final Field<String> CDNAME = field("cdname", SQLDataType.VARCHAR(100).nullable(false));
    private static final Field<Timestamp> FINAL = field("final", SQLDataType.TIMESTAMP);

    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(USER, CDNAME, FINAL)
                .constraint(constraint().primaryKey(USER, CDNAME))
                .execute();
    }

    /**
     * @return o cooldown que resta se for diferente de -1
     **/
    public long getCooldown(int pid, String cd) {
        synchronized (SyncUtils.getLock(pid + "cd" + cd)) {
            Record1<Timestamp> rs = dsl().select(FINAL).from(TABLE).where(CDNAME.eq(cd)).and(USER.eq(pid)).fetchOne();
            if (rs == null) {
                return -1;
            }
            if (rs.get(FINAL) == null) {
                return 0;
            }
            Timestamp fim = rs.get(FINAL);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.before(fim)) {
                return fim.getTime();
            } else {
                synchronized (SyncUtils.getLock(pid + "cd" + cd)) {
                    new Thread(() -> {
                        dsl().deleteFrom(TABLE).where(CDNAME.eq(cd)).and(USER.eq(pid)).execute();
                    }
                    ).start();
                }
            }
            return -1;
        }
    }

    public void setCooldown(int pid, String cd, long finalL) {
        synchronized (SyncUtils.getLock(pid + "cd" + cd)) {

            Timestamp fim = finalL != 0 ? new Timestamp(finalL) : null;
            dsl().insertInto(TABLE)
                    .columns(USER, CDNAME, FINAL)
                    .values(pid, cd, fim)
                    .onDuplicateKeyUpdate()
                    .set(FINAL, fim).execute();

        }
    }

    /**
     * Limpa os que ja venceram pro db não ficar gigante
     */
    public void clear() {
        dsl().deleteFrom(TABLE).where(FINAL.lessOrEqual(DSL.currentTimestamp())).execute();
    }
}
