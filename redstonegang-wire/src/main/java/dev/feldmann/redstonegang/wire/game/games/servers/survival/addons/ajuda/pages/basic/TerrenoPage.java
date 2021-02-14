package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TerrenoPage extends AjudaBookPage {

    public TerrenoPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor command = getCommandColor();
        return Arrays.asList(new PageBuilder()
                        .centerPage("§a§lTerrenos").newLine()
                        .addText(bg,
                                "Para proteger suas construções e baús "
                                        + "você precisa comprar um terreno, você precisará de ")
                        .addText(ChatColor.GOLD, "Moedas")
                        .setHoverBreak(ChatColor.GRAY, "Clique aqui para saber sobre como ganhar Moedas!")
                        .executeCommand(getAddon().getCommand(MoneyPage.class))
                        .addText(bg, ", e achar um lugar disponível. " +
                                "Após isto, basta executar o comando")
                        .newLine()
                        .addCommand(command, "terreno comprar <comprimento> <largura>")
                        .addText(bg, " onde você")
                        .create(),
                new PageBuilder()
                        .addText(bg, "irá substituir ")
                        .addText(bg, "<comprimento>")
                        .addText(bg, " e ")
                        .addText(bg, "<largura> ")
                        .addText(bg, " pelo tamanho que desejar, " +
                                "porém quanto maior, mais moedas irá custar. " +
                                "Após executar o comando irá ser mostrado temporariamente qual será o tamanho do terrenos, " +
                                "caso você queira confirmar o tamanho basta")
                        .create(),
                new PageBuilder()
                        .addText(bg, "executar o comando ").newLine()
                        .addCommand(command, "terreno confirmar")
                        .addText(bg, " ou clicar na mensagem no chat. " +
                                "Após comprar o terreno não esqueça de setar uma home, com o comando "
                        )
                        .addCommand(command, "sethome")
                        .addText(bg, " .Caso não consiga comprar o terreno, basta ler o chat para ver o que aconteceu!")
                        .create()
        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.FENCE)
                .name("Terrenos")
                .descBreak("Sistema para proteger suas construções e itens!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:terrenos";
    }
}
