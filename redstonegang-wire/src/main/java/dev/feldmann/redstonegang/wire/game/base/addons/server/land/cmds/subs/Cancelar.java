package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cancelar extends RedstoneCmd {

    LandAddon manager;

    public Cancelar(LandAddon manager) {
        super("cancelar", "cancela a compra do terreno");
        this.manager =manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        Player p = (Player) sender;
        User rg = RedstoneGang.getPlayer(p.getUniqueId());
        if (manager.getPlayer(rg.getId()).buying == null) {
            C.error(p, "Você não está visualizando um terreno!");
        } else {
            C.info(p, "Você cancelou a compra do terreno!");
            manager.removeDisplay(p, manager.getPlayer(rg.getId()).buying);
            manager.getPlayer(rg.getId()).buying = null;

        }
    }


}
