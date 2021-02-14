package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.cmds.CmdJobs;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.agricultor.AgricultorJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.alquimia.AlquimiaJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.encantador.EncantadorJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.escavador.EscavadorJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.lenhador.LenhadorJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.minerador.MineradorJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.pecuarista.PecuaristaJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.defaultJobs.pescador.PescadorJob;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.menus.JobVerPlayerMenu;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.ranks.JobRanks;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.titles.TitleIntegration;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import io.netty.util.internal.ConcurrentSet;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


import java.util.HashMap;
import java.util.HashSet;

@Dependencies(soft = TituloAddon.class)
public class JobsAddon extends Addon {


    public static MsgType MSG = new MsgType("§5[Jobs] ", ChatColor.WHITE, ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, ChatColor.GOLD, ChatColor.GOLD, ChatColor.GOLD);
    public static MsgType BONUS = new MsgType("§5[Jobs] ", ChatColor.GOLD, ChatColor.WHITE, ChatColor.WHITE, ChatColor.RED, ChatColor.RED, ChatColor.GOLD);
    public static HashMap<Integer, ChatColor> coresTitulos = new HashMap<>();
    public static final int lowestColor = 40;

    static {
        coresTitulos.put(40, ChatColor.YELLOW);
        coresTitulos.put(60, ChatColor.WHITE);
        coresTitulos.put(80, ChatColor.RED);
        coresTitulos.put(100, ChatColor.LIGHT_PURPLE);
        coresTitulos.put(110, ChatColor.GREEN);
        coresTitulos.put(120, ChatColor.GRAY);
        coresTitulos.put(130, ChatColor.GOLD);
        coresTitulos.put(140, ChatColor.DARK_RED);
        coresTitulos.put(150, ChatColor.DARK_PURPLE);
        coresTitulos.put(160, ChatColor.DARK_GREEN);
        coresTitulos.put(170, ChatColor.DARK_GRAY);
        coresTitulos.put(180, ChatColor.DARK_BLUE);
        coresTitulos.put(190, ChatColor.DARK_AQUA);
        coresTitulos.put(200, ChatColor.BLUE);
        coresTitulos.put(210, ChatColor.BLACK);
        coresTitulos.put(220, ChatColor.AQUA);
    }


    private String databaseName;
    private JobDB db;
    private JobRanks ranks;

    HashSet<Job> jobs = new HashSet();
    HashMap<Integer, JobPlayer> playerCache = new HashMap<>();

    public JobsAddon(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void onEnable() {
        ranks = new JobRanks(this);
        db = new JobDB(databaseName, this);
        registerCommand(new CmdJobs(this));

        //Se tiver o addon de titulos bota os titulos tbm
        if (getServer().hasAddon(TituloAddon.class)) {
            registerListener(new TitleIntegration(this));
        }
    }

    @Override
    public void onStart() {
        addJob(new LenhadorJob());
        addJob(new AgricultorJob());
        addJob(new PecuaristaJob());
        addJob(new PescadorJob());
        addJob(new AlquimiaJob());
        addJob(new EscavadorJob());
        addJob(new MineradorJob());
        addJob(new EncantadorJob());
        ranks.updateAll();
    }

    public void addJob(Job job) {
        if (enabled) {
            job.setAddon(this);
            jobs.add(job);
            db.addColumn(job);
            registerListener(job);
        } else {
            throw new RuntimeException("Addon não está habilitado!");
        }
    }


    public JobPlayer getJobPlayer(int playerId) {
        if (!playerCache.containsKey(playerId)) {
            playerCache.put(playerId, db.loadPlayer(playerId));
        }
        return playerCache.get(playerId);
    }

    public void addXp(int playerId, Job job, int quanto) {
        if (playerCache.containsKey(playerId)) {
            getJobPlayer(playerId).addXp(job, quanto);
        }
        db.addXp(playerId, job, quanto);
    }

    public HashSet<Job> getJobs() {
        return jobs;
    }


    public JobDB getDb() {
        return db;
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.MIN_5) {
            if (ranks != null)
                ranks.updateAll();
        }
    }

    public JobRanks getRanks() {
        return ranks;
    }

    ConcurrentSet<Player> opening = new ConcurrentSet<>();

    public boolean openPlayer(Player p, User rgpl) {
        if (opening.contains(p)) {
            return false;
        }
        final int plId = rgpl.getId();
        opening.add(p);
        runAsync(() -> getDb().loadPlayer(plId), (jobPlayer -> {
            opening.remove(p);
            new JobVerPlayerMenu(this, jobPlayer).open(p);
        }));
        return true;
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        playerCache.remove(getPlayerId(ev.getPlayer()));
    }
}
