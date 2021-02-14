package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.subs.Criar;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.subs.Deletar;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.subs.SetCooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.subs.SetItens;
import org.bukkit.command.CommandSender;

public class CmdKitAdm extends RedstoneCmd {
    KitsAddon manager;

    public CmdKitAdm(KitsAddon manager) {
        super("kitadm", "comando para editar kits");
        this.manager = manager;
        setPermission("rg.kit.staff.cmd.kitadm");
        addCmd(new Criar(manager));
        addCmd(new SetItens(manager));
        addCmd(new Deletar(manager));
        addCmd(new SetCooldown(manager));
        addCmd(new HelpCmd());
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
