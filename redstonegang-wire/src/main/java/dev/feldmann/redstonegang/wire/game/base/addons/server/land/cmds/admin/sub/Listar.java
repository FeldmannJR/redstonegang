package dev.feldmann.redstonegang.wire.game.base.addons.server.land.cmds.admin.sub;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Listar extends RedstoneCmd {
    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false);
    LandAddon manager;

    public Listar(LandAddon manager) {
        super("listar", "lista os terrenos de um player", PLAYER);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        List<Land> terrenos = manager.getTerrenos(args.get(PLAYER).getId());
        Player p = (Player) sender;
        p.sendMessage("§bTerrenos §f" + args.get(PLAYER).getName());
        for (Land t : terrenos) {
            double[] med = t.getRegion().getMidPoint();
            TextComponent txt = new TextComponent("§6" + t.getId() + ". §aX: " + med[0] + " Z: " + med[1]);
            txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tphigher " + med[0] + " " + med[1]));
            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique para TP!")));
            p.spigot().sendMessage(txt);
        }
    }
}
