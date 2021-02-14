package dev.feldmann.redstonegang.app.console.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.LogRecord;

public class LogDispatcher extends Thread {

    public static DCLogger logger;
    public static BlockingQueue<LogRecord> queue = new LinkedBlockingQueue<>();

    public LogDispatcher(DCLogger logger)
    {
        super("RedstoneGang");
        LogDispatcher.logger = logger;
    }

    @Override
    public void run()
    {
        while (!isInterrupted())
        {
            LogRecord record;
            try
            {
                record = queue.take();
            }
            catch (InterruptedException ex)
            {
                continue;
            }
            logger.doLog(record);
        }
        for (LogRecord record : queue)
        {
            logger.doLog(record);
        }
    }

    public void queue(LogRecord record)
    {
        if (!isInterrupted())
        {
            queue.add(record);
        }
    }
}
