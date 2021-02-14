package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.cmds.subs;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.*;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.Hooks;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.Quest;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestHook;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CriarHook extends RedstoneCmd {

    QuestManager manager;
    Hooks hook;

    public CriarHook(Hooks hook, QuestManager manager) {
        super(hook.name().toLowerCase(), "cria uma quest de " + hook.name(), hook.createHook().getArgs());
        this.hook = hook;
        this.manager = manager;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        QuestHook qh = hook.create((Player) sender, args);
        if (qh == null) {
            C.error(sender, "WHOOOPS!");
            return;
        }
        qh.setManager(manager);
        Quest q = new Quest();
        q.nome = qh.suggestName();
        q.setHook(qh);
        q.setManager(manager);
        q.desc = null;
        manager.addQuest(q);
        C.info(sender, "Quest %% criada com sucesso", q.nome);

    }
}
