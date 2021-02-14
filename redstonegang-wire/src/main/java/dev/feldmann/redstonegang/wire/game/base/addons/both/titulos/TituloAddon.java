package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.cmds.AddTitulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.cmds.Titulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events.PlayerGetTitlesEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.menu.EscolheTitulo;
import dev.feldmann.redstonegang.wire.game.base.apis.customname.AboveNameAPI;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ConcurrentHashMap;

@Dependencies(apis = AboveNameAPI.class)
public class TituloAddon extends Addon {

    private String database;
    private ConcurrentHashMap<Integer, TituloPlayer> titulos = new ConcurrentHashMap<>();
    private TituloDB db;
    private String staffTitle;

    public TituloAddon(String database, String staff) {
        this.database = database;
        this.staffTitle = staff;
    }

    public TituloAddon(String database) {
        this(database, null);
    }

    @Override
    public void onEnable() {
        this.db = new TituloDB(database);
        registerCommand(new Titulo(this));
        registerCommand(new AddTitulo(this));
    }

    public void openEditor(Player p, BackButton backButton) {
        new EscolheTitulo(getUser(p).getId(), this, backButton).open(p);
    }

    public TituloPlayer getTitulos(int pid) {
        if (!titulos.containsKey(pid)) {
            titulos.put(pid, db.loadTitulos(pid));
        }
        return titulos.get(pid);
    }


    public void addTitulo(int pid, String titulo, String cor) {
        TituloPlayer t = getTitulos(pid);
        TituloCor tcor = t.addTitle(titulo, cor);
        if (tcor != null) {
            db.addTitulo(tcor);
        }
    }

    public void removeTitulo(int pid, TituloCor cor) {
        if (cor.getParent().isVirtual()) {
            return;
        }
        TituloPlayer t = getTitulos(pid);
        if (t.has(cor)) {
            t.removeTitle(cor);
        }
        db.removeTitulo(cor);
    }

    public boolean setAtivo(int pid, TituloCor t) {
        TituloPlayer ts = getTitulos(pid);
        if (ts.has(t)) {
            db.setActive(pid, t);
            ts.setAtivo(t);
            updateTitulo(getOnlinePlayer(pid), t);
            return true;
        }
        return false;

    }

    public void updateTitulo(Player p, TituloCor cor) {
        api(AboveNameAPI.class).setaNome(p, cor.getCor() + cor.getParent().getProcessedName());
    }

    @EventHandler
    public void join(PlayerJoinEvent ev) {
        int pid = getPlayerId(ev.getPlayer());
        runAsync(() -> getTitulos(pid), (t) -> {
            if (ev.getPlayer().isOnline() && t.getAtivo() != null) {
                updateTitulo(ev.getPlayer(), t.getAtivo());
            }
        });
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent ev) {
        titulos.remove(getPlayerId(ev.getPlayer()));
    }

    @EventHandler
    public void checkStaff(PlayerGetTitlesEvent ev) {
        if (staffTitle == null) return;
        User pl = RedstoneGangSpigot.getPlayer(ev.getOwner());
        if (pl.isStaff()) {
            ev.addTitle(new StaffTitle(ev.getOwner()));
        }
    }
}

