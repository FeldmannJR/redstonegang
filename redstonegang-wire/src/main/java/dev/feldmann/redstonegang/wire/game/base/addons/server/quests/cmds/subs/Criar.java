package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.HelpCmd;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.Hooks;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import org.bukkit.command.CommandSender;

public class Criar extends RedstoneCmd {

    public Criar(QuestManager manager) {
        super("criar", "cria uma quest");
        for (Hooks h : Hooks.values()) {
            addCmd(new CriarHook(h, manager));
        }
        addCmd(new HelpCmd());

    }

    @Override
    public void command(CommandSender sender, Arguments args) {

    }
}
