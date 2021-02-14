package dev.feldmann.redstonegang.wire.game.base.addons.both;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Bukkit;

import java.util.HashSet;

public class SchedulerAddon extends Addon {
    private HashSet<Integer> tasks = new HashSet();

    /*
     * RODA UMA TASK DEPOIS DE X TICKS
     * TA MAS PRA QUE ESSA CLASSE?
     * CASO MUDAR DE MINIGAME CANCELAR TODAS AS TASKS DO MINIGAME
     */
    public int runAfter(Runnable r, int ticks) {
        return addTask(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin(), r, ticks));
    }

    public void runSync(Runnable r) {
        addTask(Bukkit.getScheduler().runTask(plugin(), r).getTaskId());
    }

    public int rodaRepeting(Runnable r, int intervalo) {
        return addTask(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin(), r, intervalo, intervalo));
    }

    public int rodaAsync(Runnable r, int ticks) {
        return addTask(Bukkit.getScheduler().runTaskLater(plugin(), r, ticks).getTaskId());
    }

    public int rodaAsync(Runnable r) {
        return addTask(Bukkit.getScheduler().runTaskAsynchronously(plugin(), r).getTaskId());
    }

    public void cancelTask(int id){
        Bukkit.getScheduler().cancelTask(id);
    }
    private int addTask(int t) {
        tasks.add(t);
        return t;
    }

    @Override
    public void onDisable() {
        for (int t : tasks) {
            Bukkit.getScheduler().cancelTask(t);
        }
        tasks.clear();
    }
}
