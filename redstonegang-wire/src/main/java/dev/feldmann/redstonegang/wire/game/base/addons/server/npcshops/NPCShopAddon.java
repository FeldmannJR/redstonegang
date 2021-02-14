package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.cmds.LojaAdm;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.menus.LojaMenu;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.*;

@Dependencies(hard = EconomyAddon.class)
public class NPCShopAddon extends Addon {

    public static NPCShopAddon instance;
    private LojaDB db;
    private String databaseName;

    private HashMap<UUID, LojaNPC> lojas = new HashMap<UUID, LojaNPC>();

    public NPCShopAddon(String databaseName) {
        instance = this;
        this.databaseName = databaseName;
    }

    @Override
    public void onEnable() {
        db = new LojaDB(databaseName);
        lojas = db.loadLojas();
        registerCommand(new LojaAdm(this));

        for (LojaNPC l : lojas.values()) {
            l.manager = this;
        }
    }

    @Override
    public void onDisable() {

        for (LojaNPC l : new ArrayList<LojaNPC>(lojas.values())) {
            if (l.getNPC() == null) {
                deleteLoja(l);
            }

        }
    }

    public static NPC getSelected(CommandSender p) {
        return CitizensAPI.getDefaultNPCSelector().getSelected(p);
    }

    public boolean hasLoja(NPC n) {
        return lojas.containsKey(n.getUniqueId());
    }

    public LojaNPC getLoja(NPC n) {
        return lojas.get(n.getUniqueId());
    }

    public void addLoja(LojaNPC loja) {
        lojas.put(loja.getUUID(), loja);
        loja.manager = this;
        db.saveLoja(loja);
    }

    public void deleteLoja(LojaNPC loja) {
        lojas.remove(loja.getUUID());
        db.deleteLoja(loja);
    }

    public void save(LojaNPC npc) {
        db.saveLoja(npc);
    }

    private Cooldown npcCd = new Cooldown(1000);

    @EventHandler
    public void click(PlayerInteractEntityEvent ev) {
        NPC npc = CitizensAPI.getNPCRegistry().getNPC(ev.getRightClicked());
        if (npc != null) {
            if (hasLoja(npc)) {
                if (npcCd.isCooldown(ev.getPlayer().getUniqueId())) {
                    return;
                }
                LojaNPC loja = getLoja(npc);
                if (loja.editing) {
                    C.error(ev.getPlayer(), "Loja sendo modificada aguarde!");
                    return;
                }
                if (loja.getPermission() != null) {
                    if (ev.getPlayer().hasPermission(loja.getPermission())) {
                        C.error(ev.getPlayer(), "Você não pode abrir esta loja!");
                        return;
                    }
                }
                npcCd.addCooldown(ev.getPlayer().getUniqueId());
                new LojaMenu(loja).open(ev.getPlayer());
            }
        }
    }


    public Collection<LojaNPC> getLojas() {
        return lojas.values();
    }
}
