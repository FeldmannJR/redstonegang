package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.lenhador;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BreakBlockJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.BlockXpInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DigSpeedPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LenhadorJob extends Job implements BreakBlockJob {
    public LenhadorJob() {
        super("lenhador", "Lenhador", new ItemStack(Material.IRON_AXE));

        addPerk(new DigSpeedPerk(10, 20, "Pode quebrar + rapido", 10, 0));
        //Maderia dobrado
        addPerk(new DropDoubleLogPerk(20, 30));
        //Ingote de ferro
        addPerk(new DropOnBreakPerk(30, 40, "Pode dropar ferro", new ItemStack(Material.IRON_INGOT)));
        //Dropar a muda
        addPerk(new DropSaplingPerk(70, 20));
        //Pepita de ouro
        addPerk(new DropOnBreakPerk(30, 50, "Pode dropar pepitas de ouro", new ItemStack(Material.GOLD_NUGGET)));
        //Maça dourada
        addPerk(new DropOnBreakPerk(100, 50, "Pode dropar maça dourada", new ItemStack(Material.GOLDEN_APPLE)));
        addPerk(new DropOnBreakPerk(120, 500, "Pode dropar maça dourada", new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1)));


    }

    @Override
    public String getTitulo() {
        return "Lenhador";
    }

    @Override
    public String getDesc() {
        return "quebrar arvores";
    }


    @Override
    public BlockXpInfo[] getBlocksXps() {
        return new BlockXpInfo[]{
                new BlockXpInfo(3, Material.LOG_2),
                new BlockXpInfo(2, Material.LOG)

        };
    }

    @Override
    public boolean canGainXp(Player p, Block b) {
        return p.getItemInHand() != null && p.getItemInHand().getType().name().endsWith("_AXE");
    }

    @Override
    public int getMultiplicador() {
        return 60;
    }
}
