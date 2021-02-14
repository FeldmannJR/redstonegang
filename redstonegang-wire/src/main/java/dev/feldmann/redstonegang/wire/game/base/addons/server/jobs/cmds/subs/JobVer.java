package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.subs;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobVer extends RedstoneCmd {
    private static PlayerNameArgument PLAYER = new PlayerNameArgument("player", true);
    private JobsAddon addon;

    public JobVer(JobsAddon addon) {
        super("ver", "ve os jobs de um jogador", PLAYER);
        this.addon = addon;
        setCooldown(new Cooldown(5000));
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        User rgpl = RedstoneGangSpigot.getPlayer((Player) sender);
        if (args.isPresent(PLAYER)) {
            rgpl = args.get(PLAYER);
        }
        addon.openPlayer((Player) sender, rgpl);


    }
}
