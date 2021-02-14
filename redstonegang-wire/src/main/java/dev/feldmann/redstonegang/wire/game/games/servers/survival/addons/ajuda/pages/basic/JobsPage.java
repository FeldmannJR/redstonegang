package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class JobsPage extends AjudaBookPage {

    public JobsPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor ex = ChatColor.BLUE;

        ChatColor cmd = getCommandColor();
        return Arrays.asList(new PageBuilder()
                        .centerPage("§2§lJobs/Trabalhos").newLine()
                        .addText(bg, "O servidor possuí trabalhos," +
                                " que são formas de ganhar alguns bonus" +
                                " em áreas do jogo, O servidor possuí varios" +
                                " trabalhos que podem ser vistos em ")
                        .addCommand(cmd, "jobs ver")
                        .addText(bg, " em cada trabalho você tem um level, e para upar" +
                                " é necessário fazer atividades")
                        .create(),
                new PageBuilder()
                        .addText(bg, "relacionadas a ele. Como exemplo minerar ao minerador.").newLine()
                        .addText(bg, "Jogadores com leveis altos em trabalhos ganham titulos!").newLine()
                        .addText(cmd, "Para mais informações use ")
                        .addCommand(cmd, "jobs")
                        .create()

        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.FISHING_ROD)
                .name("Jobs/Trabalhos")
                .descBreak("Como funciona o sistema de /jobs!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:jobs";
    }
}
