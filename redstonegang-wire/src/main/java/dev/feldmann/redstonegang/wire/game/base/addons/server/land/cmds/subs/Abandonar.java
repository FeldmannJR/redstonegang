package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandPlayer;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.events.PlayerDeleteLand;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.world.WorldUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Abandonar extends RedstoneCmd {


    private static final StringArgument CODIGO = new StringArgument("codigo", true, false);

    private Cooldown cd = new Cooldown(5000);
    LandAddon manager;

    public Abandonar(LandAddon manager) {
        super("abandonar", "olhe para o lado que deseja expandir", CODIGO);
        this.manager = manager;
    }


    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;
        User rg = RedstoneGang.getPlayer(p.getUniqueId());
        LandPlayer pl = manager.getPlayer(rg.getId());

        if (manager.getPlayer(rg.getId()).buying != null) {
            C.error(p, "Você está visualizando um terreno! Caso queira comprar use `%%`, caso não use `%%`", "/terreno confirmar", "/terreno cancelar");
            return;
        }
        if (cd.isCooldown(p.getUniqueId())) {
            C.error(p, "Espere um pouco para poder usar o comando novamente!");
            return;
        }

        if (args.isPresent(CODIGO)) {
            if (pl.deletando != null) {
                if (pl.codigo.equals(args.get(CODIGO))) {
                    if (System.currentTimeMillis() < (pl.startedDeletando + 30000)) {
                        if (manager.canEditTerreno(p, pl.deletando)) {
                            if (Wire.callEvent(new PlayerDeleteLand(p, pl.deletando, false))) {
                                pl.deletando = null;
                                pl.codigo = null;
                                return;
                            }
                            manager.removeTerreno(pl.deletando);
                            WorldUtils.regenerate(pl.deletando.getRegion(), pl.deletando.getWorld());
                            C.info(p, "Terreno abandonado!");
                            cd.addCooldown(p.getUniqueId());
                            pl.deletando = null;
                            pl.codigo = null;
                        } else {
                            pl.deletando = null;
                            pl.codigo = null;
                            C.error(p, "Expirou o tempo de deletar o terreno! Tente novamente!");
                        }
                    }
                } else {
                    C.error(p, "Código inválido!");
                }
            }
        } else {

            Land terreno = manager.getTerreno(p);
            if (manager.canEditTerreno(p, terreno)) {
                String codigo = RandomUtils.randomInt(10, 100) + "";
                pl.setDeletando(terreno, codigo);
                C.info(p, "Para abandonar o terreno digite %%", "/" + this.getFullCommand() + " " + codigo);
            }
        }
    }


}
