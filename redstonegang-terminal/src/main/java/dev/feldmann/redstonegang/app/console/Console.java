package dev.feldmann.redstonegang.app.console;

import dev.feldmann.redstonegang.app.RedstoneTerminal;
import dev.feldmann.redstonegang.app.cmds.Comando;
import dev.feldmann.redstonegang.app.console.log.DCLogger;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class Console {

    public static DCLogger log;
    public static boolean doLog = false;
    public static ConsoleReader console;
    public static PrintWriter output;


    public static void init() {
        try {
            console = new ConsoleReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        output = new PrintWriter(console.getOutput());
        log = new DCLogger();

    }

    public static void readConsole() throws IOException {

        String cmd = "";
        CMDLOOP:
        while (!cmd.equalsIgnoreCase("stop")) {
            if (console == null) continue;
            cmd = console.readLine();
            if (cmd == null) continue;
            String[] split = cmd.split(" ");
            if (split.length > 1) {
                cmd = split[0];
            }
            String[] cargs = new String[split.length - 1];
            for (int x = 1; x < split.length; x++) {
                cargs[x - 1] = split[x];
            }
            for (Comando c : RedstoneTerminal.getCmds()) {
                if (c.getName().equalsIgnoreCase(cmd)) {
                    c.exec(cargs);
                    continue CMDLOOP;
                }
            }
            addLog("§cComando '§f" + cmd + "§c' não encontrado! §2Use 'help'");
        }
    }

    public static void addLog(String loga) {
        addLog(loga, true);
    }

    public static void addLog(String loga, boolean file) {
        if (file || doLog) {
            log.log(Level.INFO, loga);
            return;
        }
        outLog(loga);

    }

    private static void outLog(String loga) {
        Date date = new Date();
        SimpleDateFormat datahora = new SimpleDateFormat("[dd/MM hh:mm:ss]");
        output.println(datahora.format(date) + " " + loga);
        output.flush();
    }

}
