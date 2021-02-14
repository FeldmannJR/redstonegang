package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MinerarPage extends AjudaBookPage {

    public MinerarPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor ex = ChatColor.BLUE;

        ChatColor cmd = getCommandColor();
        return Arrays.asList(new PageBuilder()
                .centerPage("§8§lMinerar").newLine()
                .addText(bg, "O minerar é um mundo onde você pode obter itens," +
                        " como madeira, minérios, entre outros recursos que não é" +
                        " possível pegar no mundo 'normal'(terrenos). Para ir até esse mundo use ")
                .addCommand(cmd, "minerar").newLine()
                .addText(ex, " Cuidado este mundo tem o PvP Ligado!")
                .create()

        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.DIAMOND_PICKAXE)
                .name("Minerar")
                .descBreak("Como pegar recursos!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:minerar";
    }
}
