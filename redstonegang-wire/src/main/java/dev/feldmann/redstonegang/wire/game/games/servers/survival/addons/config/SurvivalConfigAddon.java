package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.config;

import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalConfigAddon extends Addon {


    @Override
    public void onEnable() {
        registerCommand(new RedstoneCmd("config") {
            @Override
            public void command(CommandSender sender, Arguments args) {
                new ConfigMenu((Survival) getServer(), RedstoneGangSpigot.getPlayer((Player) sender)).open((Player) sender);
            }
        });
    }
}
