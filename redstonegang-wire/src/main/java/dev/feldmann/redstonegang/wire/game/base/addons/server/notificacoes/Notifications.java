package dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;

public class Notifications extends Addon {

    public String databaseName;

    private NotificationDB db;
    private HashMap<String, NotificationType> types = new HashMap<>();

    public Notifications(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void onEnable() {
        db = new NotificationDB(this);
    }

    public void register(NotificationType type) {
        types.put(type.getType(), type);
    }

    public NotificationType getType(String name) {
        if (types.containsKey(name)) {
            return types.get(name);
        }
        return null;
    }

    public Notification getNotificao(int user, NotificationType type) {
        return db.loadNotification(user, type);
    }

    public void addNotification(Notification not) {
        scheduler().rodaAsync(() -> {
            Notification load = getNotificao(not.owner, not.type);
            if (load == null) {
                not.vars = not.type.processAdd(null, not.vars);
            } else {
                not.vars = not.type.processAdd(load.vars, not.vars);
            }
            db.saveNotification(not);
        }, 0);
    }

    @EventHandler
    public void join(PlayerJoinEvent ev) {
        final Player p = ev.getPlayer();
        scheduler().rodaAsync(() -> {
            if (p != null && p.isOnline() && p.isValid()) {
                List<Notification> nots = db.loadNotifications(RedstoneGang.getPlayer(p.getUniqueId()).getId());
                db.deleteNotifications(RedstoneGang.getPlayer(p.getUniqueId()).getId());
                for (Notification not : nots) {
                    TextComponent[] text = not.type.process(not.vars);
                    for (TextComponent t : text)
                        p.spigot().sendMessage(t);
                }

            }
        }, 20 * 4);
    }


}
