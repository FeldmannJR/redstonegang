package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SpawnEggs {

    private static HashMap<EntityType, Short> tipos = new HashMap();
    private static HashMap<EntityType, Material> custom = new HashMap();

    private static void init() {
        if (tipos.isEmpty()) {
            tipos.put(EntityType.CREEPER, (short) 50);
            tipos.put(EntityType.SKELETON, (short) 51);
            tipos.put(EntityType.SPIDER, (short) 52);
            tipos.put(EntityType.ZOMBIE, (short) 54);
            tipos.put(EntityType.SLIME, (short) 55);
            tipos.put(EntityType.GHAST, (short) 56);
            tipos.put(EntityType.PIG_ZOMBIE, (short) 57);
            tipos.put(EntityType.ENDERMAN, (short) 58);
            tipos.put(EntityType.CAVE_SPIDER, (short) 59);
            tipos.put(EntityType.SILVERFISH, (short) 60);
            tipos.put(EntityType.BLAZE, (short) 61);
            tipos.put(EntityType.MAGMA_CUBE, (short) 62);
            tipos.put(EntityType.WITCH, (short) 66);
            tipos.put(EntityType.ENDERMITE, (short) 67);
            tipos.put(EntityType.GUARDIAN, (short) 68);
            tipos.put(EntityType.PIG, (short) 90);
            tipos.put(EntityType.SHEEP, (short) 91);
            tipos.put(EntityType.COW, (short) 92);
            tipos.put(EntityType.CHICKEN, (short) 93);
            tipos.put(EntityType.SQUID, (short) 94);
            tipos.put(EntityType.WOLF, (short) 95);
            tipos.put(EntityType.MUSHROOM_COW, (short) 96);
            custom.put(EntityType.SNOWMAN, Material.SNOW_BLOCK);
            tipos.put(EntityType.OCELOT, (short) 98);
            custom.put(EntityType.IRON_GOLEM, Material.IRON_BLOCK);
            tipos.put(EntityType.HORSE, (short) 100);
            tipos.put(EntityType.RABBIT, (short) 101);
            tipos.put(EntityType.VILLAGER, (short) 120);


        }
    }


    public static ItemStack getItemStack(EntityType ent) {
        init();
        if (tipos.containsKey(ent)) {
            ItemStack it = new ItemStack(Material.MONSTER_EGG, 1, tipos.get(ent));
            return it;
        }
        if (custom.containsKey(ent)) {
            return new ItemStack(custom.get(ent));
        }
        return new ItemStack(Material.MONSTER_EGG);
    }


}
