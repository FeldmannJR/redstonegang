package dev.feldmann.redstonegang.wire.modulos.customevents.potion;


import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class PlayerBrewManager extends Modulo implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void click(InventoryClickEvent ev) {
        if (ev.getInventory().getType() != InventoryType.BREWING) {
            return;
        }
        if (!(ev.getInventory() instanceof BrewerInventory)) {
            return;
        }
        if (ev.isCancelled() || ev.getResult() == Event.Result.DENY) return;
        BrewerInventory b = (BrewerInventory) ev.getInventory();
        if (b.getHolder() == null) return;
        if (b.getHolder().getLocation() == null) return;
        Player p = (Player) ev.getWhoClicked();
        if (ev.getSlotType() == InventoryType.SlotType.FUEL) {
            if (!ev.isShiftClick() && ev.getCursor() != null && ev.getCursor().getType() != Material.AIR) {
                //    p.sendMessage("Adicionado por click normal");
                adicionou(p, b);
                return;
            }
        }
        if (ev.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                if (ev.getCurrentItem().getType() != Material.POTION) {
                    if (b.getItem(3) == null || b.getItem(3).getType() == Material.AIR) {
                        //    p.sendMessage("Adicionado por move");

                        adicionou(p, b);
                        return;
                    }


                }
            }

            return;
        }


    }

    HashMap<Vector, UUID> pls = new HashMap<Vector, UUID>();

    public void adicionou(Player p, BrewerInventory i) {
        pls.put(i.getHolder().getLocation().toVector(), p.getUniqueId());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void openInventory(InventoryOpenEvent ev) {
        if (ev.getInventory() instanceof BrewerInventory) {
            BrewerInventory i = (BrewerInventory) ev.getInventory();
            if (i.getHolder() != null) {
                if (i.getHolder().getLocation() != null) {
                    if (!pls.containsKey(i.getHolder().getLocation().toVector())) {
                        if (i.getIngredient() != null && i.getIngredient().getType() != Material.AIR) {
                            adicionou((Player) ev.getPlayer(), i);
                        }
                    }
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void brew(BrewEvent ev) {

        BrewerInventory i = ev.getContents();
        if (i.getHolder() != null) {
            Vector v = i.getHolder().getLocation().toVector();
            if (!pls.containsKey(v)) {
                return;
            }
            final Player p = Bukkit.getPlayer(pls.get(v));
            if (p == null || !p.isOnline()) return;

            final PlayerBrewPotionEvent.PotionData[] d = new PlayerBrewPotionEvent.PotionData[3];
            for (int x = 0; x < 3; x++) {

                ItemStack now = null;
                if (i.getItem(x) != null) {
                    now = i.getItem(x).clone();
                }
                d[x] = new PlayerBrewPotionEvent.PotionData();
                d[x].before = now;
                //    PlayerBrewPotionEvent.PotionData.debug(p,now);
            }
            final ItemStack added = i.getIngredient();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Wire.instance, new Runnable() {
                @Override
                public void run() {
                    for (int x = 0; x < 3; x++) {
                        ItemStack after = null;
                        if (i.getItem(x) != null) {
                            after = i.getItem(x).clone();
                        }
                        d[x].after = after;
                    }
                    PlayerBrewPotionEvent pb = new PlayerBrewPotionEvent(ev.getBlock(), p, added, d);
                    Bukkit.getPluginManager().callEvent(pb);
                }
            });


        }

    }


    @Override
    public void onEnable() {
        register(this);
    }

    @Override
    public void onDisable() {

    }
}
