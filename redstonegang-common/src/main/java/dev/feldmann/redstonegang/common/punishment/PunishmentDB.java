package dev.feldmann.redstonegang.common.punishment;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsReasonsDurationsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsReasonsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

public class PunishmentDB extends Database {

    public PunishmentDB() {
        super("redstonegang_common", true);
    }

    @Override
    public void createTables() {
        safeExecute("CREATE TABLE IF NOT EXISTS `punishments_reasons` (\n" +
                " `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                " `type` enum('ban','unban','mute','unmute') NOT NULL DEFAULT 'ban',\n" +
                " `name` varchar(250) NOT NULL,\n" +
                " `note` varchar(250) NOT NULL,\n" +
                " `can_remove` tinyint(1) NOT NULL DEFAULT 1,\n" +
                " PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci");

        safeExecute("ALTER TABLE `punishments_reasons`" +
                "ADD COLUMN IF NOT EXISTS " +
                "`can_remove` tinyint(1) NOT NULL DEFAULT 1 " +
                "AFTER `note`");

        safeExecute("CREATE TABLE IF NOT EXISTS `punishments_reasons_durations` (\n" +
                " `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                " `punishment_reason_id` int(11) NOT NULL,\n" +
                " `in_order` int(11) NOT NULL,\n" +
                " `duration` int(11) NOT NULL,\n" +
                " PRIMARY KEY (`id`),\n" +
                " KEY `punishment_reason_id` (`punishment_reason_id`),\n" +
                " CONSTRAINT `punishments_reasons_durations_ibfk_1` FOREIGN KEY (`punishment_reason_id`) REFERENCES `punishments_reasons` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci");

        safeExecute("CREATE TABLE IF NOT EXISTS `punishments` (\n" +
                " `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                " `punish` enum('ban','mute') NOT NULL DEFAULT 'ban',\n" +
                " `user_id` int(11) NOT NULL,\n" +
                " `user_ip` varchar(250) NOT NULL,\n" +
                " `start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                " `end` timestamp NULL DEFAULT NULL,\n" +
                " `remove` timestamp NULL DEFAULT NULL,\n" +
                " `op_punishment_id` int(11) NOT NULL,\n" +
                " `op_remove_id` int(11) DEFAULT NULL,\n" +
                " `punishment_reason_id` int(11) NOT NULL,\n" +
                " `punishment_note` varchar(250) DEFAULT NULL,\n" +
                " `remove_reason_id` int(11) DEFAULT NULL,\n" +
                " `remove_note` varchar(250) DEFAULT NULL,\n" +
                " PRIMARY KEY (`id`),\n" +
                " KEY `user_id` (`user_id`),\n" +
                " KEY `op_punishment_id` (`op_punishment_id`),\n" +
                " KEY `op_remove_id` (`op_remove_id`),\n" +
                " KEY `punishment_reason_id` (`punishment_reason_id`),\n" +
                " KEY `remove_reason_id` (`remove_reason_id`),\n" +
                " CONSTRAINT `punishments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),\n" +
                " CONSTRAINT `punishments_ibfk_2` FOREIGN KEY (`op_punishment_id`) REFERENCES `users` (`id`),\n" +
                " CONSTRAINT `punishments_ibfk_3` FOREIGN KEY (`op_remove_id`) REFERENCES `users` (`id`),\n" +
                " CONSTRAINT `punishments_ibfk_4` FOREIGN KEY (`punishment_reason_id`) REFERENCES `punishments_reasons` (`id`),\n" +
                " CONSTRAINT `punishments_ibfk_5` FOREIGN KEY (`remove_reason_id`) REFERENCES `punishments_reasons` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci");
    }

    public Punishment newPunishment()
    {
        PunishmentsRecord data = dsl().newRecord(Tables.PUNISHMENTS);
        return new Punishment(data);
    }

    public List<Punishment> loadPunishments(Condition cond)
    {
        List<Punishment> punishments = new ArrayList<>();
        Result<Record> rs = dsl()
                .select().from(Tables.PUNISHMENTS)
                .where(cond)
                .fetch();

        for (Record r : rs)
        {
            Punishment punishment = new Punishment((PunishmentsRecord) r);
            punishment.setReason(this.loadPunishmentsReason(punishment.getData().getPunishmentReasonId()));
            punishments.add(punishment);
        }
        return punishments;
    }

    public List<Punishment> loadPunishments(int user_id)
    {
        return loadPunishments(Tables.PUNISHMENTS.USER_ID.eq(user_id));
    }

    public List<Punishment> loadPunishments(Punish punish, int user_id)
    {
        return loadPunishments(
                Tables.PUNISHMENTS.USER_ID.eq(user_id)
                    .and(Tables.PUNISHMENTS.PUNISH.eq(punish.getName()))
        );
    }

    public Punishment hasPunished(Punish punish, int user_id)
    {
        for (Punishment punishment : this.loadPunishments(
                Tables.PUNISHMENTS.USER_ID.eq(user_id)
                    .and(Tables.PUNISHMENTS.PUNISH.eq(punish.getName()))
                    .and(Tables.PUNISHMENTS.REMOVE.isNull())
        ))
        {
            if (punishment.isActive())
            {
                return punishment;
            }
        }
        return null;
    }

    public List<PunishmentReason> loadPunishmentsReasons(Condition cond)
    {
        List<PunishmentReason> reasons = new ArrayList<>();
        Result<Record> rs = dsl()
                .select().from(Tables.PUNISHMENTS_REASONS)
                .where(cond)
                .fetch();

        for (Record r : rs)
        {
            PunishmentReason reason = new PunishmentReason((PunishmentsReasonsRecord) r);
            reason.setDurations(this.loadPunishmentsReasonsDurations(reason.getData().getId()));
            reasons.add(reason);
        }
        return reasons;
    }

    public List<PunishmentReason> loadPunishmentsReasons(Punish.Reason punishReason)
    {
        return this.loadPunishmentsReasons(
                Tables.PUNISHMENTS_REASONS.TYPE.equalIgnoreCase(punishReason.getName())
        );
    }

    public PunishmentReason loadPunishmentsReason(int punishment_reason_id)
    {
        return this.loadPunishmentsReasons(
                Tables.PUNISHMENTS_REASONS.ID.eq(punishment_reason_id)
        ).get(0);
    }

    public List<PunishmentReasonDuration> loadPunishmentsReasonsDurations(Condition cond)
    {
        List<PunishmentReasonDuration> durations = new ArrayList<>();
        Result<Record> rs = dsl()
                .select().from(Tables.PUNISHMENTS_REASONS_DURATIONS)
                .where(cond)
                .orderBy(Tables.PUNISHMENTS_REASONS_DURATIONS.IN_ORDER)
                .fetch();

        for (Record r : rs)
        {
            durations.add(new PunishmentReasonDuration((PunishmentsReasonsDurationsRecord) r));
        }
        return durations;
    }

    public List<PunishmentReasonDuration> loadPunishmentsReasonsDurations(int punishment_reason_id)
    {
        return this.loadPunishmentsReasonsDurations(
                Tables.PUNISHMENTS_REASONS_DURATIONS.PUNISHMENT_REASON_ID.eq(punishment_reason_id)
        );
    }

    public int loadTotalPunishments(Condition cond)
    {
        int count = dsl()
                .selectCount()
                .from(Tables.PUNISHMENTS)
                .where(cond)
                .fetchOne(0, int.class);
        return count;
    }

    public int loadTotalPunishmentsReasons(int user_id, int punishment_reason_id)
    {
        return this.loadTotalPunishments(
                Tables.PUNISHMENTS.USER_ID.eq(user_id)
                        .and(Tables.PUNISHMENTS.PUNISHMENT_REASON_ID.eq(punishment_reason_id))
        );
    }

}
