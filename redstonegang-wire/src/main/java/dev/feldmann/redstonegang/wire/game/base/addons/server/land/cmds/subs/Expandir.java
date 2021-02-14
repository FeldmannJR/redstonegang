package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.PlayerExpandingInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import dev.feldmann.redstonegang.wire.utils.location.LocUtils;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Expandir extends RedstoneCmd {


    private static final IntegerArgument TAMANHO = new IntegerArgument("tamanho", false, 1, 50);
    LandAddon manager;

    public Expandir(LandAddon manager) {
        super("expandir", "olhe para o lado que deseja expandir", TAMANHO);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;
        User rg = RedstoneGang.getPlayer(p.getUniqueId());
        Land terreno = manager.getTerreno(p);


        if (manager.getPlayer(rg.getId()).buying != null) {
            manager.getPlayer(rg.getId()).sendAlreadyEditing();
            return;
        }
        if (manager.canEditTerreno(p, terreno)) {
            BlockFace direction = LocUtils.yawToFace(p.getLocation().getYaw());
            Hitbox2D newbox = terreno.getRegion().expands(direction, args.get(TAMANHO));

            double relacao;
            if (newbox.getWidth() > newbox.getHeight()) {
                relacao = (double) newbox.getWidth() / (double) newbox.getHeight();
            } else {
                relacao = (double) newbox.getHeight() / (double) newbox.getWidth();
            }
            if (relacao > 3) {
                C.error(p, "Um lado do terreno não pode ser %% maior que o outro!", "3x");
                return;
            }

            PlayerExpandingInfo info = new PlayerExpandingInfo(newbox, terreno.getWorldName(), System.currentTimeMillis(), terreno);
            if (manager.checagem(p, info)) {
                manager.getPlayer(rg.getId()).buying = info;
                manager.getPlayer(rg.getId()).buyingStart = System.currentTimeMillis();
                manager.display(p, info);
                manager.getPlayer(rg.getId()).sendConfirmMessage();
                C.info(p, "A expansão irá custar ## !", Math.max(0, info.getPreco() - manager.getDesconto(rg.getId())));
                return;
            }
        }
    }

    @Override
    public String getLongHelp() {
        return "Para expandir seu terreno basta olhar para a direção que quer expandir, e executar o comando com o numero de blocos que quer aumentar, o terreno só aumentará para o lado que você olhou!";
    }
}
