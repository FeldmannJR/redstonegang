package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus.ListQuestAdmMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List extends RedstoneCmd {
    QuestManager manager;

    public List(QuestManager manager) {
        super("list", "abre um menu com todas as quests para editar!");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new ListQuestAdmMenu(manager).open((Player) sender);
    }
}
