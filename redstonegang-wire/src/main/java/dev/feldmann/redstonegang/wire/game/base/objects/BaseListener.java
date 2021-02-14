package dev.feldmann.redstonegang.wire.game.base.objects;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class BaseListener implements Listener {
    private List<RedstoneCmd> _commands = new ArrayList();
    private List<BaseListener> _listeners = new ArrayList();

    public void unregisterCmdsAndListeners() {
        unregisterCmdsAndListeners(false);
    }

    public void unregisterCmdsAndListeners(boolean recursive) {
        for (RedstoneCmd cmd : _commands) {
            Wire.instance.cmds().unregisterCommand(cmd);
        }
        for (BaseListener listener : _listeners) {
            HandlerList.unregisterAll(listener);
            if (recursive) {
                listener.unregisterCmdsAndListeners(true);
            }
        }
        _commands.clear();
        _listeners.clear();
    }

    protected void registerListener(BaseListener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Wire.instance);
        _listeners.add(listener);
    }

    protected void unregisterListener(BaseListener listener) {
        boolean contains = _listeners.remove(listener);
        if (contains) {
            HandlerList.unregisterAll(listener);
        }
    }

    public List<RedstoneCmd> getCommands() {
        return _commands;
    }

    public void registerCommand(RedstoneCmd... cmds) {
        for (RedstoneCmd cmd : cmds) {
            _commands.add(cmd);
            Wire.instance.cmds().addCommand(cmd);
        }
    }

}
