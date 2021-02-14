package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Broadcast extends SimpleCmd {

    private static final RemainStringsArgument MENSAGEM = new RemainStringsArgument("mensagem", false);

    public Broadcast() {
        super("bcs", "Manda mensagem para todo o servidor", "broadcast", MENSAGEM);
    }

    @Override
    public void command(Player player, Arguments args) {
        getAddon().getServer().broadcast("§8[§4§lRedstoneGang§8] §f" + ChatColor.translateAlternateColorCodes('&', args.get(MENSAGEM)));
    }
}
