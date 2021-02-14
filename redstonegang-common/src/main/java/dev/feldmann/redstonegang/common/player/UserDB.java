package dev.feldmann.redstonegang.common.player;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.tables.records.AccountsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.UsersRecord;
import dev.feldmann.redstonegang.common.player.permissions.PermissionUser;
import org.jooq.*;
import org.jooq.impl.DSL;


import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables.*;
import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.Tables.ACCOUNTS;

import java.sql.Timestamp;
import java.util.UUID;

public class UserDB extends Database {

    public static Table TABLE = USERS;
    public static Field<Integer> ID = USERS.ID;

    public UserDB() {
        super("redstonegang_common", true);
    }

    public void createTables() {
        safeExecute("CREATE TABLE IF NOT EXISTS `users`" +
                "(" +
                " `id`         integer NOT NULL AUTO_INCREMENT ," +
                " `name`       varchar(32) UNIQUE NOT NULL ," +
                " `uuid`       varchar(64) NOT NULL ," +
                " `account_id`   integer UNIQUE," +
                " `group`      INTEGER," +
                " `registred` TIMESTAMP DEFAULT NOW(), " +
                " `cash`    INTEGER DEFAULT 0," +
                " `server`  varchar(32) DEFAULT NULL, " +
                " `bungee`  varchar(32) DEFAULT NULL, " +
                " `last_login` TIMESTAMP NULL DEFAULT NULL, " +
                " `disguise_name`  VARCHAR(16) DEFAULT NULL, " +
                "PRIMARY KEY (`id`)" +
                ")");

        safeExecute("ALTER TABLE `users`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`last_server` VARCHAR(32) DEFAULT NULL " +
                "AFTER `server`");

        safeExecute("ALTER TABLE `users`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`last_cash_claim` TIMESTAMP NULL DEFAULT NULL " +
                "AFTER `cash`");

        // Cada tipo de servidor tem um identificador, pra saber qual tipo de servidor ele tá (survival,minigames,factions...)
        safeExecute("ALTER TABLE `users`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`server_identifier` VARCHAR(32) DEFAULT NULL " +
                "AFTER `last_server`");

        safeExecute("ALTER TABLE `users`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`ip` VARCHAR(12) DEFAULT NULL " +
                "AFTER `last_server`");


    }

    public User loadPlayerById(int id) {
        return loadPlayer(USERS.ID.eq(id));
    }

    public User loadPlayerByName(String name) {
        return loadPlayer(USERS.NAME.eq(name));
    }

    public User loadPlayerByDisguise(String name) {
        Condition cond = USERS.NAME.eq(name).and(USERS.DISGUISE_NAME.isNull());
        cond = cond.or(USERS.DISGUISE_NAME.eq(name));
        return loadPlayer(cond);
    }

    public Integer findIdByName(String name) {
        Condition cond = USERS.NAME.eq(name).and(USERS.DISGUISE_NAME.isNull());
        cond = cond.or(USERS.DISGUISE_NAME.eq(name));
        Record1<Integer> record = dsl().select(USERS.ID).from(USERS).where(cond).fetchAny();
        return record != null ? record.value1() : null;
    }

    public Integer findIdByUUID(UUID uuid) {
        Record1<Integer> record = dsl().select(USERS.ID).from(USERS).where(USERS.UUID.eq(uuid.toString())).fetchAny();
        return record != null ? record.value1() : null;
    }

    public User loadPlayerByUUID(UUID uuid) {
        return loadPlayer(USERS.UUID.eq(uuid.toString()));
    }


    private User loadPlayer(Condition cond) {
        UsersRecord r = dslpool()
                .selectFrom(USERS)
                .where(cond)
                .fetchOne();

        if (r != null) {
            User pl = new User(r);
            loadPermissions(pl, r);
            return pl;
        }
        return null;
    }


    private PermissionUser loadPermissions(User pl, Record r) {
        PermissionUser puser = new PermissionUser(pl, r == null ? null : r.get(USERS.GROUP));
        pl.setPermissions(puser);
        puser.setPermissionList(RedstoneGang.instance.user().getPermissions().getDb().getPermissions(puser));
        return puser;
    }

    public User insertPlayer(UUID uid, String name) {
        return insertPlayer(uid, name, null);
    }

    public User insertPlayer(UUID uid, String name, Integer accountId) {
        UsersRecord r = dslpool()
                .insertInto(USERS)
                .columns(USERS.UUID, USERS.NAME, USERS.REGISTRED, USERS.ACCOUNT_ID)
                .values(uid.toString(), name, new Timestamp(System.currentTimeMillis()), accountId)
                .returning(USERS.ID)
                .fetchOne();
        if (r == null) {
            return null;
        }
        User pl = new User(r);
        loadPermissions(pl, null);
        return pl;
    }

    public boolean setCash(int playerId, int value) {
        return dsl().update(USERS).set(USERS.CASH, value).where(USERS.ID.eq(playerId)).execute() > 0;
    }

    public boolean removeCash(int playerId, int value) {
        return dsl()
                .update(USERS).
                        set(USERS.CASH, DSL.greatest(USERS.CASH.sub(value), DSL.val(0)))
                .where(USERS.ID.eq(playerId))
                .execute() >= 0;
    }

    public boolean addCash(int playerId, int value) {
        return dsl().update(USERS).set(USERS.CASH, DSL.greatest(USERS.CASH.add(value), DSL.val(0))).where(USERS.ID.eq(playerId)).execute() >= 0;
    }

    public int getCash(int playerId) {
        Record1<Integer> cache = dsl().select(USERS.CASH).from(USERS).where(USERS.ID.eq(playerId)).fetchOne();
        if (cache != null) {
            return cache.get(USERS.CASH);
        }
        return 0;
    }

    public void setCurrentServer(int userId, String bungee, String server) {
        dsl().update(USERS)
                .set(USERS.LAST_SERVER, USERS.SERVER)
                .set(USERS.SERVER, server)
                .set(USERS.BUNGEE, bungee)
                .where(USERS.ID.eq(userId)).execute();
    }

    public void resetBungeePlayers(String bungee) {
        dsl().update(USERS)
                .set(USERS.SERVER, (String) null)
                .set(USERS.BUNGEE, (String) null)
                .set(USERS.SERVER_IDENTIFIER, (String) null)
                .where(USERS.BUNGEE.eq(bungee)).execute();
    }

    public AccountsRecord getAccount(String nickname) {
        return dsl().selectFrom(ACCOUNTS).where(ACCOUNTS.USERNAME.eq(nickname)).fetchAny();
    }


    public void updateName(int id, String newName) {
        dsl().update(USERS).set(USERS.NAME, newName).where(USERS.ID.eq(id)).execute();
    }

    public void setIdentifier(int id, String identifier) {
        dsl().update(USERS).set(USERS.SERVER_IDENTIFIER, identifier).where(USERS.ID.eq(id)).execute();

    }
}
