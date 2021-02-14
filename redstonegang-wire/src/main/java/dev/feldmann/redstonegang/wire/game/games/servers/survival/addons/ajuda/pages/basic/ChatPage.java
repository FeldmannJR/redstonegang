package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.basic;

import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.pages.AjudaBookPage;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.book.PageBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ChatPage extends AjudaBookPage {

    public ChatPage(int slot) {
        super(slot);
    }

    @Override
    public List<String> generate() {
        ChatColor bg = getBackgroundColor();
        ChatColor ex = ChatColor.BLUE;

        ChatColor cmd = getCommandColor();
        return Arrays.asList(new PageBuilder()
                        .centerPage("§1§lChat").newLine()
                        .addText("§2Global:").newLine()
                        .addText(bg, "Para falar no chat global, use ![mensagem] ")
                        .addText(ex, "Ex: !Oi").newLine()
                        .addText("§5Ajuda:").newLine()
                        .addText(bg, "Para falar no chat ajuda, use /? <mensagem> ")
                        .addText(ex, "Ex: /? Oi").newLine()
                        .addText("§cClan:")
                        .setHoverBreak(ChatColor.WHITE, "Clique aqui para ver a ajuda dos clans!")
                        .executeCommand(getAddon().getCommand(ClanPage.class))
                        .newLine()
                        .addText(bg, "Para falar no chat do seu clan use, use .[mensagem] ")
                        .addText(ex, "Ex: .Oi").newLine()
                        .create(),
                new PageBuilder()
                        .addText("§dPrivado:").newLine()
                        .addText(bg, "Para falar no privado com algum jogador use , use ")
                        .addCommand(cmd, "msg <jogador> [mensagem]")
                        .addText(bg, " , você pode responder a ultima mensagem recebida com ")
                        .addCommand(cmd, "r <mensagem>").newLine()
                        .addText(ex, "Ex: /msg Notch Oi")
                        .newLine()
                        .addText("§6Local:").newLine()
                        .addText(bg, "Para falar com os jogadores próximos, use :[mensagem] ")
                        .addText(ex, "Ex: :Oi").create(),
                new PageBuilder()
                        .centerPage("§4Atenção").newLine()
                        .addText(bg, "Você pode entrar em canais, isso significa")

                        .addText(bg, "que não vai mais precisar usar o simbolo na frente. Basta usar somente o simbolo do canal").newLine()
                        .addText(ex, "Ex: . ")
                        .addText(bg, "Irá entrar no canal do clan, você também pode usar isto com /msg")
                        .create()

        );
    }

    @Override
    public ItemStack generateIconItemstack() {
        return CItemBuilder.item(Material.SIGN)
                .name("Chat")
                .descBreak("Como utilizar o chat e seus canais!")
                .build();
    }

    @Override
    public String getCommandSlug() {
        return "basic:chat";
    }
}
