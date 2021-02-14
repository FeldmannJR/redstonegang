package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.subs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.PlayerBuyingInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comprar extends RedstoneCmd {

    public static final IntegerArgument TAMANHO_X = new IntegerArgument("tamanhoX", 10, 100);
    public static final IntegerArgument TAMANHO_Y = new IntegerArgument("tamanhoY", 10, 100);

    LandAddon manager;

    public Comprar(LandAddon manager) {
        super("comprar", "comprar terreno", TAMANHO_X, TAMANHO_Y);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player p = (Player) sender;
        User rg = RedstoneGang.getPlayer(p.getUniqueId());
        if (manager.getPlayer(rg.getId()).buying == null) {
            int tamX = args.get(TAMANHO_X);
            int tamY = args.get(TAMANHO_Y);
            int x = args.get(TAMANHO_X) / 2;
            int y = args.get(TAMANHO_Y) / 2;
            int bX = p.getLocation().getBlockX();
            int bY = p.getLocation().getBlockZ();
            int minX = bX - x;
            int maxX = bX + x - (tamX % 2 == 0 ? 1 : 0);
            int minY = bY - y;
            int maxY = bY + y - (tamY % 2 == 0 ? 1 : 0);
            Hitbox2D hit = new Hitbox2D(minX, minY, maxX, maxY);

            double relacao;
            if (hit.getWidth() > hit.getHeight()) {
                relacao = (double) hit.getWidth() / (double) hit.getHeight();
            } else {
                relacao = (double) hit.getHeight() / (double) hit.getWidth();
            }
            if (relacao > 3) {
                C.error(p, "Um lado do terreno não pode ser 3x maior que o outro!");
                return;
            }


            PlayerBuyingInfo info = new PlayerBuyingInfo(hit, p.getWorld().getName(), System.currentTimeMillis());
            if (manager.checagem(p, info)) {
                manager.display(p, info);
                manager.getPlayer(rg.getId()).buying = info;
                manager.getPlayer(rg.getId()).buyingStart = System.currentTimeMillis();
                manager.getPlayer(rg.getId()).sendConfirmMessage();
                C.info(p, "O terreno irá custar ## !", Math.max(0, info.getPreco() - manager.getDesconto(rg.getId())));
            }
        } else {
            manager.getPlayer(rg.getId()).sendAlreadyEditing();
        }
    }


}
