package dev.feldmann.redstonegang.wire.game.base.addons.both.shopintegration.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class AtivarCodigo extends PlayerCmd {
    private static final StringArgument CODIGO = new StringArgument("Código", false);

    public AtivarCodigo() {
        super("ativar", "Ativa um código de vip ou cash", CODIGO);
        setAlias("codigo");
        setCooldown(new Cooldown(5000));
    }

    @Override
    public void command(Player player, Arguments args) {
        String codigo = args.get(CODIGO);
        boolean activated = RedstoneGang.instance().shop().tryToActivateCode(getUser(player), codigo);
        if (!activated) {
            C.error(player, "Código inválido!");
        }
    }
}
