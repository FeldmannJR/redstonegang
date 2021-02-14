package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.subs.everybody;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.ClanSubCmd;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanListar extends ClanSubCmd {

    public static IntegerArgument PAGE = new IntegerArgument("pagina", true, 1, 1, Integer.MAX_VALUE);

    public ClanListar(ClanAddon addon) {
        super(addon, "listar", "lista todos os clans do servidor", PAGE);
        setCooldown(new Cooldown(1500));
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        getAddon().getCache().getDb().list((Player) sender, args.get(PAGE));
    }
}
