package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.menus.MainMenu;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuestListener extends BaseListener {

    private Cooldown clickNpc = new Cooldown(2000);

    private QuestAddon addon;

    public QuestListener(QuestAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        addon.getManager().clearCache(addon.getUser(ev.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent ev) {
        addon.getManager().clearCache(addon.getUser(ev.getPlayer()));
        if (addon.isAddQuests()) {
            addon.getManager().getDaily().addDailyQuest(ev.getPlayer());
        }
    }


    @EventHandler
    public void interact(PlayerInteractEntityEvent ev) {
      /*  Entity e = ev.getRightClicked();
        if (e.hasMetadata("NPC")) {
            if (e.getCustomName() != null) {
                boolean is = ChatColor.stripColor(e.getCustomName()).equalsIgnoreCase(nomenpc);
                if (is) {
                    if (Cooldown.isCooldown(ev.getUser(), "questnpc")) {
                        return;
                    }
                    Cooldown.addCoolDown(ev.getUser(), "questnpc", 2000);

                    new MainMenu(ev.getUser().getUniqueId()).open(ev.getUser());
                }
            }
        }
*/
    }

    @EventHandler
    public void rightClickNpc(NPCRightClickEvent ev) {
        if (addon.npc != null) {
            if (addon.npc == ev.getNPC()) {
                if (clickNpc.isCooldown(ev.getClicker().getUniqueId())) {
                    return;
                }
                clickNpc.addCooldown(ev.getClicker().getUniqueId());
                new MainMenu(addon.getPlayerId(ev.getClicker()),addon.getManager()).open(ev.getClicker());
            }

        }
    }
}
