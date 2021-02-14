package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class GodCommand extends PlayerCmd {
    StaffCommandsAddon addon;

    public GodCommand(StaffCommandsAddon addon) {
        super("god", "fica invencível");
        this.addon = addon;
        this.setPermission(addon.CAN_USE_GOD);
    }

    @Override
    public void command(Player player, Arguments args) {
        addon.setGod(player, !addon.isGodMode(player));
        if (addon.isGodMode(player)) {
            C.info(player, "Agora você está em modo DEUS!");
        } else {
            C.info(player, "Agora você pode tomar dano novamente!!!");
        }
    }
}
