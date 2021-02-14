package dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.events.PlayerDeleteLand;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import com.google.gson.JsonObject;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class CustomBlocksAddon extends Addon {

    CustomBlocksDB db;

    HashMap<BungeeBlock, BlockData> data = new HashMap<>();
    HashMap<String, Class<? extends BlockData>> types = new HashMap<>();

    String database, tablename;

    public CustomBlocksAddon(String database, String tablename) {
        this.database = database;
        this.tablename = tablename;
    }

    @Override
    public void onEnable() {
        db = new CustomBlocksDB(this, database, tablename);
        db.createTables();
    }

    @Override
    public void onStart() {
        data = db.loadBlocks();
    }

    public void save(BlockData b) {
        data.put(b.getLoc(), b);
        if (b.change && !b.ramOnly) {
            db.save(b);
            return;
        }

        b.change = false;
    }

    public void registerType(String name, Class<? extends BlockData> data) {
        types.put(name, data);
    }

    public BlockData create(BungeeBlock location, String type, String data) {
        if (types.containsKey(type)) {
            Class<? extends BlockData> classe = types.get(type);
            if (classe == null) return null;
            try {
                BlockData bdata = classe.newInstance();
                bdata.from(location);
                JsonObject obj = data == null || data.isEmpty() ? new JsonObject() : RGson.parse(data).getAsJsonObject();
                bdata.read(obj);
                return bdata;

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void remove(BlockData b) {
        if (data.containsKey(b.getLoc())) {
            data.remove(b.getLoc());
            db.delete(b);
        }
    }

    public void remove(BungeeBlock block) {
        if (data.containsKey(block)) {
            BlockData data = this.data.get(block);
            db.delete(data);
            this.data.remove(block);
        }
    }

    public BlockData get(Block b) {
        return data.get(BungeeBlock.fromBlock(b));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void breakCustom(BlockBreakEvent ev) {
        BlockData data = get(ev.getBlock());
        if (data != null) {
            data.breakBlock(this, ev);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void breakBlock(BlockBreakEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        checkForRemove(ev.getBlock());
    }

    public void interactPriority(PlayerInteractEvent ev, EventPriority priority) {
        if (ev.getClickedBlock() != null) {
            BlockData data = get(ev.getClickedBlock());
            if (data != null) {
                data.interactBlock(this, ev);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void highestInteract(PlayerInteractEvent ev) {
        interactPriority(ev, EventPriority.HIGH);
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void deleteTerreno(PlayerDeleteLand ev) {
        for (BungeeBlock key : new HashSet<>(data.keySet())) {
            if (ev.getTerreno().getRegion().isInside(key.getX(), key.getZ())) {
                remove(data.get(key));
            }
        }

    }

    public Collection<BlockData> getBlocks() {
        return data.values();
    }

    private void checkForRemove(Block b) {
        BlockData data = get(b);
        if (data != null) {
            remove(data);
        }
    }

    public BlockData get(BungeeBlock block) {
        if (data.containsKey(block)) {
            return data.get(block);
        }
        return null;
    }
}
