package dev.feldmann.redstonegang.wire.game.base.addons.world;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.MetaUtils;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldControlAddon extends Addon {

    private List<CreatureSpawnEvent.SpawnReason> pode;
    private boolean fogo = false;
    private boolean explodeBlocks = false;
    private boolean leavesDecay = false;

    public WorldControlAddon() {
        pode = new ArrayList<>();
    }

    @Override
    public void onEnable() {
        pode.add(CreatureSpawnEvent.SpawnReason.EGG);
        pode.add(CreatureSpawnEvent.SpawnReason.CUSTOM);
        pode.add(CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM);
        pode.add(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN);
        pode.add(CreatureSpawnEvent.SpawnReason.SPAWNER);
        pode.add(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
        pode.add(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG);
        pode.add(CreatureSpawnEvent.SpawnReason.LIGHTNING);
        pode.add(CreatureSpawnEvent.SpawnReason.SILVERFISH_BLOCK);
        pode.add(CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
    }

    public void allowNaturalSpawn() {
        pode.add(CreatureSpawnEvent.SpawnReason.NATURAL);
        pode.add(CreatureSpawnEvent.SpawnReason.BREEDING);
        pode.add(CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
        pode.add(CreatureSpawnEvent.SpawnReason.CURED);
        pode.add(CreatureSpawnEvent.SpawnReason.DEFAULT);
        pode.add(CreatureSpawnEvent.SpawnReason.JOCKEY);
        pode.add(CreatureSpawnEvent.SpawnReason.INFECTION);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void bixoSpawna(CreatureSpawnEvent ev) {
        if (!pode.contains(ev.getSpawnReason())) {
            ev.setCancelled(true);
        } else {
            if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
                int contador = 0;
                for (Entity e : ev.getEntity().getNearbyEntities(10, 10, 10)) {
                    if (e.getType() == ev.getEntity().getType()) {
                        contador++;
                    }
                }
                if (contador >= 8) {
                    ev.setCancelled(true);
                    ev.getLocation().getWorld().dropItemNaturally(ev.getLocation(), ItemBuilder.item(Material.INK_SACK).color(DyeColor.WHITE).name("§f§oEcaaaa!").build());
                } else if (ev.getEntity().getType() == EntityType.SHEEP) {
                    if (RandomUtils.oneIn(10)) {
                        Sheep sheep = (Sheep) ev.getEntity();
                        sheep.setColor(DyeColor.values()[new Random().nextInt(DyeColor.values().length)]);
                    }
                }

            }
            MetaUtils.set(ev.getEntity(), "spawncause", ev.getSpawnReason());
        }
    }


    public void limpaMobs() {
        for (World w : Bukkit.getWorlds()) {
            for (LivingEntity e : w.getLivingEntities()) {
                if (e instanceof Creature || getSpawnReason(e) == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                    e.remove();
                }
            }
        }
    }

    public CreatureSpawnEvent.SpawnReason getSpawnReason(Entity e) {
        return (CreatureSpawnEvent.SpawnReason) MetaUtils.get(e, "spawncause");
    }


    public void setFogo(boolean fogo) {
        this.fogo = fogo;
    }

    @EventHandler
    public void fireDamageControl2(BlockSpreadEvent event) {//prevents fire from spreading
        if (event.getNewState().getType() == Material.FIRE) {
            if (!fogo) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ignite(BlockIgniteEvent ev) {
        if (ev.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            if (!fogo) {
                ev.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void blockburn(BlockBurnEvent ev) {
        if (!fogo) {
            ev.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlocoTrocaFisica(BlockFadeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPhysics(BlockFormEvent event) {
        if (event.getNewState().getType() == Material.ICE || event.getNewState().getType() == Material.SNOW) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void weatherchange(WeatherChangeEvent ev) {
        if (ev.toWeatherState()) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void ignitefire(BlockIgniteEvent e) {
        if (fogo) {
            return;
        }
        if (!e.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            e.setCancelled(true);
        }
    }

    public void setExplodeBlocks(boolean explodeBlocks) {
        this.explodeBlocks = explodeBlocks;
    }

    public boolean isExplodeBlocks() {
        return explodeBlocks;
    }


    public void setLeavesDecay(boolean leavesDecay) {
        this.leavesDecay = leavesDecay;
    }

    public boolean isLeavesDecay() {
        return leavesDecay;
    }

    @EventHandler
    public void FolhasSomem(LeavesDecayEvent ev) {
        if (leavesDecay)
            ev.setCancelled(true);
    }


}
