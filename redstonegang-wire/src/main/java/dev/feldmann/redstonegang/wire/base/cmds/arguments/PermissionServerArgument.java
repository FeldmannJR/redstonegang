package dev.feldmann.redstonegang.wire.base.cmds.arguments;

import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PermissionServerArgument extends Argument<PermissionServer> {

    public PermissionServerArgument(String name, boolean optional) {
        super(name, optional);
    }

    public PermissionServerArgument(String name) {
        this(name, false);
    }

    @Override
    public String getErrorMessage(String input) {
        String validos = "";
        for (PermissionServer sv : PermissionServer.values()) {
            if (validos.length() > 0) {
                validos += " | ";
            }
            validos += sv.name().toLowerCase();
        }
        return "Servidor `" + input + "` inválido! Válidos: " + validos;
    }

    @Override
    public PermissionServer process(CommandSender cs, int index, String[] args) {
        if (index >= args.length) {
            return null;
        }
        try {
            PermissionServer sv = PermissionServer.valueOf(args[index].toUpperCase());
            return sv;
        } catch (IllegalArgumentException ex) {

        }
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender cs,String start) {
        List<String> svs = new ArrayList();
        for(PermissionServer sv : PermissionServer.values()){
            svs.add(sv.name());
        }
        return svs;
    }
}
