package dev.feldmann.redstonegang.wire.game.base.addons.server.oreGenerator;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.stream.Collectors;

public class OreGenerator extends Addon {

    private boolean torch = true;

    private HashSet<Block> already = new HashSet<>();
    private HashSet<Block> deny = new HashSet<>();

    private List<OreGenChance> chances = new ArrayList<>();

    public static OreGenerator instance = null;

    private Cooldown mineCooldown = new Cooldown(500);

    @Override
    public void onEnable() {
        instance = this;

        chances.add(new OreGenChance(Material.COAL_ORE, 5, 52, 5, 12, 2));
        chances.add(new OreGenChance(Material.COAL_ORE, 53, 128, 5, 12, 0.6));

        chances.add(new OreGenChance(Material.IRON_ORE, 5, 67, 4, 10, 0.9));

        chances.add(new OreGenChance(Material.LAPIS_ORE, 14, 16, 3, 7, 0.15));
        chances.add(new OreGenChance(Material.LAPIS_ORE, 17, 33, 1, 5, 0.1));

        chances.add(new OreGenChance(Material.GOLD_ORE, 5, 33, 4, 8, 0.25));

        chances.add(new OreGenChance(Material.DIAMOND_ORE, 5, 15, 1, 5, 0.05));

        chances.add(new OreGenChance(Material.REDSTONE_ORE, 5, 15, 4, 8, 0.1));
        chances.add(new OreGenChance(Material.REDSTONE_ORE, 16, 30, 4, 8, 0.01));

        chances.add(new OreGenChance(Material.EMERALD_ORE, 5, 50, 1, 2, 0.03));

        chances.add(new OreGenChance(Material.QUARTZ_ORE, 5, 32, 2, 3, 0.1));


    }

    public boolean isHidden(Block b) {
        if (b.getY() == 0) {
            return false;
        }
        if (b.getType() != Material.STONE && b.getType() != Material.DIRT && b.getType() != Material.SAND && b.getType() != Material.SANDSTONE && b.getType() != Material.GRAVEL) {
            return false;
        }
        if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.NORTH).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.SOUTH).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.WEST).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.EAST).getType() == Material.AIR) {
            return false;
        }
        return b.getRelative(BlockFace.DOWN).getType() != Material.AIR;
    }

    BlockFace[] faces
            = {
            BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH
    };
    Random rnd = new Random();


    public OreGenChance getOre(int y, Player p) {
        List<OreGenChance> canB = chances.stream().filter((c) -> c.isIn(y)).collect(Collectors.toList());
        Collections.shuffle(canB);
        double chance = rnd.nextDouble() * 100;
        double comulative = 0;
        for (OreGenChance ch : canB) {
            comulative += ch.chance;
            if (chance < comulative)
                return ch;
        }

        return null;

    }


    public List<Block> getNearBlocks(Block b) {

        List<Block> near = new ArrayList<>();

        for (BlockFace face : faces) {
            near.add(b.getRelative(face));
        }
        Collections.shuffle(near);
        return near;
    }

    public void spawnOres(Block block, Player p, boolean addCd, boolean startInSame) {
        if (already.contains(block)) {
            return;
        }
        already.add(block);


        OreGenChance thisOre = this.getOre(block.getY(), p);
        if (thisOre != null) {
            Block b = block;
            if (!startInSame) {
                for (Block bt : getNearBlocks(block)) {
                    if (isHidden(bt)) {
                        b = bt;
                        break;
                    }
                }
            }
            if (b == null) {
                return;
            }

            b.setType(thisOre.data.getItemType());

            if (addCd)
                mineCooldown.addCooldown(p.getUniqueId());


            int size = thisOre.getVeinSize() - 1;
            geraVeioDeMinerio(b, thisOre.data, size, p);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void breakBlock(BlockBreakEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (ev.getBlock().getType() == Material.STONE) {

            if (mineCooldown.isCooldown(ev.getPlayer().getUniqueId())) {
                return;
            }
            spawnOres(ev.getBlock(), ev.getPlayer(), true, false);
        }
        for (Block b : getNearBlocks(ev.getBlock())) {
            if (b.getType() == Material.STONE) {
                deny.add(b);
            }
        }
    }


    public void vasculhaAr(Block b, Player p, int depth, List<Block> vasculhados) {
        if (depth == 0) return;
        for (Block bl : getNearBlocks(b)) {
            if (vasculhados.contains(bl) || deny.contains(bl)) {
                continue;
            }
            if (bl.getType() == Material.AIR) {
                vasculhaAr(bl, p, depth - 1, vasculhados);
            }
            if (bl.getType() == Material.STONE) {
                spawnOres(bl, p, false, true);
            }
            vasculhados.add(bl);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void fire(BlockPlaceEvent ev) {
        if (torch)
            if (ev.getBlock().getType() == Material.TORCH) {
                long nano = System.nanoTime();
                vasculhaAr(ev.getBlockAgainst(), ev.getPlayer(), 30, new ArrayList<>());
                //ev.getUser().sendMessage(System.nanoTime() - nano + "ns");
            }
    }


    public void geraVeioDeMinerio(Block b, MaterialData ore, int count, Player p) {
        List<Block> near = getNearBlocks(b);
        for (Block block : near) {
            if (isHidden(block)) {
                block.setType(ore.getItemType());

                block.getState().update();
                count--;
                if (count > 0) {
                    geraVeioDeMinerio(block, ore, count, p);
                }
                break;
            }
        }
    }

}
