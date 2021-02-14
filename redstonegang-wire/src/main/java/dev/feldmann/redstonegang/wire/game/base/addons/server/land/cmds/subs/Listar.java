package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Listar extends RedstoneCmd {

    LandAddon manager;

    public Listar(LandAddon manager) {
        super("listar", "lista os seus terrenos mostrando a localização");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        User pl = RedstoneGang.getPlayer(((Player) sender).getUniqueId());


        List<Land> terrenos = manager.getTerrenos(pl.getId());
        if (!terrenos.isEmpty()) {
            sender.sendMessage("§7=====- §9" + pl.getName() + " Terrenos §7-=====");
            int x = 1;
            for (Land t : terrenos) {
                double[] point = t.getRegion().getMidPoint();
                sender.sendMessage(x++ + ": §6X:§7" + (int) point[0] + "   §6Z:§7" + (int) point[1]);
            }
        } else {
            C.info(sender, "Sem terrenos!");
        }

    }
}
