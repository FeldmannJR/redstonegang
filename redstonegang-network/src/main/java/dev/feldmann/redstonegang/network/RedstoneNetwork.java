/*
 * Projeto feito por Carlos André Feldmann Júnior
 * Agradeciomentos a Isaias Finger e Gabriel Augusto Souza.
 */
package dev.feldmann.redstonegang.network;


import dev.feldmann.redstonegang.app.RedstoneTerminal;
import dev.feldmann.redstonegang.app.console.Console;
import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.RedstoneGangTerminal;
import dev.feldmann.redstonegang.common.config.ServerConfig;
import dev.feldmann.redstonegang.network.cmds.Clientes;
import dev.feldmann.redstonegang.network.cmds.Debug;
import dev.feldmann.redstonegang.network.netty.Server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RedstoneNetwork extends RedstoneTerminal {

    public Server server;
    public static RedstoneNetwork instance;
    public static boolean debug = false;
    public static RedstoneGang rg;

    public void onEnable() {
        rg = new RedstoneGangNetwork();
        instance = this;
        initCmds();
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        log("Iniciando Redstone-Network ...");
        ServerConfig config = rg.config().getConfig();
        String porta;
        if (this.javaArgs.length >= 1) {
            porta = this.javaArgs[0];
        } else {
            porta = config.network_port;
        }
        log("Usando porta " + porta + " com token " + config.network_token);
        server = new Server(porta, config.network_token);
        server.bootServer();

    }

    private void initCmds() {
        addCommand(new Clientes());
        addCommand(new Debug());
    }

    public static void addlog(String s, boolean b) {
        Console.addLog(s, b);
    }

    public Server getServer() {
        return server;
    }
}
