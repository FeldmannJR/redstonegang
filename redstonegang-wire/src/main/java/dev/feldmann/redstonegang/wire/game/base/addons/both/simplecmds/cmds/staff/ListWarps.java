package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsWarpsRecord;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ListWarps extends SimpleCmd {


    public ListWarps() {
        super("listwarps", "lista todos os warps", "listwarps");
    }

    @Override
    public void command(Player p, Arguments a) {
        StringBuilder listWarps = new StringBuilder();
        int qtd = 0;
        Collection<SimplecmdsWarpsRecord> warps = getAddon().getWarps();
        for (SimplecmdsWarpsRecord warp : warps) {
            if (qtd % 2 == 0) {
                listWarps.append("§f");
            } else {
                listWarps.append("§b");
            }
            if (qtd > 0) {
                listWarps.append(" ");
            }
            listWarps.append(warp.getName());
            if (warp.getPublic()) {
                listWarps.append("(pública)");
            }
            qtd++;
        }
        p.sendMessage("§9Warps: " + listWarps.toString());

    }

    @Override
    public boolean canOverride() {
        return true;
    }
}