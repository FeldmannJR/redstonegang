package dev.feldmann.redstonegang.common.utils.formaters;

import java.sql.Timestamp;

public class TimestampDiff
{
    private Timestamp currentTime;
    private Timestamp futureTime;

    public TimestampDiff(Timestamp currentTime, Timestamp futureTime)
    {
        this.currentTime = currentTime;
        this.futureTime = futureTime;
    }

    public boolean isTimeOut()
    {
        return this.currentTime.after(this.futureTime);
    }

    public long getDifference()
    {
        long milliseconds1 = this.futureTime.getTime();
        long milliseconds2 = this.currentTime.getTime();
        return milliseconds1 - milliseconds2;
    }

    public long getSeconds()
    {
        return this.getDifference() / 1000;
    }

    public long getMinutes()
    {
        return this.getDifference() / (60 * 1000);
    }

    public long getHours()
    {
        return this.getDifference() / (60 * 60 * 1000);
    }

    public long getDays()
    {
        return this.getDifference() / (24 * 60 * 60 * 1000);
    }
}
