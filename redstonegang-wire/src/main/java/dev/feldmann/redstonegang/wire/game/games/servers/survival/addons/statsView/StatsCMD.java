package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.statsView;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCMD extends RedstoneCmd {

    public static PlayerNameArgument PLAYER = new PlayerNameArgument("player", true);

    private StatsViewAddon addon;

    public StatsCMD(StatsViewAddon addon) {
        super("stats", "abre os status de um jogador", PLAYER);
        this.addon = addon;
        setCooldown(new Cooldown(1000));
        setPermission("rg.stats.cmd.view");
    }


    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Player player = (Player) sender;
        int id = getPlayerId(player);
        if (args.isPresent(PLAYER)) {
            id = args.get(PLAYER).getId();
        }

        addon.view(player, id);
    }
}
