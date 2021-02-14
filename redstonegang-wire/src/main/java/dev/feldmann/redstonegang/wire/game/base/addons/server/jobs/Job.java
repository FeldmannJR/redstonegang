package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnActionPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.DropOnBreakPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.JobPerk;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.OnXpPerk;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.events.PlayerGainJobXpEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks.*;
import dev.feldmann.redstonegang.wire.modulos.BlockUtils;
import dev.feldmann.redstonegang.wire.utils.Cores;
import dev.feldmann.redstonegang.wire.utils.items.Fireworks;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.player.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.HashSet;

public abstract class Job extends BaseListener {

    private String dbId;
    private String nome;
    private ItemStack icone;
    private JobsAddon addon;
    private HashSet<JobPerk> perks = new HashSet<>();


    public Job(String dbId, String nome, ItemStack icone) {
        this.dbId = dbId;
        this.nome = nome;
        this.icone = icone;
    }

    public void setAddon(JobsAddon addon) {
        this.addon = addon;
    }

    public Field<Long> getDatabaseField() {
        return DSL.field(DSL.name("job_" + dbId), SQLDataType.BIGINT.defaultValue(0L));
    }

    public int getLevel(long xp) {
        double mp = getMultiplicador();
        return (int) (int) Math.floor(0.5 + (Math.sqrt(1d + ((8d * xp) / mp))) / 2d);
    }

    public long getRequiredXp(int lvl) {
        double mp = getMultiplicador();
        return (long) Math.floor((((lvl * (lvl - 1))) * mp) / 2d);
    }

    public int getMultiplicador() {
        return 90;
    }

    public long getXp(Player p) {
        return addon.getJobPlayer(addon.getPlayerId(p)).getXp(this);
    }

    public int getLevel(Player p) {
        return addon.getJobPlayer(addon.getPlayerId(p)).getLevel(this);
    }


    public void addXp(Player p, int quanto) {

        boolean upou = getLevel(getXp(p)) < getLevel(getXp(p) + quanto);
        long old = getXp(p);
        addon.addXp(addon.getPlayerId(p), this, quanto);
        int lvl = getLevel(p);
        if (upou) {
            ActionBar.sendActionBar(p, C.msg(JobsAddon.MSG, "Você upou para o level %% de %%!", lvl, nome));
            C.send(p, JobsAddon.MSG, "Você upou para o level %% de %%!", lvl, nome);
            if (JobsAddon.coresTitulos.containsKey(lvl)) {
                C.send(p, JobsAddon.MSG, "Você agora pode usar o titulo %% com a cor " + Cores.getNome(JobsAddon.coresTitulos.get(lvl)), getTitulo());
            }
            Fireworks.randomFirework(p, RandomUtils.randomInt(3, 5));
            if (lvl % 10 == 0) {
                addon.getServer().broadcast(C.msgText(JobsAddon.MSG, "%% upou para o nivel %% de %%!", p, lvl, nome));
            }
        } else {
            long tem = getXp(p) - getRequiredXp(lvl);
            long toNextLevel = getRequiredXp(lvl + 1) - getRequiredXp(lvl);
            ActionBar.sendActionBar(p, C.msg(JobsAddon.MSG, "+%% em %% | %% / %%", quanto, nome, tem, toNextLevel));
        }
        Wire.callEvent(new PlayerGainJobXpEvent(p, this, old, quanto, upou));
        PERK:
        for (JobPerk perk : perks) {
            if (perk.getLevel() <= getLevel(p) && !perk.isCooldown(p)) {
                if (perk instanceof OnXpPerk && perk.roll()) {
                    if (((OnXpPerk) perk).gainXp(p)) {
                        perk.addCooldown(p);
                        break PERK;
                    }
                }
            }
        }


    }

    public void addPerk(JobPerk perk) {
        perks.add(perk);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void blockBreakJob(BlockBreakEvent ev) {
        if (this instanceof BreakBlockJob) {
            BreakBlockJob b = (BreakBlockJob) this;
            if (!b.canGainXp(ev.getPlayer(), ev.getBlock())) {
                return;
            }
            boolean playerPlaced = BlockUtils.isPlayerPlaced(ev.getBlock());
            MaterialData data = new MaterialData(ev.getBlock().getType(), ev.getBlock().getData());
            for (BlockXpInfo xpInfo : b.getBlocksXps()) {
                if (playerPlaced && !xpInfo.isAllowPlayerPlaced()) {
                    continue;
                }
                MaterialData md = xpInfo.getData();
                if (md.getItemType() == data.getItemType()) {
                    if (md instanceof ExactMaterialData) {
                        if (md.getData() != data.getData()) {
                            continue;
                        }
                    }
                    addXp(ev.getPlayer(), xpInfo.getXp());
                    callActions(ev.getPlayer(), DropOnBreakPerk.class, ev.getBlock());
                    break;
                }
            }


        }
    }

    public <T> void callActions(Player p, Class<? extends DropOnActionPerk<T>> classe, T t) {
        PERK:
        for (JobPerk perk : perks) {
            if (perk.getLevel() <= getLevel(p) && !perk.isCooldown(p)) {
                if (classe.isAssignableFrom(perk.getClass()) && perk.roll()) {
                    if (((DropOnActionPerk<T>) perk).onAction(p, t)) {
                        perk.addCooldown(p);
                        break PERK;
                    }
                }
            }
        }
    }


    public JobsAddon getAddon() {
        return addon;
    }

    public ItemStack getIcone() {
        return icone.clone();
    }

    public String getNome() {
        return nome;
    }

    public abstract String getTitulo();

    public abstract String getDesc();

    public HashSet<JobPerk> getPerks() {
        return perks;
    }

    public String getDbId() {
        return dbId;
    }
}
