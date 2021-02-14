package dev.feldmann.redstonegang.common.punishment;

import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsRecord;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import net.md_5.bungee.api.ChatColor;

import java.sql.Timestamp;
import java.util.List;

public class PunishmentManager
{

    private PunishmentDB db;

    public PunishmentManager()
    {
        db = new PunishmentDB();
    }

    public Punishment newPunishment()
    {
        return this.db.newPunishment();
    }

    public Punishment newPunishment(Punish punish, User punishedUser, String punishedIp, User opUser, PunishmentReason reason, String note)
    {
        Punishment punishment = this.newPunishment();
        punishment.setReason(reason);
        PunishmentsRecord data = punishment.getData();
        data.setPunish(punish.getName());
        data.setUserId(punishedUser.getId());
        data.setUserIp(punishedIp);

        Timestamp endTime = null;
        if(reason.getDurations().size() > 0)
        {
            PunishmentReasonDuration reasonDuration = null;
            int durationNext = this.db.loadTotalPunishmentsReasons(punishedUser.getId(), reason.getData().getId());
            if (durationNext >= reason.getDurations().size())
            {
                durationNext = reason.getDurations().size() - 1;
                reasonDuration = reason.getDurations().get(durationNext);
            }
            else
            {
                reasonDuration = reason.getDurations().get(durationNext);
            }

            if (reasonDuration.getData().getDuration() != 0)
            {
                endTime = DateUtils.addMinutesToDate(DateUtils.getCurrentTime(), reasonDuration.getData().getDuration());
            }
        }

        data.setEnd(endTime);
        data.setOpPunishmentId(opUser.getId());
        data.setPunishmentReasonId(reason.getData().getId());
        data.setPunishmentNote(note);
        data.store();
        return punishment;
    }

    public Punishment removePunishment(Punishment punishment, User opUser, PunishmentReason reason, String note)
    {
        PunishmentsRecord data = punishment.getData();
        data.setRemove(DateUtils.getCurrentTime());
        data.setOpRemoveId(opUser.getId());
        data.setRemoveReasonId(reason.getData().getId());
        data.setRemoveNote(note);
        data.store();
        return punishment;
    }

    public List<Punishment> getPunishments(User pl)
    {
        return this.db.loadPunishments(pl.getId());
    }

    public List<Punishment> getBansPunishments(User pl)
    {
        return this.db.loadPunishments(Punish.BAN, pl.getId());
    }

    public Punishment hasBanned(User pl)
    {
        return db.hasPunished(Punish.BAN, pl.getId());
    }

    public boolean isBanned(User pl)
    {
        return this.hasBanned(pl) != null;
    }

    public List<Punishment> getMutesPunishments(User pl)
    {
        return this.db.loadPunishments(Punish.MUTE, pl.getId());
    }

    public Punishment hasMuted(User pl)
    {
        return db.hasPunished(Punish.MUTE, pl.getId());
    }

    public boolean isMuted(User pl)
    {
        return this.hasMuted(pl) != null;
    }

    public List<PunishmentReason> getReasons(Punish.Reason punishReason)
    {
        return this.db.loadPunishmentsReasons(punishReason);
    }

    public List<PunishmentReason> getBanReasons()
    {
        return this.getReasons(Punish.Reason.BAN);
    }

    public List<PunishmentReason> getUnBanReasons()
    {
        return this.getReasons(Punish.Reason.UNBAN);
    }

    public List<PunishmentReason> getMuteReasons()
    {
        return this.getReasons(Punish.Reason.MUTE);
    }

    public List<PunishmentReason> getUnMuteReasons()
    {
        return this.getReasons(Punish.Reason.UNMUTE);
    }

    public String getKickMsg(Punishment punishment)
    {
        if(punishment != null)
        {
            return ChatColor.RED
                    + "Você está Banido! Motivo: " + ChatColor.YELLOW + punishment.getReason().getData().getNote() + ChatColor.WHITE + " | " + ChatColor.GREEN + "Restam: " + punishment.getTimeLeft().getMinutes() + " Minuto(s). "
                    + "\n" + ChatColor.RED + "+ Informações: " + ChatColor.YELLOW + punishment.getData().getPunishmentNote()
                    + "\n§b§lPara saber como ser desbanido entre no link: §f§l§nhttp://www.redstonegang.com.br/"
                    + "\n§b§lLeia nossas regras: §f§l§nhttp://www.redstonegang.com.br/regras/";
        }
        return "";
    }

}
