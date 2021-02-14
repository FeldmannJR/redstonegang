package dev.feldmann.redstonegang.common.player.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.GroupOptionsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.GroupsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PermissionsDescRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables;
import org.jooq.*;

import java.util.HashMap;
import java.util.List;

public class PermissionsDB extends Database {


    public void createTables() {


        safeExecute("CREATE TABLE IF NOT EXISTS `groups`" +
                "(" +
                " `id` INTEGER AUTO_INCREMENT," +
                " `name` VARCHAR(60) UNIQUE, " +
                " `prefix` VARCHAR(45) ," +
                " `suffix` VARCHAR(45) ," +
                " `discord_role` BIGINT ," +
                " `forum_group` INTEGER ," +
                " `parent` INTEGER ," +
                " `default` BIT NULL UNIQUE," +
                "PRIMARY KEY (`id`)" +
                ")");

        safeExecute("ALTER TABLE `groups`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`forum_mod` BOOLEAN DEFAULT 0 AFTER `forum_group`");

        safeExecute("ALTER TABLE `groups`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`display_name` VARCHAR(64) AFTER `name`");

        safeExecute("CREATE TABLE IF NOT EXISTS `group_options` (" +
                "`group` INTEGER NOT NULL, " +
                "`key` VARCHAR(100) NOT NULL, " +
                "`value` DOUBLE," +
                "CONSTRAINT fk_group" +
                "   FOREIGN KEY (`group`)" +
                "   REFERENCES `groups` (id)" +
                "   ON DELETE CASCADE," +
                "PRIMARY KEY (`group`,`key`)" +
                ")");

        safeExecute("CREATE TABLE IF NOT EXISTS `permissions`" +
                "(" +
                " `id`     INTEGER NOT NULL AUTO_INCREMENT ," +
                " `key`    VARCHAR(100) NOT NULL ," +
                " `value`  SMALLINT NOT NULL ," +
                " `owner`   INTEGER NOT NULL ," +
                " `type`   BIT NOT NULL ," +
                " `server` INTEGER NOT NULL ," +
                " CONSTRAINT permission UNIQUE ( `key`,`server`,`owner`,`type` ), " +
                " PRIMARY KEY (`id`)" +
                ")");
        safeExecute("CREATE TABLE IF NOT EXISTS `permissions_desc` (" +
                "`key` VARCHAR(100) NOT NULL, " +
                "`type` BOOLEAN NOT NULL, " +
                "`name` VARCHAR(100) NOT NULL, " +
                "`desc` VARCHAR(250)," +
                "`server` VARCHAR(100) NOT NULL, " +
                "PRIMARY KEY (`type`,`key`,`server`)" +
                ")");
    }

    public void setPermissionsDesc(List<GroupOption> options, PermissionServer sv) {
        dsl().deleteFrom(Tables.PERMISSIONS_DESC).where(Tables.PERMISSIONS_DESC.SERVER.eq(sv.name())).execute();

        InsertValuesStep5<PermissionsDescRecord, String, String, String, String, Byte> columns = dsl().insertInto(Tables.PERMISSIONS_DESC)
                .columns(Tables.PERMISSIONS_DESC.KEY, Tables.PERMISSIONS_DESC.NAME, Tables.PERMISSIONS_DESC.DESC, Tables.PERMISSIONS_DESC.SERVER, Tables.PERMISSIONS_DESC.TYPE);
        for (GroupOption option : options) {
            columns = columns.values(option.getKey(), option.getNome(), option.getDesc(), sv.name(), (byte) (option instanceof PermissionDescription ? 0 : 1));
        }
        columns.execute();

    }


    public HashMap<PermissionServer, HashMap<String, PermissionValue>> getPermissions(PermissionHolder holder) {
        HashMap<PermissionServer, HashMap<String, PermissionValue>> perms = new HashMap<>();
        Result<Record3<String, Short, Integer>> rs = dsl()
                .select(Tables.PERMISSIONS.KEY, Tables.PERMISSIONS.VALUE, Tables.PERMISSIONS.SERVER)
                .from(Tables.PERMISSIONS)
                .where(Tables.PERMISSIONS.OWNER.eq(holder.getIdentifier()))
                .and(Tables.PERMISSIONS.TYPE.eq(holder.getType()))
                .fetch();

        for (Record r : rs) {
            PermissionServer server = PermissionServer.values()[r.get(Tables.PERMISSIONS.SERVER)];
            if (!perms.containsKey(server)) {
                perms.put(server, new HashMap<>());
            }
            perms.get(server).put(r.getValue(Tables.PERMISSIONS.KEY), PermissionValue.values()[r.getValue(Tables.PERMISSIONS.VALUE)]);
        }
        return perms;
    }

    private HashMap<String, Double> loadOptions(int groupId) {
        Result<GroupOptionsRecord> rs = dsl().selectFrom(Tables.GROUP_OPTIONS).where(Tables.GROUP_OPTIONS.GROUP.eq(groupId)).fetch();
        HashMap<String, Double> options = new HashMap<>();
        for (GroupOptionsRecord r : rs) {
            options.put(r.getKey(), r.getValue());
        }
        return options;
    }

    public void setOption(Group p, String key, double value) {
        dsl().insertInto(Tables.GROUP_OPTIONS)
                .columns(Tables.GROUP_OPTIONS.GROUP, Tables.GROUP_OPTIONS.KEY, Tables.GROUP_OPTIONS.VALUE)
                .values(p.getIdentifier(), key, value)
                .onDuplicateKeyUpdate().set(Tables.GROUP_OPTIONS.VALUE, value).execute();
    }

    public void deleteOption(Group p, String key) {
        dsl().deleteFrom(Tables.GROUP_OPTIONS).where(Tables.GROUP_OPTIONS.GROUP.eq(p.getIdentifier())).and(Tables.GROUP_OPTIONS.KEY.eq(key)).execute();
    }


    protected HashMap<Integer, Group> loadGroups() {
        HashMap<Integer, Group> groups = new HashMap();
        Result<GroupsRecord> rs = dsl().selectFrom(Tables.GROUPS).fetch();
        for (GroupsRecord r : rs) {
            Group g = new Group(r);
            g.setOptions(loadOptions(g.getIdentifier()));
            g.setPermissionList(getPermissions(g));
            groups.put(g.getIdentifier(), g);

        }
        return groups;
    }

    public boolean setGroup(User user, Integer groupId) {
        int execute = dsl()
                .update(Tables.USERS)
                .set(Tables.USERS.GROUP, groupId)
                .where(Tables.USERS.ID.eq(user.getId()))
                .execute();
        return execute != 0;
    }

    public boolean updatePrefixAndSuffix(Group gr) {
        return dsl()
                .update(Tables.GROUPS)
                .set(Tables.GROUPS.PREFIX, gr.getPrefix())
                .set(Tables.GROUPS.SUFFIX, gr.getSuffix())
                .where(Tables.GROUPS.ID.eq(gr.getIdentifier())).execute() > 0;
    }

    public boolean setGroupParent(Group gr, Integer parent) {
        return dsl().update(Tables.GROUPS)
                .set(Tables.GROUPS.PARENT, parent)
                .where(Tables.GROUPS.ID.eq(gr.getIdentifier()))
                .execute() > 0;
    }

    public boolean createGroup(String nome, String prefix, String suffix, boolean defaultGroups) {
        //JOOQ
        if (dsl()
                .insertInto(Tables.GROUPS)
                .columns(Tables.GROUPS.NAME, Tables.GROUPS.PREFIX, Tables.GROUPS.SUFFIX, Tables.GROUPS.DEFAULT)
                .values(nome, prefix, suffix, defaultGroups ? true : null)
                .onDuplicateKeyIgnore()
                .execute() > 0) {
            RedstoneGang.instance.user().getPermissions().reloadGroups();
            return true;
        } else {
            return false;
        }
    }


    public boolean setPermission(PermissionHolder holder, PermissionServer server, String key, PermissionValue value) {
        dsl()
                .insertInto(Tables.PERMISSIONS)
                .columns(Tables.PERMISSIONS.OWNER, Tables.PERMISSIONS.SERVER, Tables.PERMISSIONS.TYPE, Tables.PERMISSIONS.KEY, Tables.PERMISSIONS.VALUE)
                .values(holder.getIdentifier(), server.ordinal(), holder.getType(), key, (short) value.ordinal())
                .onDuplicateKeyUpdate().set(Tables.PERMISSIONS.VALUE, (short) value.ordinal())
                .execute();
        return false;
    }


}
