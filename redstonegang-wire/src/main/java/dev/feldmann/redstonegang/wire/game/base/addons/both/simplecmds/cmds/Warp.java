package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsWarpsRecord;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Warp extends SimpleCmd {


    private static final StringArgument NOME = new StringArgument("nome", true);

    public Warp() {
        super("warp", "Vai para uma warp", "warp", NOME);
    }

    @Override
    public void command(Player p, Arguments a) {
        if (!a.isPresent(NOME)) {
            StringBuilder listWarps = new StringBuilder();
            int qtd = 0;
            Collection<SimplecmdsWarpsRecord> warps = getAddon().getWarps();
            for (SimplecmdsWarpsRecord warp : warps) {
                if (warp.getPublic() || getAddon().hasWarpPermission(p, warp.getName())) {
                    if (qtd % 2 == 0) {
                        listWarps.append("§f");
                    } else {
                        listWarps.append("§b");
                    }
                    if (qtd > 0) {
                        listWarps.append(" ");
                    }
                    listWarps.append(warp.getName());
                    qtd++;
                }
            }
            p.sendMessage("§9Warps: " + listWarps.toString());
            return;
        }
        String nome = a.get(NOME);
        SimplecmdsWarpsRecord warp = getAddon().getWarp(nome);
        if (warp == null) {
            C.error(p, "Esta warp não existe!");
            return;
        }
        if (!warp.getPublic() && !getAddon().hasWarpPermission(p, nome)) {
            C.error(p, "Você não tem permissão para usar esta warp!");
            return;
        }
        getAddon().getServer().teleport(p, BungeeLocation.fromString(warp.getLocation()));
        C.info(p, "Teleportado para a warp %%!", nome);
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}