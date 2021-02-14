package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.pecuarista;

import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.Job;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.*;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tosar.PlayerShearAnimalEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tosar.ResourcesFromAnimalsAddon;
import dev.feldmann.redstonegang.wire.modulos.customevents.PlayerBreedingEntityEvent;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.*;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PecuaristaJob extends Job {


    public PecuaristaJob() {
        super("pecuarista", "Pecuarista", new ItemStack(Material.SHEARS));
        addPerk(new DropOnKillSpecificPerk(10, 15, "Pode ganhar peixe ao matar jaguatirica", new ItemStack(Material.RAW_FISH, 1), EntityType.OCELOT));
        addPerk(new DropOnKillSpecificPerk(20, 15, "Pode ganhar galinha assada ao matar galinha", new ItemStack(Material.COOKED_CHICKEN, 1), EntityType.CHICKEN));
        addPerk(new DropOnBreedSpecificPerk(30, 15, "Pode ganhar um laço a cruzar cavalos", new ItemStack(Material.LEASH), EntityType.HORSE));
        addPerk(new DropOnShearSpecificPerk(50, 15, "Pode ganhar uma lã extra ao tosar uma ovelha", new ItemStack(Material.WOOL), EntityType.SHEEP) {
            @Override
            public ItemStack[] getDropItemStack(Player p, Entity ent) {
                if (ent instanceof Sheep) {
                    Sheep s = (Sheep) ent;
                    DyeColor color = s.getColor();
                    return new ItemStack[]{ItemBuilder.item(Material.WOOL).color(color).build()};
                }
                return new ItemStack[]{new ItemStack(Material.WOOL)};
            }
        });
        addPerk(new DropOnShearSpecificPerk(70, 15, "Pode ganhar uma carne ao tosar uma vaca", new ItemStack(Material.RAW_BEEF), EntityType.COW));

        addPerk(new DropOnShearPerk(90, 25, "Pode ganhar o dobro ao tosar um animais", new ItemStack(Material.SHEARS)) {
            @Override
            public ItemStack[] getDropItemStack(Player p, Entity ent) {
                ItemStack[] drops = PecuaristaJob.this.getAddon().a(ResourcesFromAnimalsAddon.class).getDrops(ent.getType());
                if (drops != null) {
                    return drops;
                }
                if (ent instanceof Sheep) {
                    Sheep s = (Sheep) ent;
                    DyeColor color = s.getColor();
                    return new ItemStack[]{ItemBuilder.item(Material.WOOL).color(color).build()};
                }
                return null;
            }
        });


    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void tosa(PlayerShearAnimalEvent ev) {
        addXp(ev.getPlayer(), 2);
        callActions(ev.getPlayer(), DropOnShearPerk.class, ev.getTosado());
    }

    @EventHandler
    public void breed(PlayerBreedingEntityEvent ev) {
        addXp(ev.getPlayer(), 2);
        callActions(ev.getPlayer(), DropOnBreedPerk.class, ev.getSpawned());
    }

    @EventHandler
    public void killAnimal(EntityDeathEvent ev) {
        if (ev.getEntity().getKiller() != null && ev.getEntity().getKiller() instanceof Player) {
            Player killer = ev.getEntity().getKiller();
            if (ev.getEntity() instanceof Animals) {
                addXp(killer, 1);
                callActions(killer, DropOnKillPerk.class, ev.getEntity());
            }
        }

    }

    @Override
    public String getTitulo() {
        return "Pecuarista";
    }

    @Override
    public int getMultiplicador() {
        return 80;
    }

    @Override
    public String getDesc() {
        return "ganha xp ao matar/tosar animais";
    }
}
