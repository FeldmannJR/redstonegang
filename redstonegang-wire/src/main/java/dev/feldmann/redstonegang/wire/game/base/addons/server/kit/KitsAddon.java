package dev.feldmann.redstonegang.wire.game.base.addons.server.kit;

import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.addons.both.cooldown.PersistentCdAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.CmdKit;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.cmds.adm.CmdKitAdm;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

@Dependencies(hard = PersistentCdAddon.class)
public class KitsAddon extends Addon {


    KitDB db;
    private String dbName;
    private String permissionPreffix = "rg.kit.";

    private HashMap<String, Kit> kits = new HashMap<>();

    public KitsAddon(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void onEnable() {
        db = new KitDB(dbName);
        kits = db.loadKits();
        registerCommand(new CmdKit(this));
        registerCommand(new CmdKitAdm(this));
        for (Kit k : kits.values()) {
            addOption(new PermissionDescription("kit " + k.getName(), permissionPreffix + k.getName(), "pode usar o kit " + k.getName()));
        }
    }

    public Collection<String> getKits() {
        return kits.keySet();
    }


    public void listar(CommandSender cs) {
        cs.sendMessage("§9Kits:");
        String s = "";
        ChatColor color = ChatColor.YELLOW;
        for (Kit k : kits.values()) {
            if (hasPermission(cs, k)) {
                s += color + k.getName() + "(" + k.getFormattedCooldown() + ") ";
                color = color == ChatColor.YELLOW ? ChatColor.WHITE : ChatColor.YELLOW;
            }
        }
        s = s.trim();
        cs.sendMessage(s);

    }

    public boolean hasPermission(CommandSender p, Kit k) {
        return p.hasPermission(permissionPreffix + "" + k.getName());
    }

    public String getCdName(Kit k) {
        return "kit-" + dbName + "-" + k.getName();
    }

    public void giveKit(Player p, Kit k) {
        if (!hasPermission(p, k)) {
            C.error(p, "Você não tem permissão para usar este kit!");
            return;
        }
        if (a(PersistentCdAddon.class).isCooldown(p, getCdName(k))) {
            return;
        }
        if (InventoryHelper.getFreeSlots(p) < k.countItens()) {
            C.error(p, "Você não tem espaço no inventário!");
            return;
        }
        k.give(p);
        a(PersistentCdAddon.class).addCooldownMinutos(p, getCdName(k), k.getCooldown());
        if (k.getCooldown() > 0) {
            C.info(p, "Você recebeu o Kit %%, e só poderá usar denovo em %%!", k.getName(), k.getFormattedCooldown());
        } else {
            C.info(p, "Você recebeu o Kit %%, e %% poderá usar denovo! !", k.getName(), "NUNCA");

        }

    }

    public Kit getKit(String arg) {
        return kits.get(arg);
    }

    public void addKit(Kit k) {
        kits.put(k.getName(), k);
        db.saveKit(k);
    }

    public void deleteKit(Kit k) {
        kits.remove(k.getName());
        db.deleteKit(k);
    }
}
