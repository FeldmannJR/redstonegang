package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd;


import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.EnumArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestCriar extends QuestContador {


    public static EnumArgument<Bixo> BIXO = new EnumArgument<>("bixo", Bixo.class, false);

    Bixo bixo;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void build(BlockPlaceEvent ev) {
        if (ev.isCancelled()) return;
        QuestInfo fazendo = getFazendo(ev.getPlayer());
        if (fazendo != null) {
            switch (bixo) {
                case Iron_Golem: {
                    checkIronGolem(ev, fazendo);
                    break;
                }
                case Boneco_De_Neve: {
                    checkSnowMan(ev, fazendo);
                    break;
                }
                case Wither: {
                    checkWither(ev, fazendo);
                    break;
                }
            }
        }
    }

    public boolean isWitherHead(Block b) {
        if (b.getType() == Material.SKULL) {
            BlockState state = b.getState();
            if (state instanceof Skull) {
                return ((Skull) state).getSkullType() == SkullType.WITHER;
            }
        }
        return false;
    }

    public void checkSnowMan(BlockPlaceEvent ev, QuestInfo info) {
        Block b = ev.getBlock();
        if (b.getType() == Material.PUMPKIN || b.getType() == Material.JACK_O_LANTERN) {
            if (b.getRelative(0, -1, 0).getType() == Material.SNOW_BLOCK) {
                if (b.getRelative(0, -2, 0).getType() == Material.SNOW_BLOCK) {
                    info.faz();
                }
            }
        }
    }

    public void checkWither(BlockPlaceEvent ev, QuestInfo info) {

        if (isWitherHead(ev.getBlock())) {

            boolean oq = true;
            Block cabeca = null;
            List<Block> tocheck = new ArrayList();
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    tocheck.add(ev.getBlock().getRelative(x, 0, z));
                    tocheck.add(ev.getBlock().getRelative(x, 0, z));
                }
            }
            for (Block possivel : tocheck) {
                if (isWitherHead(possivel)) {
                    if (isWitherHead(possivel.getRelative(1, 0, 0)) && isWitherHead(possivel.getRelative(-1, 0, 0))) {
                        cabeca = possivel;
                        oq = true;
                    }
                    if (isWitherHead(possivel.getRelative(0, 0, -1)) && isWitherHead(possivel.getRelative(0, 0, 1))) {
                        cabeca = possivel;
                        oq = false;
                    }
                }
            }
            if (cabeca == null) return;
            BLOC:
            for (int[] check : new int[][]{{0, -1}, {0, -2}, {1, -1}, {-1, -1}}) {
                int x = check[0];
                int y = check[1];
                Material m1 = oq ? cabeca.getRelative(x, y, 0).getType() : cabeca.getRelative(0, y, x).getType();
                if (m1 != Material.SOUL_SAND) {
                    return;
                }
            }
            if (oq) {
                if (cabeca.getRelative(-1, -2, 0).getType() != Material.AIR || cabeca.getRelative(1, -2, 0).getType() != Material.AIR) {
                    return;
                }
            } else {
                if (cabeca.getRelative(0, -2, -1).getType() != Material.AIR || cabeca.getRelative(0, -2, 1).getType() != Material.AIR) {
                    return;
                }
            }
            info.faz();


        }
    }

    public void checkIronGolem(BlockPlaceEvent ev, QuestInfo info) {
        Material type = ev.getBlock().getType();
        if (type == Material.PUMPKIN || type == Material.JACK_O_LANTERN) {

            for (boolean b : new boolean[]{true, false}) {
                boolean todos = true;
                BLOC:
                for (int[] check : new int[][]{{0, -1}, {0, -2}, {1, -1}, {-1, -1}}) {
                    int x = check[0];
                    int y = check[1];
                    Material m1 = b ? ev.getBlock().getRelative(x, y, 0).getType() : ev.getBlock().getRelative(0, y, x).getType();
                    if (m1 != Material.IRON_BLOCK) {
                        todos = false;
                        break BLOC;
                    }
                }
                int x = 1;
                int z = 0;
                if (!b) {
                    x = 0;
                    z = 1;
                }
                if (ev.getBlock().getRelative(x * -1, -2, z * -1).getType() == Material.AIR && ev.getBlock().getRelative(x * 1, -2, z * 1).getType() == Material.AIR) {
                    if (todos) {
                        info.faz();
                        break;
                    }
                }
            }

        }
    }

    @Override
    public String toString() {
        return "Crie " + contador + " " + bixo.name();
    }

    @Override
    public boolean createQuestHook(Player p, Arguments args) {
        this.bixo = args.get(BIXO);
        this.contador = args.get(QUANTIDADE);
        return true;
    }

    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE, BIXO};
    }

    @Override
    public ItemStack toItemStack() {
        if (bixo == Bixo.Wither) {
            return new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
        }
        return new ItemStack(Material.PUMPKIN);
    }

    @Override
    public String suggestName() {
        return "Crie " + contador + " " + bixo.getNome();
    }


    public enum Bixo {

        Boneco_De_Neve,
        Iron_Golem,
        Wither;


        public void check(Block b) {
        }


        public String getNome() {
            return name().replace("_", " ");
        }
    }
}
