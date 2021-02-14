package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.alquimia;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBrewPotionPerk;
import dev.feldmann.redstonegang.wire.modulos.customevents.potion.PlayerBrewPotionEvent;
import dev.feldmann.redstonegang.wire.utils.items.PotionUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AlquimiaJob extends Job {
    public AlquimiaJob() {
        super("alquimia", "Alquimista", new ItemStack(Material.BREWING_STAND_ITEM));
        addPerk(new DropOnBrewPotionPerk(10, 30, "Pode ganhar pólvora, glowstone ou redstone", new ItemStack(Material.SULPHUR)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, PlayerBrewPotionEvent ent) {
                return new ItemStack[]{new ItemStack(RandomUtils.getRandom(Arrays.asList(Material.SULPHUR, Material.REDSTONE, Material.GLOWSTONE)))};
            }
        });
        addPerk(new DropOnBrewPotionPerk(20, 30, "Pode ganhar 3x frascos de de agua já fermentada", PotionUtils.buildAwkward(3)));
        addPerk(new DropOnBrewPotionPerk(30, 30, "Pode ganhar um reagente", new ItemStack(Material.RABBIT_FOOT)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, PlayerBrewPotionEvent ent) {
                return new ItemStack[]{new ItemStack(
                        RandomUtils.getRandom(
                                Arrays.asList(
                                        Material.SULPHUR,
                                        Material.REDSTONE,
                                        Material.GLOWSTONE,
                                        Material.SPECKLED_MELON,
                                        Material.GOLDEN_CARROT,
                                        Material.SPIDER_EYE,
                                        Material.BLAZE_POWDER,
                                        Material.GHAST_TEAR,
                                        Material.SUGAR,
                                        Material.RABBIT_FOOT)
                        )
                )};
            }
        });
        addPerk(new DropOnBrewPotionPerk(50, 75, "Pode ganhar um caldeirão!", new ItemStack(Material.CAULDRON_ITEM)));
        addPerk(new DropOnBrewPotionPerk(90, 100, "Pode ganhar um Suporte de Poções", new ItemStack(Material.BREWING_STAND_ITEM)));


    }

    @Override
    public String getTitulo() {
        return "Alquimista";
    }

    @Override
    public String getDesc() {
        return "ganha xp fazendo poções";
    }


    @EventHandler
    public void fazPocao(PlayerBrewPotionEvent ev) {
        Player p = ev.getPlayer();

        int xp = 0;
        for (PlayerBrewPotionEvent.PotionData d : ev.getPotions()) {
            for (PlayerBrewPotionEvent.PotionEffectU u : d.getNew()) {
                if (u.updatetype != null) {
                    xp += 1;
                }
            }
        }
        if (xp > 0) {
            addXp(p, xp);
            callActions(ev.getPlayer(), DropOnBrewPotionPerk.class, ev);
        }
    }

    @Override
    public int getMultiplicador() {
        return 50;
    }
}
