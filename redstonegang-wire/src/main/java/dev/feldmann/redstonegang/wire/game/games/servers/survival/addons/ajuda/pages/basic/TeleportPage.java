package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TeleportPage extends AjudaBookPage {

    public TeleportPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor cmd = getCommandColor();
        return Arrays.asList(new PageBuilder()
                        .centerPage("§5§lTeleportes").newLine()
                        .addText(bg, "Use o comando ")
                        .addCommand(cmd, "tp")
                        .addText(bg, " para se teletransportar para outro jogador!").newLine()
                        .addText(bg, "Quando você receber um pedido de teleporte você pode aceitar o teleporte com o comando ")
                        .addCommand(cmd, "tpa")
                        .addText(bg, " ou você pode recusar com ")
                        .addCommand(cmd, "tpr")
                        .addText(bg, " !").newLine().create(),
                new PageBuilder()
                        .addText(bg, "Você também pode teleportar para outras localizações usando ")
                        .addCommand(cmd, "warp")
                        .addText(bg, " !")
                        .create()
        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.ENDER_PEARL)
                .name("Teleportes")
                .descBreak("Como usar os teleportes do servidor!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:teleport";
    }
}
