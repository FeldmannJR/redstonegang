package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.events.PlayerDeleteLand;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.world.WorldUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Deletar extends RedstoneCmd {
    LandAddon manager;

    public Deletar(LandAddon manager) {
        super("deletar", "Deleta e regenera o terreno que você está");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        Player p = (Player) sender;
        Land terre = manager.getTerreno(p);
        if (terre == null) {
            C.error(p, "Você não está em um terreno!");
            return;
        }
        if (Wire.callEvent(new PlayerDeleteLand(p, terre, true))) {
            return;
        }

        manager.removeTerreno(terre);
        WorldUtils.regenerate(terre.getRegion(), terre.getWorld());
        C.info(p, " Terreno deletado!");
    }
}
