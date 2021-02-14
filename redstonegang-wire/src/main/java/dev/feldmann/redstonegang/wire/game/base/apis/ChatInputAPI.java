package dev.feldmann.redstonegang.wire.game.base.apis;

import dev.feldmann.redstonegang.wire.game.base.objects.API;

import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ChatInputAPI extends API {

    private HashMap<UUID, ChatExecute> funcoes = new HashMap<>();


    public void getInput(Player p, String what, BiConsumer<Player, String> function) {
        p.closeInventory();
        p.sendMessage("§cDigite no chat o valor de " + what + " ou digite §ax§c para fechar!");
        funcoes.put(p.getUniqueId(), new ChatExecute(30, what, function));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void chat(AsyncPlayerChatEvent ev) {
        if (funcoes.containsKey(ev.getPlayer().getUniqueId())) {
            String msg = ev.getMessage();
            if (msg.equalsIgnoreCase("x")) {
                funcoes.get(ev.getPlayer().getUniqueId()).function.accept(ev.getPlayer(), null);
            } else {
                funcoes.get(ev.getPlayer().getUniqueId()).function.accept(ev.getPlayer(), ev.getMessage());
            }
            funcoes.remove(ev.getPlayer().getUniqueId());
            ev.setCancelled(true);
        }
    }


    @EventHandler
    private void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_1) {
            List<UUID> uids = new ArrayList(funcoes.keySet());
            for (UUID u : uids) {
                ChatExecute e = funcoes.get(u);
                if (e.expira < System.currentTimeMillis()) {
                    Player p = Bukkit.getPlayer(u);
                    if (p != null) {
                        e.function.accept(p, null);
                        p.sendMessage("§eO tempo para digitar expirou!");
                    }
                    funcoes.remove(u);
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static class ChatExecute {
        long expira;
        String oq;
        BiConsumer<Player, String> function;

        public ChatExecute(int timeout, String oq, BiConsumer<Player, String> function) {
            this.expira = System.currentTimeMillis() + (timeout * 1000);
            this.oq = oq;
            this.function = function;
        }

    }

}
