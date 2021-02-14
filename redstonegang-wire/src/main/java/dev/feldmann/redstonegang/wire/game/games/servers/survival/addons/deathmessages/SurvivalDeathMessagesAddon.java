package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.deathmessages;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.EnumConfig;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.DeathInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.HitInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.HitType;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.PlayerCustomDeathEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SurvivalDeathMessagesAddon extends Addon {

    public EnumConfig<MensagemMorte> DEATH_MESSAGES;
    private MsgType DEATH = new MsgType("§4[§fMorte§4] ", ChatColor.WHITE, ChatColor.RED, ChatColor.RED, ChatColor.YELLOW, ChatColor.YELLOW, ChatColor.GOLD);

    public enum MensagemMorte {
        Desligado,
        Global,
        Clan,
        Local
    }

    @Override
    public void onEnable() {
        DEATH_MESSAGES = new EnumConfig<MensagemMorte>("ad_deathmessages_" + getServer().getIdentifier(), MensagemMorte.Global);
        addConfig(DEATH_MESSAGES);
    }

    @EventHandler
    public void custom(PlayerCustomDeathEvent ev) {
        DeathInfo morte = ev.getInfo();
        TextComponent message = getMessageMorte(ev.getPlayer(), morte, true);
        User killer = morte.getLastPlayerDamage() != null ? morte.getLastPlayerDamage().getUserDamager() : null;
        boolean isPvp = morte.getLastPlayerDamage() != null;
        for (Entity e : ev.getPlayer().getNearbyEntities(64, 64, 64)) {
            if (e.getType() == EntityType.PLAYER) {
                Player pOn = (Player) e;
                if (pOn == ev.getPlayer()) continue;
                if (pOn.hasMetadata("NPC")) continue;
                MensagemMorte x = getUser(pOn).getConfig(DEATH_MESSAGES);
                if (x == MensagemMorte.Local) {
                    pOn.spigot().sendMessage(message);
                }
            }
        }
        ev.getPlayer().spigot().sendMessage(message);
        Clan deathClan = a(ClanAddon.class).getCache().getMember(ev.getPlayer()).getClan();
        Clan killerClan = killer != null ? a(ClanAddon.class).getCache().getMember(killer.getId()).getClan() : null;
        network().sendMessage(
                "deathmessage",
                getServer().getIdentifier(),
                isPvp,
                ComponentSerializer.toString(message),
                deathClan != null ? deathClan.getTag() : "-1",
                killerClan != null ? killerClan.getTag() : "-1"
        );
        handleGlobalDeathMessage(ev.getPlayer(), isPvp, deathClan, killerClan, message);
    }

    public void handleGlobalDeathMessage(Player death, boolean pvp, Clan deathClan, Clan killerClan, BaseComponent... message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p == death) continue;
            MensagemMorte x = getUser(p).getConfig(DEATH_MESSAGES);
            if (x == MensagemMorte.Global && pvp) {
                p.spigot().sendMessage(message);
                continue;
            }
            if (x == MensagemMorte.Clan) {
                Clan clan = a(ClanAddon.class).getCache().getMember(p).getClan();
                if (clan != null && (clan.equals(deathClan) || clan.equals(killerClan))) {
                    p.spigot().sendMessage(message);
                }
            }
        }
    }

    @EventHandler
    public void socket(NetworkMessageEvent ev) {
        if (ev.is("deathmessage")) {
            if (getServer().getIdentifier().equals(ev.get(0))) {
                boolean isPvp = ev.get(1).equalsIgnoreCase("true");
                String message = ev.get(2);
                String deathClan = ev.get(3);
                String killerClan = ev.get(4);
                ClanAddon clans = a(ClanAddon.class);
                handleGlobalDeathMessage(
                        null,
                        isPvp,
                        deathClan.equals("-1") ? null : clans.getCache().getClan(deathClan),
                        killerClan.equals("-1") ? null : clans.getCache().getClan(killerClan),
                        ComponentSerializer.parse(message));
            }
        }

    }

    public TextComponent d(String message, Object... objs) {
        return C.msgText(DEATH, message, objs);
    }

    public TextComponent getMessageMorte(Player p, DeathInfo m, boolean nopvp) {
        if (m != null && m.getLastPlayerDamage() != null) {
            HitInfo last = m.getLastPlayerDamage();
            String[] podeser = new String[]{"matou", "aniquilou", "dizimou", "humilhou", "bateu em", "deu um sumiço em", "detonou", "fuzilou", "acabou com"};
            String matou = podeser[new Random().nextInt(podeser.length)];
            User damager = last.getUserDamager();
            if (last.getType() == HitType.PHYSICAL) {
                if (last.getItem() == null) {
                    return d("%% " + matou + " %%!", damager, p);
                } else {
                    return d("%% " + matou + " %% com %%!", damager, p, last.getItem());
                }

            }
            switch (last.getType()) {
                case ARROW:
                    return d("%% " + matou + " %% com seu %%!", damager, p, last.getItem() != null ? last.getItem() : new ItemStack(Material.BOW));
                case MOB:
                    return d("%% morreu para um lacaio de %%!", p, damager);
                case EGG:
                    return d("%% matou %% com uma Ovada!", damager, p);
                case SNOW_BALL:
                    return d("%% acertou uma bola de neve na cabeça de %%!", damager, p);
                case THORNS:
                    return d("%% matou %% com sua armadura de espinhos!", damager, p);
                case POISON:
                    return d("%% matou %% envenenado!", damager, p);
                case POTION:
                    return d("%% matou %% com uma poção de dano!", damager, p);
                case EXPLOSION:
                    return d("%% explodiu %% com uma TNT!", damager, p);
                case CREEPER:
                    return d("%% explodiu %% com um Creeper!", damager, p);

            }
            return d("%% " + matou + " %% !", damager, p);
        }
        if (nopvp)
            return getMessageMorteNoPvP(p, m);
        return null;
    }

    public TextComponent withoutDamager(Player p, String oq) {
        return C.msgText(this.DEATH, "%% " + oq, p);
    }

    public TextComponent getMessageMorteNoPvP(Player p, DeathInfo m) {
        if (m != null && m.getLastHit() != null) {
            HitInfo last = m.getLastHit();
            if (last.getDamager() != null) {
                if (last.getType() == HitType.PHYSICAL) {
                    if (last.getItem() != null) {
                        return C.msgText(DEATH, "%% foi espancado por um(a) %% com %%!", p, LanguageHelper.getEntityName(last.getDamager()), last.getItem());
                    }
                    return C.msgText(DEATH, "%% foi espancado por um(a) %%!", p, LanguageHelper.getEntityName(last.getDamager()));
                }
                if (last.getType() == HitType.ARROW) {
                    return C.msgText(DEATH, "%% levou uma flechada no joelho!", p);
                }
            }
            switch (last.getType()) {
                case CACTUS:
                    return withoutDamager(p, "se jogou em um cactus!");
                case DROWN:
                    return withoutDamager(p, "se afogou!");
                case FALLING_BLOCK:
                    return withoutDamager(p, "foi esmagado por um bloco!");
                case FIRE:
                    return withoutDamager(p, "virou churrasquinho!");
                case VOID:
                    return withoutDamager(p, "morreu no limbo!");
                case FALL:
                    return withoutDamager(p, "caiu de um lugar alto e morreu!");
                case POTION:
                    if (last.getDamager() != null && last.getDamager() instanceof Witch) {
                        return withoutDamager(p, "morreu para uma Bruxa!");
                    }
                case CREEPER:
                    return withoutDamager(p, "foi explodido por um creeper!");
                case EXPLOSION:
                    return withoutDamager(p, "explodiu em pedacinhos!");
            }
        }
        return withoutDamager(p, "morreu!");
    }
}
