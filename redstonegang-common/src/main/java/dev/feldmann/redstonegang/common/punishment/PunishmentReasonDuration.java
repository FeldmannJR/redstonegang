package dev.feldmann.redstonegang.common.punishment;

import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PunishmentsReasonsDurationsRecord;

public class PunishmentReasonDuration
{
    private PunishmentsReasonsDurationsRecord data;

    public PunishmentReasonDuration(PunishmentsReasonsDurationsRecord data)
    {
        this.data = data;
    }

    public PunishmentsReasonsDurationsRecord getData()
    {
        return this.data;
    }

}
