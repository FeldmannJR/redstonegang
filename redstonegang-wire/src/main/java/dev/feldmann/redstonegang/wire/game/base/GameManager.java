package dev.feldmann.redstonegang.wire.game.base;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addonconfigs.AddonConfigManager;
import dev.feldmann.redstonegang.wire.game.base.controle.ServerControler;
import dev.feldmann.redstonegang.wire.game.base.events.ServerLoadedEvent;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.exceptions.CyclicalDependencyException;
import dev.feldmann.redstonegang.wire.game.base.objects.exceptions.HardDependencyNotFoundException;
import dev.feldmann.redstonegang.wire.utils.player.PlayerUtils;
import dev.feldmann.redstonegang.wire.game.base.controle.cmds.CmdTroca;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    private Server server;
    private ServerControler controler = new ServerControler();
    public AddonConfigManager addonsConfigs;

    public void load() {
        addonsConfigs = new AddonConfigManager();
        Wire.instance.cmds().addCommand(new CmdTroca());
        ServerEntry next = controler.next();
        if (next == null) {
            Wire.log("Não foi selecionado game para iniciar o servidor!");
            stop();
            return;
        }
        loadGame(next);
    }

    public void stop() {
        Bukkit.getScheduler().runTask(Wire.instance, Bukkit::shutdown);
    }

    public void loadGame(ServerEntry entry) {

        boolean update = false;
        if (server != null) {
            server.setInvalid(true);
            server.disable();
            server = null;
            update = true;
        }
        try {
            server = entry.getGameClass().newInstance();
            try {
                long start = System.currentTimeMillis();
                log("Carregando " + entry.getGameClass().getSimpleName());
                server._enable();
                server._lateEnable();
                Bukkit.getPluginManager().callEvent(new ServerLoadedEvent());
                String identifier = server.getIdentifier();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerUtils.limpa(p);
                    User user = RedstoneGangSpigot.getPlayer(p);
                    boolean updateIdentifier = false;
                    String old = user.getServerIdentifier();
                    if ((old != null && identifier == null) || (old == null && identifier != null) || (old == null && identifier == null) || (!old.equals(identifier))) {
                        RedstoneGang.instance().user().updateIdentifier(user, identifier);
                        updateIdentifier = true;
                    }
                    PlayerJoinServerEvent join = new PlayerJoinServerEvent(p, false, updateIdentifier);
                    Bukkit.getPluginManager().callEvent(join);
                }
                log("" + entry.getGameClass().getSimpleName() + " loaded in " + (System.currentTimeMillis() - start) + "ms");
                if (update) {
                    RedstoneGang.instance().servers().changeGame(server.getGames().name());
                }
                return;
            } catch (HardDependencyNotFoundException e) {
                e.printStackTrace();
            } catch (CyclicalDependencyException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        server = null;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();

    }

    public void log(String msg) {
        System.out.println("[GameManager] " + msg);
    }

    public Server getServer() {
        return server;
    }

    public void unload() {
        if (server != null) {
            server.disable();
            log("Fechado jogo");
            server = null;
        }
    }
}
