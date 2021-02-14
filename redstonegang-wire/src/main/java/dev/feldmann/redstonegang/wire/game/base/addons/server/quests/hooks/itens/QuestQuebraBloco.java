package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.itens;

import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;

import dev.feldmann.redstonegang.wire.modulos.BlockUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QuestQuebraBloco extends QuestAlgoItem {


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void faz(BlockBreakEvent ev) {
        QuestInfo faz = getFazendo(ev.getPlayer());
        if (!isFarm(new MaterialData(ev.getBlock().getType()), true)) {
            if (BlockUtils.isPlayerPlaced(ev.getBlock())) return;
        }
        if (faz != null) {
            for (MaterialData m : md) {
                if (m.getItemType() == ev.getBlock().getType() && m.getData() == ev.getBlock().getData()) {
                    faz.faz();
                }
            }
        }

    }

    private static HashMap<Material, MaterialData> bugs = new HashMap();

    private static void initBugs() {
        bugs.put(Material.CROPS, new MaterialData(Material.WHEAT));
        bugs.put(Material.POTATO, new MaterialData(Material.POTATO_ITEM));
        bugs.put(Material.CARROT, new MaterialData(Material.CARROT_ITEM));
        bugs.put(Material.NETHER_WARTS, new MaterialData(Material.NETHER_STALK));
        bugs.put(Material.COCOA, new MaterialData(Material.INK_SACK, (byte) 3));


    }

    public static boolean isFarm(MaterialData md, boolean breakblock) {
        List<Material> farm = Arrays.asList(Material.NETHER_WARTS, Material.CARROT, Material.CROPS, Material.POTATO, Material.COCOA);
        if (farm.contains(md.getItemType())) {
            return true;
        } else {
            if (!breakblock) {
                farm = Arrays.asList(Material.PUMPKIN, Material.MELON);
                return farm.contains(md.getItemType());
            }
        }
        return false;
    }

    public ItemStack toItemStack() {
        if (bugs.isEmpty()) initBugs();
        if (md.length > 0) {
            if (isFarm(md[0], false)) {
                if (bugs.containsKey(md[0].getItemType())) {
                    return bugs.get(md[0].getItemType()).toItemStack(1);
                }
                return md[0].toItemStack(1);
            }
        }
        return new ItemStack(Material.GOLD_PICKAXE);

    }

    @Override
    public String getNome() {
        if (md.length > 0) {
            if (isFarm(md[0], true)) {
                return "Colha";
            }
        }
        return "Quebre";
    }


}
