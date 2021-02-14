package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.pescador;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnFishPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.JobPerk;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.redstonegang.event.PlayerCalculateFishTimeEvent;

import java.util.Arrays;

public class PescadorJob extends Job {

    public PescadorJob() {
        super("pescador", "Pescador", new ItemStack(Material.FISHING_ROD));
        addPerk(new DropOnFishPerk(10, 25, "Pode ganhar um peixe extra", new ItemStack(Material.RAW_FISH)) {
            @Override
            public ItemStack[]
            getDropItemStack(Player p, Item ent) {
                return new ItemStack[]{ent.getItemStack().clone()};
            }
        });
        addPerk(new DropOnFishPerk(20, 15, "Chance de achar um pão pescando", new ItemStack(Material.BREAD)));
        addPerk(new DropOnFishPerk(30, 30, "Pode ganhar um balde pescando", new ItemStack(Material.BUCKET)));
        addPerk(new DropOnFishPerk(50, 35, "Pode ganhar uma vara de pescar", new ItemStack(Material.FISHING_ROD)));
        addPerk(new DropOnFishPerk(70, 50, "Pode ganhar um livro de sorte", ItemUtils.getEnchantmentBook(Enchantment.LUCK, 1)));
        addPerk(new DropOnFishPerk(230, 70, "Pode ganhar parte de armadura com respiração II", new ItemStack(Material.IRON_HELMET)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, Item ent) {
                ItemStack it = new ItemStack(RandomUtils.getRandom(Arrays.asList(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS)));
                it.addUnsafeEnchantment(Enchantment.OXYGEN, 2);
                return new ItemStack[]{it};
            }
        });

        addPerk(new JobPerk(90, "Pesca mais rapido", 20) {
            @Override
            public ItemStack getItemStack() {
                return new ItemStack(Material.LEASH);
            }
        });
    }


    @EventHandler
    public void fishEvent(PlayerFishEvent ev) {
        if (ev.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            if (ev.getCaught() != null && ev.getCaught() instanceof Item) {
                int xp = 0;
                ItemStack it = ((Item) ev.getCaught()).getItemStack();
                if (it.getType() == Material.RAW_FISH) {
                    short da = it.getDurability();
                    switch (da) {
                        case 3:
                            xp = 3;
                            break;
                        case 2:
                            xp = 5;
                            break;
                        case 1:
                            xp = 2;
                            break;
                        default:
                            xp = 1;
                            break;
                    }
                }
                if (xp > 0) {
                    addXp(ev.getPlayer(), 1);
                    callActions(ev.getPlayer(), DropOnFishPerk.class, (Item) ev.getCaught());
                }
            }
        }
    }


    @EventHandler
    public void calculate(PlayerCalculateFishTimeEvent ev) {
        int lvl = getLevel(ev.getPlayer());
        if (lvl >= 90) {
            ev.setEnchantmentReduction(ev.getEnchantmentReduction() + 60);
            ev.setPreventCatchBug(true);
        }
    }

    @Override

    public String getTitulo() {
        return "Pescador";
    }

    @Override
    public String getDesc() {
        return "ganha xp pescando";
    }

    @Override
    public int getMultiplicador() {
        return 60;
    }
}
