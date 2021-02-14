package dev.feldmann.redstonegang.app.console.log;



import dev.feldmann.redstonegang.app.console.Console;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class DCLogger extends Logger {

    public static Formatter formatter = new ConciseFormatter();
    public LogDispatcher dispatcher = new LogDispatcher(this);

    public DCLogger()
    {
        super("RedstoneGang", null);
        setLevel(Level.ALL);
        try
        {
            new File("./logs").mkdir();
            FileHandler handler = new FileHandler("logs/app.log", 1 << 24, 8, true);
            handler.setFormatter(formatter);
            addHandler(handler);
            ColouredWriter consoleHandler = new ColouredWriter(Console.console);
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(formatter);
            addHandler(consoleHandler);
        }
        catch (IOException ex)
        {
            System.err.println("Falha ao registrar arquivo Log!");
            ex.printStackTrace();
        }
        dispatcher.start();
    }

    @Override
    public void log(LogRecord record)
    {
        dispatcher.queue(record);
    }

    void doLog(LogRecord record)
    {
        super.log(record);
    }
}
