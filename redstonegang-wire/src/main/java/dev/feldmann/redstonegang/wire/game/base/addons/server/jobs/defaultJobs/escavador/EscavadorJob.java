package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.escavador;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BlockXpInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BreakBlockJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DigSpeedPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EscavadorJob extends Job implements BreakBlockJob {
    public EscavadorJob() {
        super("escavador", "Escavador", new ItemStack(Material.IRON_SPADE));
        addPerk(new DigSpeedPerk(10, 30, "Pode cavar mais rapido", 10, 0));
        addPerk(new DropOnBreakPerk(20, 20, "Pode ganhar um bloco de tijolo", new ItemStack(Material.CLAY_BRICK)));
        addPerk(new DropOnBreakPerk(30, 30, "Pode ganhar um pó de glowstone", new ItemStack(Material.GLOWSTONE_DUST)));
        addPerk(new DropOnBreakPerk(50, 20, "Pode ganhar gramas", new ItemStack(Material.GRASS, 2)));
        addPerk(new DropOnBreakPerk(70, 150, "Pode ganhar um diamante", new ItemStack(Material.DIAMOND)));
        addPerk(new DropOnBreakPerk(90, 150, "Pode ganhar um pack do que quebrou", new ItemStack(Material.SAND, 64)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, Block ent) {
                Material m = ent.getType();
                return new ItemStack[]{new ItemStack(m, 64)};
            }
        });


    }

    @Override
    public String getTitulo() {
        return "Escavador";
    }

    @Override
    public String getDesc() {
        return "ganha xp ao quebrar terra/areia/cascalho";
    }

    @Override
    public BlockXpInfo[] getBlocksXps() {
        return new BlockXpInfo[]{
                new BlockXpInfo(1, Material.DIRT),
                new BlockXpInfo(1, Material.GRASS),
                new BlockXpInfo(2, Material.GRAVEL),
                new BlockXpInfo(1, Material.SAND),
                new BlockXpInfo(3, Material.CLAY),
                new BlockXpInfo(1, Material.HARD_CLAY)
        };
    }

    @Override
    public int getMultiplicador() {
        return 100;
    }
}
