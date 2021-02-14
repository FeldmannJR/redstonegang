package dev.feldmann.redstonegang.app;

import dev.feldmann.redstonegang.app.cmds.Comando;
import dev.feldmann.redstonegang.app.cmds.Help;
import dev.feldmann.redstonegang.app.cmds.Stop;
import dev.feldmann.redstonegang.app.cmds.Usage;
import dev.feldmann.redstonegang.app.console.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RedstoneTerminal {
    protected String[] javaArgs;
    private List<Comando> cmds = new ArrayList();


    public void onEnable() {

    }

    public void onDisable() {

    }

    public void log(String s) {
        Console.addLog(s);
    }

    public void addCommand(Comando cmd) {
        cmds.add(cmd);
    }


    private static RedstoneTerminal app;

    public static void start(RedstoneTerminal app) {
        start(app, new String[0]);
    }

    public static void start(RedstoneTerminal app, String[] args) {
        RedstoneTerminal.app = app;
        app.javaArgs = args;
        Console.init();
        app.addCommand(new Help(app.cmds));
        app.addCommand(new Stop());
        app.addCommand(new Usage());
        app.onEnable();
        try {
            Console.readConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handleSigterm() {
        Runtime.getRuntime().addShutdownHook(new Thread(RedstoneTerminal::stop));
    }

    public static void stop() {
        RedstoneTerminal.app.onDisable();
        System.exit(1);
    }

    public static List<Comando> getCmds() {
        if (app != null) {
            return app.cmds;
        }
        return new ArrayList<>();
    }


}
