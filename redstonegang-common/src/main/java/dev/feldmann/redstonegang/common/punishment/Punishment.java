package dev.feldmann.redstonegang.common.punishment;

import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsRecord;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.common.utils.formaters.TimestampDiff;

import java.sql.Timestamp;

public class Punishment
{
    private PunishmentsRecord data;
    private PunishmentReason reason;

    public Punishment(PunishmentsRecord data)
    {
        this.data = data;
    }

    public PunishmentsRecord getData()
    {
        return this.data;
    }

    public void setReason(PunishmentReason reason)
    {
        this.reason = reason;
    }

    public PunishmentReason getReason()
    {
        return this.reason;
    }

    public TimestampDiff getTimeLeft()
    {
        Timestamp nowTime = DateUtils.getCurrentTime();
        Timestamp endTime = this.getData().getEnd();
        if (this.getData().getEnd() == null)
        {
            endTime = DateUtils.getCurrentTime();
        }
        return new TimestampDiff(nowTime, endTime);
    }

    public boolean isActive()
    {
        if (this.getData().getRemove() == null)
        {
            if (this.getData().getEnd() == null)
            {
                return true;
            }
            return !this.getTimeLeft().isTimeOut();
        }
        return false;
    }

}
