package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsWarpsRecord;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class DelWarp extends SimpleCmd {


    private static final StringArgument NOME = new StringArgument("nome", false);

    public DelWarp() {
        super("delwarp", "Deleta um warp", "delwarp", NOME);
    }

    @Override
    public void command(Player p, Arguments a) {
        String nome = a.get(NOME);
        SimplecmdsWarpsRecord warp = getAddon().getWarp(nome);
        if (warp == null) {
            C.error(p, "Esta warp não existe!");
            return;
        }
        getAddon().deleteWarp(nome);
        C.info(p, "Warp %% deletado!", nome);
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}