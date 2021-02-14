package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.Quest;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ListQuestAdmMenu extends MultiplePageMenu<Quest> {

    QuestManager manager;

    public ListQuestAdmMenu(QuestManager manager) {
        super("Editando missões");
        this.manager = manager;

    }

    @Override
    public Button getButton(Quest q, int page) {
        ItemStack it = q.getHook().toItemStack();
        ItemMeta meta = it.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
        it.setItemMeta(meta);
        it.setAmount(1);
        ItemUtils.setItemName(it, "§e" + q.getId() + " §f- §a" + q.nome);
        ItemUtils.addLore(it, "§7" + q.getHook().toString());
        ItemUtils.addLore(it, "§aClique para editar!");
        return new Button(it) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new EditQuestMenu(q).open(player);
            }
        };

    }

    @Override
    public List<Quest> getAll() {
        return new ArrayList<>(manager.getQuests());
    }


}
