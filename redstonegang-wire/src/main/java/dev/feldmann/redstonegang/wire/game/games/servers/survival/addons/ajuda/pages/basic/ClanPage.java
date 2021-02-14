package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ClanPage extends AjudaBookPage {

    public ClanPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor cmd = getCommandColor();
        return Arrays.asList(new PageBuilder()
                .centerPage("§4§lClan").newLine()
                .addText("Clan é uma comunidade de jogadores que podem interagir" +
                        " entre eles em um chat exclusivo, entre outras coisas." +
                        " Para obter um clan é preciso ter 1.5k em moedas, use ")
                .addCommand(cmd, "clan criar <nome> <tag>")
                .addText(bg, ". Em caso de dúvidas use ")
                .addCommand(cmd, "clan")
                .addText(bg, " e veja os outros comandos!")
                .create()
        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.BEACON)
                .name("Clan")
                .descBreak("Como utilizar o sistema de clans!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:clan";
    }
}
