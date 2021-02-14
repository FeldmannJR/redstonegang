package dev.feldmann.redstonegang.wire.modulos.maps.queue;

import dev.feldmann.redstonegang.wire.Wire;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SendMapsQueue {

    private ConcurrentHashMap<UUID, List<QueueEntry>> queue = new ConcurrentHashMap();

    int perPlayerPerTick = 15;
    int maxPerTick = 500;
    public static int colunaPixels = 1;

    public SendMapsQueue() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Wire.instance, new Runnable() {
            @Override
            public void run() {
                doTick();

            }
        }, 2, 1);

    }

    public void clear(UUID uid) {
        queue.remove(uid);
    }

    public void add(QueueEntry entry) {
        UUID u = entry.p.getUniqueId();
        if (!queue.containsKey(u)) {
            queue.put(u, new ArrayList<>());
        }
        queue.get(u).add(entry);

    }


    private void doTick() {
        int fiz = 0;
        Iterator<UUID> uuids = queue.keySet().iterator();
        while (uuids.hasNext()) {
            if (fiz >= maxPerTick) {
                break;
            }
            UUID uid = uuids.next();
            Player p = Bukkit.getPlayer(uid);
            if (p != null && p.isOnline()) {
                fiz += manda(p, uuids);
            } else {
                uuids.remove();
            }
        }
    }

    private void handleFinish(FinishSendEntry en) {
        en.quadro.sending.remove(en.p.getUniqueId());
    }

    private void mandaPacote(MapQueueEntry entry) {
        if (entry.p.isOnline()) {
            ((CraftPlayer) entry.p).getHandle().playerConnection.sendPacket(entry.packet);
        }

    }


    private int manda(Player p, Iterator it) {
        int fiz = 0;
        List<QueueEntry> entry = queue.get(p.getUniqueId());
        for (int x = 0; x < perPlayerPerTick; x++) {
            if (!entry.isEmpty()) {
                QueueEntry en = entry.get(0);
                if (en instanceof MapQueueEntry) {
                    mandaPacote((MapQueueEntry) en);
                    fiz++;
                } else if (en instanceof FinishSendEntry) {
                   handleFinish((FinishSendEntry) en);
                }
                entry.remove(0);

            } else {
                it.remove();
                return fiz;
            }

        }
        if (entry.isEmpty()) {
            it.remove();
        }
        return fiz;
    }


}
