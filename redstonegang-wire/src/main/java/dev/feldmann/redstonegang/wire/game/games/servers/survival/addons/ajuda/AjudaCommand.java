package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import org.bukkit.entity.Player;

public class AjudaCommand extends PlayerCmd {
    private static final StringArgument PAGE = new StringArgument("pagina", true);
    SurvivalAjudaAddon addon;

    public AjudaCommand(SurvivalAjudaAddon addon) {
        super("ajuda", "comando de ajuda do survival",PAGE);
        this.addon = addon;
    }

    @Override
    public void command(Player player, Arguments args) {
        if (args.isPresent(PAGE)) {
            AjudaPage found = addon.findWithSlug(args.get(PAGE));
            if (found != null) {
                found.openPage(player);
                return;
            }
        }
        addon.openMainMenu(player);
    }
}
