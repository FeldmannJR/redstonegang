package dev.feldmann.redstonegang.wire.game.games.other.login;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.player.PlayerUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class Listeners extends BaseListener {

    Login login;

    public Listeners(Login login) {
        this.login = login;
    }

    public void teleportSpawn(Entity ent) {
        ent.teleport(login.getMapa().getSpawn());
    }

    @EventHandler
    public void spawn(PlayerSpawnLocationEvent ev) {
        ev.setSpawnLocation(login.getMapa().getSpawn());
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        login.clearData(ev.getPlayer());

        ev.getPlayer().setGameMode(GameMode.ADVENTURE);
        PlayerUtils.limpa(ev.getPlayer());
        login.sendMessageLogin(ev.getPlayer());
        for (Player pOn : Bukkit.getOnlinePlayers()) {
            pOn.hidePlayer(ev.getPlayer());
            ev.getPlayer().hidePlayer(pOn);
        }
        ev.getPlayer().setGameMode(GameMode.ADVENTURE);
        Player p = ev.getPlayer();
        C.blank(p, 10);
        p.sendMessage("  §f➥ §b§lLogin");
        C.blank(p, 1);
        p.sendMessage("   §eUse §c/login <senha> §epara logar!");
        TextComponent component = new TextComponent(TextComponent.fromLegacyText("   §7Para trocar sua senha clique §caqui§7!"));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.redstonegang.com.br/account/security"));
        p.spigot().sendMessage(component);
        C.blank(p, 1);
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        login.clearData(ev.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFoodChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        event.setCancelled(true);
    }

    @EventHandler
    public void updateEvent(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_16) {

        }
        if (ev.getType() == UpdateType.SEC_1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                LoginData data = login.getData(p);
                if (data.expired()) {
                    p.kickPlayer("§cVocê demorou muito tempo para logar-se!");
                    continue;
                }
                login.sendTitle(p);
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        String command = event.getMessage().split(" ")[0].replaceFirst("/", "");
        if (!command.equals("register")
                && !command.equals("registro")
                && !command.equals("login")
                && !command.equals("logar")
                && !command.equals("l")
                && !command.equals("changepw")
                && !command.equals("mudarsenha")) {
            event.setMessage("/");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(AsyncPlayerChatEvent ev) {
        ev.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent ev) {
        ev.setCancelled(true);
        if (ev.getCause() == EntityDamageEvent.DamageCause.VOID) {
            teleportSpawn(ev.getEntity());
        }
    }

    @EventHandler
    public void leafdecay(LeavesDecayEvent ev) {
        ev.setCancelled(true);
    }
}
