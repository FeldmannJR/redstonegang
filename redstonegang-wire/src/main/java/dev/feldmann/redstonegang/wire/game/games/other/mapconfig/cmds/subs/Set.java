package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Set extends RedstoneCmd {
    MapConfigGame game;

    public Set(MapConfigGame game) {
        super("set", "Seta configs(locs/blocks/regions) do mapa");
        this.game = game;
    }

    @Override
    public void command(CommandSender cs, Arguments args) {
        if (game.getConfigurando() == null) {
            C.error(cs, "Vocẽ precisa configurar um mapa use %cmd% ou %cmd%", "/mapa criar", "/mapa troca");
            return;
        }
        game.config().setMenu.open((Player) cs);

    }
}
