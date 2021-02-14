package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests.cmds.CmdOe;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSyncLocationEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;


public class
ExtraChests extends Addon {


    public static String ENDERCHEST_PERMISSION = "rg.extrachests.enderchest";
    public PermissionDescription ENDERCHEST;
    public static final String EXTRA_CHEST_VIP = "rg.extrachests.vip";
    public static final String EXTRA_CHEST_1 = "rg.extrachests.1";
    public static final String EXTRA_CHEST_2 = "rg.extrachests.2";
    public static final String EXTRA_CHEST_3 = "rg.extrachests.3";
    public static final String OPEN_OTHER_ENDERCHEST = "rg.extrachests.staff.openother";

    ExtraChestsDB db;
    private HashMap<Integer, PlayerChestData> cache;
    private Cooldown cd = new Cooldown(1000);

    @Override
    public void onEnable() {
        db = new ExtraChestsDB(this);
        registerCommand(new CmdOe(this));
        cache = new HashMap();
        ENDERCHEST = new PermissionDescription("Abrir Enderchests", ENDERCHEST_PERMISSION, "Pode usar enderchests");
        addOption(ENDERCHEST);
        for (ChestList c : ChestList.values()) {
            // Gambiarra
            if (c != ChestList.ENDERCHEST)
                addOption(new PermissionDescription("Enderchest " + c.getNome(), c.getPermission(), "Permissão para poder abrir o bau " + c.getNome()));
        }
        addOption(new PermissionDescription("Abrir enderchests de outros", OPEN_OTHER_ENDERCHEST, "abrir enderchest de outras pessoas", true));
    }

    @Override
    public void onDisable() {
        for (PlayerChestData data : cache.values()) {
            for (ChestList c : ChestList.values()) {
                ChestData cdata = data.getData(c);
                if (cdata.hasMenu()) {
                    cdata.getMenu().close(null);
                }
            }
        }
    }

    public PlayerChestData getPlayer(int pid) {
        if (!cache.containsKey(pid)) {
            cache.put(pid, db.load(pid));
        }
        return cache.get(pid);
    }

    public PlayerChestData getPlayerData(Player p) {
        return getPlayer(getPlayerId(p));
    }

    public void save(int pid, ChestList c) {
        if (cache.containsKey(pid))
            db.save(pid, c, cache.get(pid).getData(c).getItens());
    }

    @EventHandler
    public void join(PlayerSyncLocationEvent ev) {
        clearCache(ev.getPlayer());
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        clearCache(ev.getPlayer());
    }

    public void clearCache(Player p) {
        if (cache.containsKey(getPlayerId(p))) {
            PlayerChestData data = cache.remove(getPlayerId(p));
            for (ChestList c : ChestList.values()) {
                ChestData cdata = data.getData(c);
                if (cdata.hasMenu()) {
                    //Fecha de todos os staffs bizoiando
                    cdata.getMenu().closeAll();
                }
            }
        }
    }


    public void openEnderchest(Player p) {
        getPlayerData(p).getData(ChestList.ENDERCHEST).getMenu().open(p);
    }

    public void openEnderchest(Player p, int owner) {
        User user = getUser(owner);
        if (user.isOnline() && user.getServerIdentifier() != null) {
            if (user.getServerIdentifier().equals(getServer().getIdentifier())) {
                // Se o nego tiver no mesmo tipo de server(survival_Spawn e survival_Minerar) não deixa usar
                Player pOnline = RedstoneGangSpigot.getOnlinePlayer(owner);
                // Se não for no servidor atual
                if (pOnline == null || !pOnline.isOnline()) {
                    // Cancela
                    C.error(p, "O jogador está online em outro servidor! Vá até ele ou espere sair!");
                    return;
                }
            }
        }
        getPlayer(owner).getData(ChestList.ENDERCHEST).getMenu().open(p);
    }

    @EventHandler
    public void open(InventoryOpenEvent ev) {
        if (ev.getInventory().getType() == InventoryType.ENDER_CHEST) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void interactEnderchest(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ev.getPlayer().isSneaking() && ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() != Material.AIR) {
                return;
            }
            if (ev.getClickedBlock().getType() == Material.ENDER_CHEST) {
                ev.setCancelled(true);
                if (!getUser(ev.getPlayer()).hasPermission(ENDERCHEST)) {
                    C.permission(ev.getPlayer());
                    return;
                }
                if (!cd.isCooldown(ev.getPlayer().getUniqueId())) {
                    openEnderchest(ev.getPlayer());
                    cd.addCooldown(ev.getPlayer().getUniqueId());
                }
            }
        }
    }
}
