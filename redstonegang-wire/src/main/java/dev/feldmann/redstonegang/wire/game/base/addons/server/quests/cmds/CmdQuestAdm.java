package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;

import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs.Criar;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs.List;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs.Menu;
import org.bukkit.command.CommandSender;


public class CmdQuestAdm extends RedstoneCmd {

    public CmdQuestAdm(QuestManager manager) {
        super("questadm", "comandos para editar as quests diarias");
        addCmd(new Criar(manager));
        addCmd(new List(manager));
        addCmd(new Menu(manager));
        addCmd(new HelpCmd());
        setPermission("rg.dailyquests.staff.cmd.questadm");

    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
