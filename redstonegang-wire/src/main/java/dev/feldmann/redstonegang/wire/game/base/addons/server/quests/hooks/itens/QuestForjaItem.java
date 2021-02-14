package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class QuestForjaItem extends QuestAlgoItem {


    @EventHandler
    public void extract(FurnaceExtractEvent ev) {
        if (ev.getItemAmount() == 0) return;
        QuestInfo faz = getFazendo(ev.getPlayer());
        if (faz == null) return;
        Player p = ev.getPlayer();
        for (MaterialData m : md) {
            if (m.getItemType() == ev.getItemType()) {
                faz.faz(ev.getItemAmount());
            }
        }


    }

    @EventHandler(priority = EventPriority.LOW)
    public void onShiftCrafting(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.RESULT

                && event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if ((event.getCursor() != null && event.getCursor().getType() != Material.AIR) ||
                    event.getClick().equals(ClickType.SHIFT_LEFT)) {

                QuestInfo faz = getFazendo((Player) event.getWhoClicked());
                if (faz == null) return;
                for (MaterialData m : md) {
                    if (event.getCurrentItem() != null && md != null)
                        if (event.getCurrentItem().getType() == m.getItemType() && event.getCurrentItem().getData().getData() == m.getData()) {
                            event.setCancelled(true);
                            event.setResult(Event.Result.DENY);
                            return;
                        }
                }

            }
        }
    }

    public ItemStack toItemStack() {

        return new ItemStack(Material.FURNACE);

    }

    @Override
    public String getNome() {
        return "Forge/Cozinhe";
    }


}
