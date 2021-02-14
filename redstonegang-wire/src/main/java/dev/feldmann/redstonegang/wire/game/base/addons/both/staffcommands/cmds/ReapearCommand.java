package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class ReapearCommand extends PlayerCmd {
    StaffCommandsAddon addon;

    public ReapearCommand(StaffCommandsAddon addon) {
        super("reappear", "volta a aparecer para os jogadores");
        this.addon = addon;
        this.setPermission(addon.CAN_USE_VANISH);
    }

    @Override
    public void command(Player player, Arguments args) {
        if (!addon.isVanished(player)) {
            C.error(player, "Você não está em modo vanish!");
            return;
        }
        addon.setVanished(player, false);
        C.info(player, "Agora os outros jogadores estão vendo você!");
    }
}
