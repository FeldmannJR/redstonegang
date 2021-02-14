package dev.feldmann.redstonegang.network.cmds;

import dev.feldmann.redstonegang.app.cmds.Comando;
import dev.feldmann.redstonegang.network.RedstoneNetwork;
import dev.feldmann.redstonegang.network.netty.Cliente;

public class Clientes extends Comando {
    public Clientes() {
        super("clientes", "lista os clientes conectados!");
    }

    @Override
    public void exec(String[] args) {
        out("Conectados: " + RedstoneNetwork.instance.getServer().getClientes().size());
        for (Cliente c : RedstoneNetwork.instance.getServer().getClientes()) {
            out(c.getNome() + "/" + c.getTipo().name() + " - " + c.getAddress());
        }


    }
}
