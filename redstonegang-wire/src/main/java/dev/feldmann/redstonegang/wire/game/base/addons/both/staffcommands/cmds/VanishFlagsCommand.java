package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.menus.EditFlagsMenu;
import org.bukkit.entity.Player;

public class VanishFlagsCommand extends PlayerCmd {
    StaffCommandsAddon addon;

    public VanishFlagsCommand(StaffCommandsAddon addon) {
        super("vanishflags", "configura os flags");
        this.addon = addon;
        this.setPermission(addon.CAN_USE_VANISH);
    }

    @Override
    public void command(Player player, Arguments args) {
        new EditFlagsMenu(addon).open(player);
    }
}
