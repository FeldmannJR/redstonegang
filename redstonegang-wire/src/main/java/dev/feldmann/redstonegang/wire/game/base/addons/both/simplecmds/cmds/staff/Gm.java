package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Gm extends SimpleCmd {
    public Gm() {
        super("gm", "alterna entre o gamemode criativo e survival", "gm");
    }

    @Override
    public void command(Player p, Arguments args) {
        p.setGameMode(p.getGameMode() == GameMode.CREATIVE ? GameMode.SURVIVAL : GameMode.CREATIVE);
        C.info(p, "Agora seu gamemode é %%.", p.getGameMode().name().toLowerCase());
    }
}
