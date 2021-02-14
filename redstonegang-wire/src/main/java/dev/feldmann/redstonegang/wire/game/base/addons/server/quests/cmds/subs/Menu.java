package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus.ViewQuests;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Menu extends RedstoneCmd {
    QuestManager manager;

    public Menu(QuestManager manager) {
        super("menu", "abre o menu dos jogadores!");
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        new ViewQuests(manager.getPlayerId((Player) sender), manager).open((Player) sender);
    }
}
