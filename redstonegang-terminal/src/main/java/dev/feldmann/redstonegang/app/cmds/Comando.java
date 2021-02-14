package dev.feldmann.redstonegang.app.cmds;


import dev.feldmann.redstonegang.app.console.Console;

public abstract class Comando {

    private String comando;
    private String help;

    public Comando(String cmd, String help) {
        this.comando = cmd;
        this.help = help;
    }

    public abstract void exec(String[] args);

    public void out(String log, boolean toFile) {
        Console.addLog(log, toFile);
    }

    public void out(String s) {
        Console.addLog(s, true);
    }

    public String getHelp() {
        return help;
    }

    public String getName() {
        return comando;
    }


}
