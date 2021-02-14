package dev.feldmann.redstonegang.wire.game.base.addons.server.betterspawners;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItemsAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MobSpawnerAbstract;
import net.minecraft.server.v1_8_R3.TileEntityMobSpawner;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import java.lang.reflect.Field;

@Dependencies(hard = {CustomItemsAddon.class, CustomBlocksAddon.class})
public class BetterSpawnersAddon extends Addon {

    public int maxLvl = 5;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;

    private SpawnerItems items;

    @Override
    public void onEnable() {
        a(CustomBlocksAddon.class).registerType("SPAWNER", SpawnerBlock.class);
        items = new SpawnerItems(this);
        items.registerItens();
        setMaxStackSize(Item.d("mob_spawner"), 1);
    }

    public void setMaxStackSize(Item item, int i) {
        try {

            Field field = Item.class.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(item, i);

        } catch (Exception e) {
        }
    }


    @EventHandler
    public void spawn(SpawnerSpawnEvent ev) {
        ev.getSpawner();
    }


    public void setLvl(CreatureSpawner spawner, int lvl) {
        if (lvl > maxLvl) return;
        if (lvl < 0) return;
        int max = 6;
        if (lvl > 1) {
            max = lvl * 4;
        }
        setMaxNearby(spawner, max);
        double melhoria = 1d - ((3D / 4D / 4D) * ((double) lvl - 1));
        setMinSpawnDelay(spawner, (int) ((double) minSpawnDelay * melhoria));
        setMaxSpawnDelay(spawner, (int) ((double) maxSpawnDelay * melhoria));
    }

    public void setMinSpawnDelay(CreatureSpawner spawner, int amount) {
        set(spawner, "minSpawnDelay", amount);
    }

    public void setMaxSpawnDelay(CreatureSpawner spawner, int amount) {
        set(spawner, "maxSpawnDelay", amount);
    }

    public void setMaxNearby(CreatureSpawner spawner, int amount) {
        set(spawner, "maxNearbyEntities", amount);
    }

    public void setSpawnCount(CreatureSpawner spawner, int amount) {
        set(spawner, "spawnCount", amount);
    }

    private void set(CreatureSpawner spawner, String field, int value) {
        CraftCreatureSpawner craft = (CraftCreatureSpawner) spawner;
        TileEntityMobSpawner tile = craft.getTileEntity();
        MobSpawnerAbstract sp = tile.getSpawner();
        try {

            Field f = MobSpawnerAbstract.class.getDeclaredField(field);
            f.setAccessible(true);
            f.set(sp, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
