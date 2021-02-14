package dev.feldmann.redstonegang.common.shop.magento;

import dev.feldmann.redstonegang.common.db.jooq.redstonegang_magento.tables.records.DrmagentoGamecodesCodeRecord;

public class MagentoCode
{
    private DrmagentoGamecodesCodeRecord data;

    public MagentoCode(DrmagentoGamecodesCodeRecord data)
    {
        this.data = data;
    }

    public DrmagentoGamecodesCodeRecord getData()
    {
        return this.data;
    }

    public boolean isLocked()
    {
        return this.data.getLock() != 0;
    }

    public void lock()
    {
        this.data.setLock((byte) 1);
        this.data.store();
    }

    public void unLock()
    {
        this.data.setLock((byte) 0);
        this.data.store();
    }

    public boolean isActive()
    {
        return this.data.getActivedate() != null;
    }
}
