package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.shop.QuestShopAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;

import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends Menu {

    QuestManager manager;


    public MainMenu(int playerId, QuestManager manager) {
        super("Quests", 3);
        int tem = manager.getAddon().getCurrency().get(playerId);

        ItemStack missoes = ItemBuilder.item(Material.BOOK_AND_QUILL).name("§9§lMissões Diárias").lore("§fClique para ver as missões", "§fdiárias e suas recompensas!").build();
        ItemStack shop = ItemBuilder.item(Material.CHEST).name("§6§lLoja de QuestPoints").lore("§fClique para trocar seus pontos por", "§fitens da loja!", "", "§fPossuí: §b" + tem + " §9QuestPoints!").build();
        add(9 + 3, new Button(missoes) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new ViewQuests(manager.getPlayerId(player), manager).open(player);
            }
        });
        add(9 + 5, new Button(shop) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                manager.getAddon().a(QuestShopAddon.class).openPlayer(player);
            }
        });

    }
}
