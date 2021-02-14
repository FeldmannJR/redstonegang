package dev.feldmann.redstonegang.app.cmds;

import java.util.List;

public class Help extends Comando {


    List<Comando> comandos;

    public Help(List<Comando> cmds) {
        super("help", "mostra os comandos");
        this.comandos = cmds;
    }

    @Override
    public void exec(String[] args) {
        out("§b§lComandos:");
        for (Comando c : comandos) {
            out("§a" + c.getName() + " §f- §e" + c.getHelp());
        }
    }
}
