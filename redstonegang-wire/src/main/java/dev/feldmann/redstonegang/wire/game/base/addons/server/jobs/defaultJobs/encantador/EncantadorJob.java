package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.encantador;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DisplayJobPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnEnchantPerk;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class EncantadorJob extends Job {

    private DisplayJobPerk UM_LEVEL_RANDOM = new DisplayJobPerk(10, 35, "Pode gastar 1 nivel a menos para encantar", new ItemStack(Material.EXP_BOTTLE, 1));
    private DisplayJobPerk UM_LEVEL = new DisplayJobPerk(50, 35, "Gasta 1 nivel a menos para encantar", new ItemStack(Material.ENCHANTED_BOOK, 1));


    public EncantadorJob() {
        super("encantador", "Encantador", new ItemStack(Material.ENCHANTMENT_TABLE));
        addPerk(UM_LEVEL_RANDOM);
        addPerk(UM_LEVEL);
        addPerk(new DropOnEnchantPerk(30, 35, "Pode ganhar um Lápis-Lazúli ao encantar", ItemBuilder.item(Material.INK_SACK).color(DyeColor.BLUE).build()));
        addPerk(new DropOnEnchantPerk(40, 35, "Pode ganhar dois Lápis-Lazúli ao encantar", ItemBuilder.item(Material.INK_SACK).amount(2).color(DyeColor.BLUE).build()));
        addPerk(new DropOnEnchantPerk(80, 150, "Pode ganhar um livro com o encantamento", new ItemStack(Material.ENCHANTED_BOOK)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, EnchantItemEvent ent) {
                ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
                ent.getEnchantsToAdd().forEach((en, lvl) -> {
                    item.addUnsafeEnchantment(en, lvl);
                });
                return new ItemStack[]{item};

            }

        });
        addPerk(new DropOnEnchantPerk(100, 150, "Pode duplicar o item ao encantar", new ItemStack(Material.DIAMOND_SWORD)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, EnchantItemEvent ent) {

                ItemStack item = ent.getItem().clone();
                ent.getEnchantsToAdd().forEach((en, lvl) -> {
                    item.addUnsafeEnchantment(en, lvl);
                });
                return new ItemStack[]{item};

            }
        });
    }

    @Override
    public String getTitulo() {
        return "Encantador";
    }

    @Override
    public String getDesc() {
        return "ganha xp ao encantar itens";
    }

    @EventHandler
    public void enchant(EnchantItemEvent ev) {
        if (ev.getItem() != null && ev.getItem().getType() == Material.FISHING_ROD) {
            ev.setCancelled(true);
            return;
        }
        if (ev.getItem() != null) {
            int level = ev.getExpLevelCost();
            int plevel = getLevel(ev.getEnchanter());
            int xp = ev.whichButton() + 1;
            int dim = 0;
            if (plevel >= UM_LEVEL.getLevel()) {
                dim++;
            } else if (plevel >= UM_LEVEL_RANDOM.getLevel()) {
                if (UM_LEVEL_RANDOM.roll()) {
                    dim++;
                }
            }
            callActions(ev.getEnchanter(), DropOnEnchantPerk.class, ev);
            ev.setExpLevelCost(Math.max(ev.getExpLevelCost() - dim, 0));
            addXp(ev.getEnchanter(), xp);
        }

    }

    @Override
    public int getMultiplicador() {
        return 60;
    }
}
