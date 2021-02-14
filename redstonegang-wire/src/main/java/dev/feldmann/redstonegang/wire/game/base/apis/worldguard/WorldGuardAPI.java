package dev.feldmann.redstonegang.wire.game.base.apis.worldguard;

import dev.feldmann.redstonegang.wire.game.base.objects.API;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import com.google.common.collect.Sets;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.RegionResultSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.util.RegionCollectionConsumer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Set;

public class WorldGuardAPI extends API {
    WorldGuardPlugin wgPlugin;


    @Override
    public void onEnable() {
        Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (pl != null && pl instanceof WorldGuardPlugin) {
            wgPlugin = (WorldGuardPlugin) pl;
        }
    }


    public WorldGuardPlugin getPlugin() {
        return wgPlugin;
    }

    @Override
    public void onDisable() {

    }

    public ApplicableRegionSet getApplicableRegions(World world, Hitbox2D hitbox) {
        RegionManager rm = getPlugin().getRegionManager(world);
        Set<ProtectedRegion> regions = Sets.newHashSet();
        RegionCollectionConsumer consumer = new RegionCollectionConsumer(regions, true);
        for (ProtectedRegion rg : rm.getRegions().values()) {
            int minX = rg.getMinimumPoint().getBlockX();
            int minY = rg.getMinimumPoint().getBlockZ();
            int maxX = rg.getMaximumPoint().getBlockX();
            int maxY = rg.getMaximumPoint().getBlockZ();
            Hitbox2D hit = new Hitbox2D(minX, minY, maxX, maxY);
            if (hitbox.collides(hit)) {
                consumer.apply(rg);
            }
        }
        return new RegionResultSet(regions, rm.getRegion("__global__"));
    }

    public boolean hasRegion(World w, Hitbox2D hitbox) {
        if (wgPlugin == null) return false;
        RegionManager rm = wgPlugin.getRegionManager(w);
        Collection<ProtectedRegion> pr = rm.getRegions().values();
        for (ProtectedRegion rg : pr) {
            int minX = rg.getMinimumPoint().getBlockX();
            int minY = rg.getMinimumPoint().getBlockZ();
            int maxX = rg.getMaximumPoint().getBlockX();
            int maxY = rg.getMaximumPoint().getBlockZ();
            Hitbox2D hit = new Hitbox2D(minX, minY, maxX, maxY);
            if (hitbox.collides(hit)) {
                return true;
            }
        }
        return false;
    }
}
