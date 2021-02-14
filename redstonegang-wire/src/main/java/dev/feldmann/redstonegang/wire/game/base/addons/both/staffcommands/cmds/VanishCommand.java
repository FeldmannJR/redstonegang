package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class VanishCommand extends PlayerCmd {
    StaffCommandsAddon addon;

    public VanishCommand(StaffCommandsAddon addon) {
        super("vanish", "some do jogo para os outros jogadores");
        this.addon = addon;
        this.setPermission(addon.CAN_USE_VANISH);
    }

    @Override
    public void command(Player player, Arguments args) {
        if (addon.isVanished(player)) {
            C.error(player, "Você já está em modo vanish!");
            return;
        }
        addon.setVanished(player, true);
        C.info(player, "Você entrou em modo vanish!");
    }
}
