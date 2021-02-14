package dev.feldmann.redstonegang.wire.modulos.language;

import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdNome extends RedstoneCmd {
    public CmdNome() {
        super("testnome", "testa o nome do item na tradução");
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        ItemStack it = ((Player) sender).getItemInHand();
        if (it == null) {
            return;
        }
        sender.sendMessage(LanguageHelper.getItemName(it));
    }
}
