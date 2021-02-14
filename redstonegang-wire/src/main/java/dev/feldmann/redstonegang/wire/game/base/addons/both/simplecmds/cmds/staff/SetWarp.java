package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.BooleanArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class SetWarp extends SimpleCmd {


    private static final StringArgument NOME = new StringArgument("nome", false);
    private static final BooleanArgument PUBLIC = new BooleanArgument("publica", true, false);


    public SetWarp() {
        super("setwarp", "Seta uma warp no servidor", "setwarp", NOME, PUBLIC);
    }

    @Override
    public void command(Player p, Arguments a) {
        boolean publica = a.get(PUBLIC);
        String nome = a.get(NOME).toLowerCase();
        getAddon().setWarp(nome, p.getLocation(), publica);
        if (publica)
            C.info(p, "Warp pública %% setada para a localização atual!", nome);
        else
            C.info(p, "Warp privada %% setada para a localização atual!", nome);

    }

    @Override
    public boolean canOverride() {
        return true;
    }
}