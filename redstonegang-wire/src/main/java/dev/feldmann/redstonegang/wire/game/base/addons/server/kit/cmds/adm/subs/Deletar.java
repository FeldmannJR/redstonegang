package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.Kit;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.KitArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.command.CommandSender;

public class Deletar extends RedstoneCmd {

    private static KitArgument KIT;

    KitsAddon manager;

    public Deletar(KitsAddon manager) {
        super("deletar", "deleta um kit", new KitArgument(manager, false));
        KIT = (KitArgument) getArgs().get(0);
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        Kit k = args.get(KIT);
        if (k.editing) {
            C.error(sender, "Alguem já está editando este kit!");
            return;
        }
        manager.deleteKit(k);
        C.info(sender, "Kit %% deletado!", k.getName());
    }
}
