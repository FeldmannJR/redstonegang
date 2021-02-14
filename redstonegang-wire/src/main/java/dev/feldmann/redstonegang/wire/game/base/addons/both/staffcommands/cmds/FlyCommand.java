package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class FlyCommand extends PlayerCmd {
    StaffCommandsAddon addon;

    public FlyCommand(StaffCommandsAddon addon) {
        super("fly", "comando para ativar o modo fly");
        this.addon = addon;
        this.setPermission(addon.CAN_USE_FLY);
    }

    @Override
    public void command(Player player, Arguments args) {
        addon.setFly(player, !addon.isFlyMode(player));
        if (addon.isFlyMode(player)) {
            C.info(player, "Agora você pode voar!");
        } else {
            C.info(player, "Fica no chão mesmo!!!");
        }
    }
}
