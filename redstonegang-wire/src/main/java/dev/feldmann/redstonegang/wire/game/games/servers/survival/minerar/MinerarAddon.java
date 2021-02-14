package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.InvSync;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSaveLocationEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator.ElevatorBlock;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSyncLocationEvent;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.material.Torch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MinerarAddon extends Addon {

    HashMap<Integer, MinerarData> players = new HashMap<>();

    private List<Integer> taken = new ArrayList<>();


    int size = 9;
    int height = 5;
    private String worldName;

    public MinerarAddon(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public void onEnable() {
        //azar
        a(InvSync.class).setOnDisableHook(this::saveData);
        registerListener(new MinerarProtectionListener(this));
    }

    @EventHandler
    public void join(PlayerSyncLocationEvent ev) {
        MinerarData data = getData(ev.getPlayer());
        if (ev.isOverrideTeleportLocation()) return;

        if (ev.getTeleportLocation() != null && ev.getTeleportLocation().isCurrentServer()) {
            ev.setSpawnLocation(ev.getTeleportLocation().toLocation());
            return;
        }

        if (data.getLocation() == null) {
            data.setLocation(randomLocation());
            ev.setSpawnLocation(data.getLocation());
            buildShelter(ev.getPlayer(), data, data.getLocation().clone().add(-size / 2d, -1, -size / 2d));
        } else {
            if (data.getLastLoc() != null) {
                ev.setSpawnLocation(data.getLastLoc());
            } else {
                ev.setSpawnLocation(data.getLocation().clone().add(0, 2, 0));
            }
        }
    }


    @EventHandler
    public void saveData(PlayerSaveLocationEvent ev) {
        ev.setCancelled(true);
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        MinerarData data = getData(ev.getPlayer());
        if (data != null) {
            data.setLastLoc(ev.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent ev) {
        MinerarData data = getData(ev.getPlayer());
        if (data != null && data.getLocation() != null) {
            ev.setRespawnLocation(data.getLocation());
        }
    }

    public void buildShelter(Player p, MinerarData data, Location l) {
        Block bl = l.getBlock();
        Hitbox hitbox = new Hitbox(bl.getLocation().toVector(), bl.getLocation().toVector())
                .expand(size - 1, height + 1, size - 1)
                .expand(0, -1, 0);
        data.setProtectedArea(hitbox);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < size; z++) {
                    //Floor
                    Block b = bl.getRelative(x, y, z);
                    if (y == 0) {
                        b.setType(getFloorMaterial());
                        continue;
                    }
                    //Paredes
                    if (x == 0 || x == (size - 1) || z == 0 || z == (size - 1) || y == (height - 1)) {
                        b.setType(Material.COBBLESTONE);
                        continue;
                    }
                    //Deixar o oco
                    b.setType(Material.AIR);
                    b.getState().update();
                }

            }
        }

        //Botando escadas/elevador
        if (getUser(p).isVip()) {
            buildElevator(l, data);
        } else {
            buildStairs(l);
        }
        putWarning(l);
        //Construindo as entradas
        buildEntrance(l, BlockFace.NORTH);
        buildEntrance(l, BlockFace.SOUTH);
        buildEntrance(l, BlockFace.EAST);
        buildEntrance(l, BlockFace.WEST);


    }

    private void buildElevator(Location l, MinerarData data) {
        Block elv = l.clone().add(size - 2, 0, size - 2).getBlock();
        ElevatorBlock el = new ElevatorBlock();
        el.breakable = false;
        el.setRamOnly(true);
        el.infiniteRange = true;
        el.setTo(elv);
        a(CustomBlocksAddon.class).save(el);


        Block elv2 = elv.getWorld().getHighestBlockAt(elv.getX(), elv.getZ());
        el = new ElevatorBlock();
        el.setRamOnly(true);
        el.breakable = false;
        el.infiniteRange = true;
        el.setTo(elv2);
        elv.getRelative(0, 1, 0).setType(Material.AIR);
        elv.getRelative(0, 2, 0).setType(Material.AIR);
        data.setAnotherProtectedArea(new Hitbox(elv2.getLocation().toVector(), elv2.getLocation().toVector()).expand(1, 3, 1).expand(-1, -1, 0));
        a(CustomBlocksAddon.class).save(el);

    }

    private void buildStairs(Location l) {
        Block pedra1 = l.clone().add(size - 1, 1, size - 2).getBlock();
        Block escada1 = l.clone().add(size - 2, 1, size - 2).getBlock();

        int y = pedra1.getWorld().getHighestBlockYAt(pedra1.getLocation());

        boolean canstop = false;
        while (!canstop) {

            if (pedra1.getY() >= (y - 1)) {
                canstop = true;
            }
            if (!pedra1.getType().isSolid())
                pedra1.setType(Material.STONE);
            if (!canstop) {
                escada1.setType(Material.LADDER);
                escada1.setData((byte) 4);
            } else {
                escada1.setType(Material.TRAP_DOOR);
                escada1.setData((byte) 10);
                escada1.getState().update();
            }
            pedra1 = pedra1.getRelative(BlockFace.UP);
            escada1 = escada1.getRelative(BlockFace.UP);
        }
    }

    private void putWarning(Location l) {
        int metade = size / 2;
        l = l.clone().add(metade, 0, metade);
        Block b = l.getBlock();
        b.setType(Material.REDSTONE_BLOCK);
        b = b.getRelative(2, 1, 0);
        b.setType(Material.SIGN_POST);
        Sign s = (Sign) b.getState();
        s.setLine(0, "§4§l !! ALERTA !!");
        s.setLine(1, "Tudo construido");
        s.setLine(2, "neste mundo");
        s.setLine(3, "é perdido!");
        s.setRawData((byte) 4);
        s.update();
    }

    private void buildEntrance(Location l, BlockFace direction) {
        int metade = size / 2;
        l = l.clone().add(metade, 0, metade);
        Block b = l.getBlock();
        b.setType(Material.REDSTONE_BLOCK);
        b = b.getRelative(direction, metade);
        BlockFace add;
        switch (direction) {
            case NORTH:
                add = BlockFace.EAST;
                break;
            case SOUTH:
                add = BlockFace.WEST;
                break;
            case EAST:
                add = BlockFace.SOUTH;
                break;
            case WEST:
                add = BlockFace.NORTH;
                break;
            default:
                add = BlockFace.NORTH;
        }
        b = b.getRelative(add.getOppositeFace());
        int maxHeight = height - 1;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < maxHeight; y++) {
                Block tmp = b.getRelative(add.getModX() * x, y, add.getModZ() * x);
                if (y == 0 || y == maxHeight - 1) {
                    tmp.setType(Material.WOOD);
                    if (y != 0) {
                        if (x == 1) {
                            Block torch = tmp.getRelative(direction.getOppositeFace());
                            torch.getRelative(BlockFace.DOWN).setType(Material.STONE);
                            torch.setType(Material.TORCH);
                            Torch t = new Torch();
                            t.setFacingDirection(direction.getOppositeFace());
                            torch.setData(t.getData());
                            torch.getRelative(BlockFace.DOWN).setType(Material.AIR);

                        }
                    }
                    continue;
                }
                if (x == 0 || x == 2) {
                    tmp.setType(Material.FENCE);
                    continue;
                }
                tmp.setType(Material.AIR);

            }
        }


    }

    private Material getFloorMaterial() {
        switch (RandomUtils.randomInt(0, 2)) {
            case 0:
                return Material.WOOD;
            case 1:
                return Material.STONE;
            case 2:
                return Material.COBBLESTONE;
            default:
                return Material.STONE;
        }
    }


    public Location randomLocation() {
        World w = Bukkit.getWorld(worldName);
        while (true) {

            // Tamanho reservado pra casinha
            int gridSize = 50;
            // Distancia minima entre asc asinhas
            int slotSize = 100;


            int x = RandomUtils.randomInt(0, gridSize);
            int z = RandomUtils.randomInt(0, gridSize);

            int slot = (x * gridSize) + z;

            if (taken.contains(slot)) {
                continue;
            }
            int gridStart = -(gridSize * slotSize) / 2;

            int xLoc = gridStart + (x * slotSize);
            int zLoc = gridStart + (z * slotSize);

            int random = gridSize / 4;
            List<Biome> blockedBiomes = Arrays.asList(Biome.OCEAN, Biome.DEEP_OCEAN, Biome.FROZEN_OCEAN);
            if (blockedBiomes.contains(w.getBiome(xLoc, zLoc))) {
                continue;
            }

            Location loc = new Location(w, xLoc + RandomUtils.randomInt(-random, random), 15, zLoc + RandomUtils.randomInt(-random, random));
            taken.add(slot);
            Block block = w.getHighestBlockAt(loc).getRelative(0, -1, 0);
            if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
                continue;
            }


            return loc;

        }
    }


    public MinerarData getData(Player p) {
        int pId = getPlayerId(p);
        if (!players.containsKey(pId)) {
            players.put(pId, new MinerarData(pId));
        }
        return players.get(pId);
    }


}
