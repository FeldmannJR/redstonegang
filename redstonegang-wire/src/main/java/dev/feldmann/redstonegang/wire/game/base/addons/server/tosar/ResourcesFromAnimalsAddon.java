package dev.feldmann.redstonegang.wire.game.base.addons.server.tosar;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourcesFromAnimalsAddon extends Addon {

    private HashMap<EntityType, ItemStack[]> item = new HashMap<>();


    @Override
    public void onEnable() {
        addDrop(EntityType.COW, Material.LEATHER);
        addDrop(EntityType.CHICKEN, Material.FEATHER);
        addDrop(EntityType.PIG, Material.PORK);
        addDrop(EntityType.RABBIT, Material.RABBIT_HIDE);
        addDrop(EntityType.MUSHROOM_COW, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM);

    }

    private void addDrop(EntityType entity, Material... m) {
        ItemStack[] item = new ItemStack[m.length];
        for (int x = 0; x < m.length; x++) {
            item[x] = new ItemStack(m[x]);
        }
        this.item.put(entity, item);
    }

    @EventHandler
    public void sheep(PlayerShearEntityEvent ev) {
        if (ev.getEntity() instanceof Sheep) {
            List<ItemStack> drops = new ArrayList<>();
            drops.add(new ItemStack(Material.WOOL));
            PlayerShearAnimalEvent pta = new PlayerShearAnimalEvent(ev.getPlayer(), ev.getEntity(), drops);
            Bukkit.getPluginManager().callEvent(pta);
            if (pta.isCancelled()) {
                ev.setCancelled(true);
                return;
            }
        } else if (ev.getEntity() instanceof MushroomCow) {
            ev.setCancelled(true);
        }
    }

    Cooldown tiraRecursos = new Cooldown(30 * 60 * 1000);


    @EventHandler
    public void interact(PlayerInteractEntityEvent ev
    ) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.SHEARS) {
            EntityType et = ev.getRightClicked().getType();
            if (!item.containsKey(et)) {
                return;
            }
            if (!tiraRecursos.isCooldown(ev.getRightClicked().getUniqueId())) {
                Location l = ev.getRightClicked().getLocation();
                List<ItemStack> drop = new ArrayList<>();
                drop.add(RandomUtils.getRandom(this.item.get(et)));
                PlayerShearAnimalEvent pta = new PlayerShearAnimalEvent(ev.getPlayer(), ev.getRightClicked(), drop);
                Bukkit.getPluginManager().callEvent(pta);
                if (pta.isCancelled()) {
                    ev.setCancelled(true);
                    return;
                }
                drop = pta.getDrops();
                if (!drop.isEmpty()) {

                    for (ItemStack i : drop) {
                        ev.getRightClicked().getWorld().dropItemNaturally(ev.getRightClicked().getLocation(), i);
                    }

                    ev.getPlayer().getWorld().playSound(ev.getRightClicked().getLocation(), Sound.SHEEP_SHEAR, 1, 1);
                    ev.getRightClicked().playEffect(EntityEffect.HURT);
                    tiraRecursos.addCooldown(ev.getRightClicked().getUniqueId());
                }
            } else {
                ev.getPlayer().sendMessage("§cEspere um pouco para tirar recurso novamente!");
            }

        }
    }

    public ItemStack[] getDrops(EntityType type) {
        if (this.item.containsKey(type)) {
            return item.get(type);
        }

        return null;
    }
}
