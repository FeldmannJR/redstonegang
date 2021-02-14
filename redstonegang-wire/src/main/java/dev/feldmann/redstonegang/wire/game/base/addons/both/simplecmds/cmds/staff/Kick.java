package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.PlayerNameArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Kick extends SimpleCmd {


    private static final PlayerNameArgument PLAYER = new PlayerNameArgument("player", false);
    private static final RemainStringsArgument REASON = new RemainStringsArgument("motivo", false);

    public Kick() {
        super("kick", "teleporta para um jogador", "tp", PLAYER, REASON);
    }

    @Override
    public void command(Player p, Arguments a) {
        User rg = a.get(PLAYER);
        if (!rg.isOnline()) {
            C.error(p, "Jogador não está online!");
            return;
        }
        String reason = a.get(REASON);
        if (reason == null) {
            reason = "Connection Lost.";
        }
        rg.kick(new TextComponent(TextComponent.fromLegacyText(reason)));
        C.info(p, "Kickado %% !", rg);
    }

    @Override
    public boolean canOverride() {
        return true;
    }
}
