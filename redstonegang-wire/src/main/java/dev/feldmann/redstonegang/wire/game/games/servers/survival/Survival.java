package dev.feldmann.redstonegang.wire.game.games.servers.survival;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.player.permissions.PermissionManager;
import dev.feldmann.redstonegang.common.player.permissions.PermissionServer;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.both.cashshop.CashShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import dev.feldmann.redstonegang.wire.game.base.addons.both.cooldown.PersistentCdAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.crafting.BlockCraftAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.DamageInfoAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shopintegration.ShopIntegrationAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmds;
import dev.feldmann.redstonegang.wire.game.base.addons.both.tell.TellAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.whitelistcmd.WhitelistCmdAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.HorseProtection;
import dev.feldmann.redstonegang.wire.game.base.addons.server.SignColorAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.antidupe.AntiDupeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.betterspawners.BetterSpawnersAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.ServerChatAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.home.HomeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.JobsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.multiservertp.MultiServerTpAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.pvp.PvPAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.shop.QuestShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.restart.ServerRestartAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.safetime.SafeTimeAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tosar.ResourcesFromAnimalsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.world.WorldControlAddon;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.KitsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.Notifications;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShopAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator.ElevatorAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ajuda.SurvivalAjudaAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.deathmessages.SurvivalDeathMessagesAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.respawn.SurvivalRespawnAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.scoreboard.SurvivalScoreboard;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.SurvivalStats;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.config.SurvivalConfigAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests.ExtraChests;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.oldertitle.OlderTitlesAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.statsView.StatsViewAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos.SurvivalTerrenos;
import dev.feldmann.redstonegang.wire.modulos.scoreboard.ScoreboardManager;
import dev.feldmann.redstonegang.wire.permissions.CmdPerm;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.utils.NMSUtils;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.world.WorldUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.InvSync;


public class Survival extends Server {


    public static Survival instance;

    public static final String database = "redstonegang_survival";

    @Override
    public void enable() {
        super.enable();
        instance = this;
        RedstoneGang.instance().db().createDatabaseIfNotExists(database);
        PermissionManager.defaultServer = PermissionServer.SV;
        registerCommand(new CmdPerm());
        addAddon(new SignColorAddon());
        addAddon(new ServerRestartAddon());
        addAddon(new ServerChatAddon(database));
        addAddon(new Notifications(database));
        addAddon(new CustomBlocksAddon(database, "customBlocks"));
        addAddon(new InvSync(database));
        addAddon(new ExtraChests());
//        addAddon(new CleanEntitiesAddon());
        addAddon(new ChestShopAddon(database));
        addAddon(new ElevatorAddon());
        addAddon(new PersistentCdAddon());
        addAddon(new KitsAddon(database));
        addAddon(new SurvivalConfigAddon());
        addAddon(new EconomyAddon(database, "money"));
        addAddon(new HomeAddon(database));
        addAddon(new BetterSpawnersAddon());
        addAddon(new TituloAddon(database, "STAFF"), new OlderTitlesAddon());
        addAddon(new ClanAddon(database));
        addAddon(new CashShopAddon(database));
        addAddon(new ResourcesFromAnimalsAddon());
        addAddon(new QuestAddon(this instanceof SurvivalTerrenos, database));
        addAddon(new QuestShopAddon(database));
        addAddon(new FerreiroAddon());
        addAddon(new SurvivalScoreboard());
        addAddon(new DamageInfoAddon());
        addAddon(new PvPAddon());
        addAddon(new SurvivalDeathMessagesAddon());
        addAddon(new CorreioAddon(database));
        addAddon(new JobsAddon(database));
        addAddon(new SurvivalStats());
        addAddon(new SafeTimeAddon(database));
        addAddon(new StatsViewAddon());
        addAddon(new BlockCraftAddon(
                Material.PISTON_BASE,
                Material.PISTON_STICKY_BASE,
                Material.BOAT,
                Material.HOPPER_MINECART
        ));
        addAddon(new MultiServerTpAddon(database));
        addAddon(new SurvivalRespawnAddon());
        addAddon(new AntiDupeAddon());
        addAddon(new HorseProtection());
        addAddon(new TellAddon());
        addAddon(new StaffCommandsAddon(true));
        addAddon(new WhitelistCmdAddon(database));
        addAddon(new SurvivalAjudaAddon());
        addAddon(new ShopIntegrationAddon());
        WorldUtils.removePlayerFiles();
        // Remove o mobspawner de itens bloquados do spigot
        NMSUtils.removeInvalidItem(Material.MOB_SPAWNER.getId());

        C.registerType(User.class, this::processPlayer);
        C.registerType(Player.class, this::processPlayer);
        a(WorldControlAddon.class).allowNaturalSpawn();
    }

    @Override
    public void lateEnable() {
        super.lateEnable();
        a(WorldControlAddon.class).limpaMobs();
    }


    public BungeeLocation getSpawn() {
        return a(SimpleCmds.class).getSpawn();
    }


    @Override
    public String getIdentifier() {
        return "survival";
    }

    private TextComponent processPlayer(Object ob, C.MsgVars vars) {
        User pl;
        if (ob instanceof Player) {
            pl = RedstoneGangSpigot.getPlayer(((Player) ob).getUniqueId());
        } else if (ob instanceof User) {
            pl = (User) ob;
        } else {
            return new TextComponent("null");
        }
        ClanMember cl = a(ClanAddon.class).getCache().getMember(pl.getId());
        ChatColor cor = vars.type != null ? (vars.playerCount > 0 ? vars.type.otherPlayerColor : vars.type.playerColor) : ChatColor.WHITE;
        ChatColor chatColorKey = ChatColor.YELLOW;
        ChatColor chatColorValue = ChatColor.WHITE;
        ComponentBuilder bu = new ComponentBuilder("");
        bu.append(pl.getNameWithPrefix()).append("\n");
        Group group = pl.permissions().getGroup();
        if (group != null && !group.isDefaultGroup()) {
            bu.append("§f§l" + group.getDisplayName() + "\n");
        }
        bu.append("Registrado: ")
                .color(chatColorKey)
                .append(DateUtils.formatDate(pl.getRegistred()))
                .color(chatColorValue)
                .append("\n")
                .append("Clan: ")
                .color(chatColorKey);
        if (cl.getClanTag() != null) {
            String clanCargo;
            if (cl.getRole() == ClanRole.LEADER) {
                clanCargo = "Líder";
            } else if (cl.getRole() == ClanRole.SUBLEADER) {
                clanCargo = "Sub-Líder";
            } else {
                clanCargo = "Membro";
            }
            bu.append(clanCargo + " ").color(chatColorValue).append(cl.getClan().getColorTag()).append("\n");
        } else {
            bu.append("Sem Clan").color(chatColorValue).append("\n");
        }
        bu.append("Dinheiro: ")
                .color(chatColorKey)
                .append(NumberUtils.convertToString((long) a(EconomyAddon.class).getCached(pl.getId())))
                .color(chatColorValue);
        return new TextComponent(
                new ComponentBuilder(pl.getName())
                        .color(cor)
                        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + pl.getName()))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, bu.create()))
                        .create());

    }

    @EventHandler

    public void join(PlayerJoinServerEvent ev) {
        User pl = RedstoneGangSpigot.getPlayer(ev.getPlayer().getUniqueId());
        ScoreboardManager.addToTeam(ev.getPlayer().getName(), ev.getPlayer().getDisplayName(), pl.getPrefix(), pl.getSuffix(), true);

    }


    public EconomyAddon money() {
        return a(EconomyAddon.class);
    }


    public LandAddon terrenos() {
        return a(LandAddon.class);
    }


    public Notifications notificacoes() {
        return a(Notifications.class);
    }

    @Override
    public void broadcast(TextComponent text) {
        RedstoneGang.instance.network().sendMessageLocal("svmsg", ComponentSerializer.toString(text));
    }

    @Override
    public void broadcast(TextComponent text, String permission) {
        RedstoneGang.instance.network().sendMessageLocal("svmsgperm", permission, ComponentSerializer.toString(text));
    }

    @EventHandler
    public void receivedMsg(NetworkMessageEvent ev) {
        if (ev.getId().equals("svmsg")) {
            String s = ev.get(0);
            BaseComponent[] parse = ComponentSerializer.parse(s);
            TextComponent t = new TextComponent(parse);
            super.broadcast(t);
        }
        if (ev.getId().equals("svmsgperm")) {
            String perm = ev.get(0);
            String s = ev.get(1);
            BaseComponent[] parse = ComponentSerializer.parse(s);
            TextComponent t = new TextComponent(parse);
            Bukkit.getConsoleSender().sendMessage(t.toLegacyText());
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(perm)) {
                    p.spigot().sendMessage(t);
                }
            }
        }


    }


}

