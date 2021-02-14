package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MoneyPage extends AjudaBookPage {

    public MoneyPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor cmd = getCommandColor();
        return Arrays.asList(new PageBuilder()
                        .centerPage("§6§lMoedas").newLine()
                        .addText(bg, "Para ganhar moedas você precisa vender itens na loja localizada no spawn," +
                                " você pode vender minérios que conseguiu no ")
                        .addText(ChatColor.DARK_RED, "Minerar")
                        .setHoverBreak(ChatColor.WHITE, "Clique para ver sobre o mundo de mineração!")
                        .executeCommand(getAddon().getCommand(MinerarPage.class))
                        .addText(bg, ", blocos, plantações e etc!").newLine()
                        .addText("Você pode enviar dinheiro para outros jogadores com ")
                        .addCommand(cmd, "dinheiro dar")
                        .addText(bg, " !")
                        .create(),
                new PageBuilder()
                        .addText(bg, "Você pode ver pessoas mais ricas do servidor com o comando ")
                        .addCommand(cmd, "ricos")
                        .addText(bg, " !")
                        .create()
        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.DOUBLE_PLANT)
                .name("Moedas")
                .descBreak("Como conseguir e o que fazer com moedas!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:moedas";
    }
}
