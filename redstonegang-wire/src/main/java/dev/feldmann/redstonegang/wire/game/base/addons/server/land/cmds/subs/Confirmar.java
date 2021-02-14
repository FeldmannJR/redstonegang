package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.PlayerBuyingInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.PlayerExpandingInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.LandFlagStorage;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Confirmar extends RedstoneCmd {

    LandAddon manager;

    public Confirmar(LandAddon manager) {
        super("confirmar", "confirma a compra do terreno");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        Player p = (Player) sender;
        User rg = RedstoneGang.getPlayer(p.getUniqueId());
        PlayerBuyingInfo info = manager.getPlayer(rg.getId()).buying;
        if (info == null) {
            C.error(p, "Você não está visualizando um terreno!");
        } else {
            if (manager.checagem(p, info)) {

                double preco = Math.max(0, info.getPreco() - manager.getDesconto(rg.getId()));
                if (info instanceof PlayerExpandingInfo) {
                    Land t = ((PlayerExpandingInfo) info).getTerreno();
                    if (manager.canEditTerreno(p, t)) {
                        manager.removeDisplay(p, info);
                        C.info(p, "Você expandiu seu terreno por ## !", preco);
                        info.botaBlocos(t.getId() % 15);
                        if (preco > 0) {
                            manager.a(EconomyAddon.class).remove(p, preco);
                        }
                        manager.getPlayer(t.getOwnerId()).blocosUsados += ((PlayerExpandingInfo) info).getDif();
                        manager.addFreeClaim(rg.getId(), ((PlayerExpandingInfo) info).getDif());
                        t.setRegion(info.getRg());

                    }
                } else {

                    Land t = new Land(manager, -1, rg.getId(), info.getRg(), info.getMundo(), new LandFlagStorage(manager.getFlagsManager()));
                    manager.addTerreno(t);
                    C.info(p, "Você comprou o terreno por ## !", preco);
                    if (preco > 0) {
                        manager.a(EconomyAddon.class).remove(p, preco);
                    }
                    manager.removeDisplay(p, info);
                    manager.addFreeClaim(rg.getId(), info.getRg().getArea());
                    info.botaBlocos(t.getId() % 15);
                }
            }
            manager.getPlayer(rg.getId()).buying = null;

        }
    }


}
