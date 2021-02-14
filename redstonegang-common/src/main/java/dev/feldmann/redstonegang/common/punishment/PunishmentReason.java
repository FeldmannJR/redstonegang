package dev.feldmann.redstonegang.common.punishment;

import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsReasonsRecord;

import java.util.ArrayList;
import java.util.List;

public class PunishmentReason
{
    private PunishmentsReasonsRecord data;
    private List<PunishmentReasonDuration> durations = new ArrayList<>();

    public PunishmentReason(PunishmentsReasonsRecord data)
    {
        this.data = data;
    }

    public PunishmentsReasonsRecord getData()
    {
        return this.data;
    }

    public void setDurations(List<PunishmentReasonDuration> durations)
    {
        this.durations = durations;
    }

    public List<PunishmentReasonDuration> getDurations()
    {
        return this.durations;
    }
}
