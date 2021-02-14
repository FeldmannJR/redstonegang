package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.player.config.types.JsonConfig;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds.*;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.cmds.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class StaffCommandsAddon extends Addon {

    private boolean useServerIdentifier;

    public StaffCommandsAddon(boolean useServerIdentifier) {
        this.useServerIdentifier = useServerIdentifier;
    }

    public BooleanConfig VANISHED;
    public BooleanConfig FLY;
    public BooleanConfig GOD;
    public JsonConfig VANISH_DATA;
    public PermissionDescription CAN_USE_VANISH;
    public PermissionDescription CAN_USE_GOD;
    public PermissionDescription CAN_USE_FLY;

    @Override
    public void onEnable() {
        CAN_USE_VANISH = new PermissionDescription("Pode usar vanish", generatePermission("vanish"), "Pode usar o comando /vanish e ver os outros players que estão");
        CAN_USE_GOD = new PermissionDescription("Pode usar godmode", generatePermission("god"), "Pode usar o comando /vanish");
        CAN_USE_FLY = new PermissionDescription("Pode usar fly", generatePermission("fly"), "Pode usar o comando /fly");
        VANISHED = new BooleanConfig(generateStaffConfig("vanished"), false);
        GOD = new BooleanConfig(generateStaffConfig("god"), false);
        FLY = new BooleanConfig(generateStaffConfig("fly"), false);
        VANISH_DATA = new JsonConfig(generateStaffConfig("vanish_data"), new JsonObject());
        addConfig(VANISHED, VANISH_DATA, FLY, GOD);
        addOption(CAN_USE_FLY, CAN_USE_GOD, CAN_USE_VANISH);
        registerCommand(
                new ReapearCommand(this),
                new VanishCommand(this),
                new VanishFlagsCommand(this),
                new FlyCommand(this),
                new GodCommand(this)
        );
        registerListener(new VanishFlagListener(this));
    }

    private String generateStaffConfig(String name) {
        if (useServerIdentifier) {
            return generateConfigName(name);
        } else {
            return generateConfigNameWithoutIdentifier(name);
        }
    }

    public void setFlag(Player p, VanishFlag flag, boolean value) {
        JsonObject config = getUser(p).getConfig(VANISH_DATA);
        config.addProperty(flag.name(), value);
        getUser(p).setConfig(VANISH_DATA, config);
    }

    public boolean getFlag(Player p, VanishFlag flag) {
        JsonObject data = getUser(p).getConfig(VANISH_DATA);
        if (data.has(flag.name())) {
            JsonPrimitive value = data.getAsJsonPrimitive(flag.name());
            if (value.isBoolean()) {
                return value.getAsBoolean();
            }
        }
        return flag.getDefaultValue();
    }

    public void setVanished(Player p, boolean vanished) {
        User user = getUser(p);
        user.setConfig(VANISHED, vanished);
        updateVanish(p);
    }

    public void setGod(Player p, boolean on) {
        getUser(p).setConfig(GOD, on);
    }

    public void setFly(Player p, boolean on) {
        getUser(p).setConfig(FLY, on);
        updateFly(p);
    }

    public void updateVanish(Player p) {
        boolean isVanished = isVanished(p);
        boolean canSee = hasVanishPermission(p);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online != p) {
                if (isVanished(online) && !canSee) {
                    p.hidePlayer(online);
                } else {
                    p.showPlayer(online);
                }
                if (isVanished && !hasVanishPermission(online)) {
                    online.hidePlayer(p);
                } else {
                    online.showPlayer(p);
                }
            }
        }
    }

    public void updateFly(Player p) {
        if (p.getGameMode() == GameMode.CREATIVE) return;
        p.setAllowFlight(isFlyMode(p));
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void join(PlayerJoinServerEvent ev) {
        if (!hasVanishPermission(ev.getPlayer()) && isVanished(ev.getPlayer())) {
            ev.getUser().setConfig(VANISHED, false);
        }
        if (isFlyMode(ev.getPlayer()) && !getUser(ev.getPlayer()).hasPermission(this.CAN_USE_FLY)) {
            ev.getUser().setConfig(FLY, false);
        }
        if (isGodMode(ev.getPlayer()) && !getUser(ev.getPlayer()).hasPermission(CAN_USE_GOD)) {
            ev.getUser().setConfig(GOD, false);
        }
        updateVanish(ev.getPlayer());
    }

    public boolean isFlyMode(Player p) {
        return getUser(p).getConfig(FLY);
    }

    public boolean isGodMode(Player p) {
        if (p.hasMetadata("NPC")) return false;
        return getUser(p).getConfig(GOD);
    }


    public boolean hasVanishPermission(Player p) {
        return p.hasPermission(CAN_USE_VANISH.getKey());
    }

    public boolean isVanished(Player p) {
        return getUser(p).getConfig(VANISHED);
    }

    @EventHandler
    public void godmode(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof Player) {
            Player p = (Player) ev.getEntity();
            if (isGodMode(p)) {
                ev.setCancelled(true);
            }
            if (ev.getCause() == EntityDamageEvent.DamageCause.VOID) {
                p.teleport(p.getWorld().getSpawnLocation());
            }
        }
    }
}
