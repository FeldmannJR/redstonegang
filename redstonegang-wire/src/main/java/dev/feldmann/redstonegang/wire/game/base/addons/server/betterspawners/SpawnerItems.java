package dev.feldmann.redstonegang.wire.game.base.addons.server.betterspawners;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItemsAddon;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpawnerItems {

    private BetterSpawnersAddon addon;
    private static HashMap<EntityType, SpawnerItem> itens = new HashMap<>();

    public SpawnerItems(BetterSpawnersAddon addon) {
        this.addon = addon;
    }


    public void registerItens() {
        itens.clear();
        List<EntityType> entityTypes = Arrays.asList(
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.CREEPER,
                EntityType.SPIDER,
                EntityType.SKELETON,
                EntityType.CAVE_SPIDER,
                EntityType.ENDERMAN,
                EntityType.PIG_ZOMBIE,
                EntityType.SLIME,
                EntityType.GUARDIAN,
                EntityType.ENDERMITE,
                EntityType.BLAZE,
                EntityType.MAGMA_CUBE,
                EntityType.SILVERFISH,
                EntityType.GHAST,
                EntityType.WITCH,
                EntityType.IRON_GOLEM,
                // Passivos
                EntityType.PIG,
                EntityType.COW,
                EntityType.CHICKEN,
                EntityType.WOLF,
                EntityType.OCELOT,
                EntityType.SQUID,
                EntityType.RABBIT,
                EntityType.HORSE
        );
        for (EntityType type : entityTypes) {
            SpawnerItem item = new SpawnerItem(addon, type);
            addon.a(CustomItemsAddon.class).registerItem(item);
            itens.put(type, item);
        }
    }

    public static SpawnerItem getSpawner(EntityType type) {
        if (itens.containsKey(type)) {
            return itens.get(type);
        }
        return null;
    }

}
