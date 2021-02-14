package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.edit.EditShopMenu;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class FloatShopListener extends BaseListener {

    FloatShopAddon addon;
    private Cooldown npcCd = new Cooldown(500);

    public FloatShopListener(FloatShopAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void clickNpc(PlayerInteractEntityEvent ev) {
        NPC npc = CitizensAPI.getNPCRegistry().getNPC(ev.getRightClicked());
        if (npc != null) {
            if (addon.hasShop(npc)) {

                if (npcCd.isCooldown(ev.getPlayer().getUniqueId())) {
                    return;
                }
                FloatShop shop = addon.getShop(npc);
                if (shop.editing) {
                    C.error(ev.getPlayer(), "Loja sendo modificada aguarde!");
                    return;
                }
                if (ev.getPlayer().isSneaking() && ev.getPlayer().hasPermission(FloatShopAddon.SHOPADM_PERMISSION.getKey())) {
                    new EditShopMenu(addon, shop).open(ev.getPlayer());
                    return;
                }
                if (shop.getPermission() != null) {
                    if (ev.getPlayer().hasPermission(shop.getPermission())) {
                        C.error(ev.getPlayer(), "Você não pode abrir esta loja!");
                        return;
                    }
                }
                npcCd.addCooldown(ev.getPlayer().getUniqueId());
                addon.openShop(ev.getPlayer(), npc);
            }
        }
    }

    @EventHandler
    public void npcRemoved(NPCRemoveEvent ev) {
        NPC npc = ev.getNPC();
        FloatShop shop = addon.getShop(npc);
        if (shop != null) {
            addon.deleteShop(shop);
        }
    }
}
