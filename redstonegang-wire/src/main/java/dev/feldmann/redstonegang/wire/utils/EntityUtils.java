package dev.feldmann.redstonegang.wire.utils;

import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

public class EntityUtils {

    private static final List<EntityType> AGRESSIVE = Arrays.asList(
            EntityType.SKELETON,
            EntityType.ZOMBIE,
            EntityType.PIG_ZOMBIE,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.BLAZE,
            EntityType.CAVE_SPIDER,
            EntityType.SLIME,
            EntityType.ENDERMAN,
            EntityType.GHAST,
            EntityType.GUARDIAN,
            EntityType.MAGMA_CUBE,
            EntityType.SILVERFISH,
            EntityType.WITCH,
            EntityType.GIANT,
            EntityType.ENDERMITE
    );

    public static boolean isAgressive(EntityType type) {
        return AGRESSIVE.contains(type);
    }
}
