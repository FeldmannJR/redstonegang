package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestManager;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ViewQuests extends Menu {
    QuestManager manager;

    public ViewQuests(int playerId, QuestManager manager) {
        super("Missões", 1);
        this.manager = manager;
        int slot = 1;
        int dia = 1;
        List<QuestInfo> list = manager.getDaily().getCurrentStreak(playerId);
        for (QuestInfo q : list) {
            ItemStack it;
            if (q != null) {
                if (q.fail) {
                    it = new ItemStack(Material.PAPER);
                } else {
                    it = q.getQuest().getHook().toItemStack();
                    ItemMeta meta = it.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
                    it.setItemMeta(meta);
                    it.setAmount(1);
                }
                if (q.isCompleta()) {
                    it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                    ItemUtils.setItemName(it, "§e§lDia §f§l" + dia + "§7: §a§lMissão Concluida");
                    if (!q.fail) {
                        ItemUtils.addLore(it, "§b" + q.getQuest().nome);
                    }
                } else {
                    ItemUtils.setItemName(it, "§e§lDia §f§l" + dia + "§7: §e§lEm andamento!");
                    if (!q.fail) {
                        ItemUtils.addLore(it, "§b" + q.getQuest().nome);
                    }
                    ItemUtils.addLore(it, "§eProgresso: " + q.getStatus());
                    ItemUtils.addLore(it, " ");
                }

                if (q.isTodayDaily()) {
                    if (!q.isCompleta()) {
                        ItemUtils.addLore(it, "§r§lMissão de hoje!");
                    } else {
                        ItemUtils.addLore(it, "§a§lVolte amanhã para mais missões!");
                    }
                }

                ItemUtils.addLore(it, getRecompensa(dia));
            } else {
                it = ItemBuilder.item(Material.BEDROCK).name("Relogue para pegar nova missão!").build();
            }
            add(slot, new DummyButton(it));
            dia++;
            slot++;
        }
        for (int x = slot; x < 8; x++) {

            ItemStack it = ItemBuilder.item(Material.STAINED_GLASS_PANE).color(DyeColor.GRAY).name("§e§lDia §f§l" + dia + "").lore(getRecompensa(dia)).build();
            add(x, new DummyButton(it));
            dia++;
        }
    }

    public String getRecompensa(int dia) {
        return "§6§lRecompensa: §f§l" + manager.getRecompensa(dia) + " §9§lQuestPoints";
    }
}
