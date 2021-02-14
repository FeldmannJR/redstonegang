package dev.feldmann.redstonegang.wire.game.base.addons.both.clan;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.player.permissions.GroupOption;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.ArrayUtils;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.cmds.CmdClan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.event.ClanCheckFriendlyFire;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.integration.ClanChatChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.invites.ClanInvites;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners.ClanListeners;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners.ClanMessages;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners.TitleIntegration;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.ServerChatAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.utils.msgs.C;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Dependencies(soft = ServerChatAddon.class)
public class ClanAddon extends Addon {

    public static final long TEMPO_TROCA_TAG = 1000 * 60 * 60 * 24 * 5;
    public static final long TEMPO_TROCA_NOME = 1000 * 60 * 60 * 24 * 5;

    public final int PRECO_CRIAR = 1500;
    public static MsgType CLAN_CHAT = new MsgType(ChatColor.GRAY, ChatColor.BLUE, ChatColor.BLUE, ChatColor.WHITE, ChatColor.YELLOW, ChatColor.BLUE);
    public static MsgType CLAN_INFO = new MsgType(ChatColor.AQUA, ChatColor.WHITE, ChatColor.BLUE, ChatColor.BLUE, ChatColor.YELLOW, ChatColor.BLUE);


    public boolean canUseSuffix;

    public String databaseName;
    ClanCache cache;
    ClanMessages messages;
    ClanInvites invites;

    public static String VIEW_CLAN_MESSAGES = "rg.clan.staff.spy";
    public static String CAN_USE_CLAN_SUFFIX = "rg.clan.suffix";

    public BooleanConfig STAFF_CHAT_CONFIG;
    public BooleanConfig SUFFIX_CONFIG;
    public PermissionDescription USE_COLOR_IN_TAG;

    public GroupOption MAX_MEMBERS = new GroupOption("max clan members", "clan_maxMembers", "Maximo de jogadores no clan criado", 5);

    public ClanChatChannel channel;

    public ClanAddon(String databaseName) {
        this(databaseName, false);
    }

    public ClanAddon(String databaseName, boolean suffix) {
        this.databaseName = databaseName;
        this.canUseSuffix = suffix;
    }


    @Override
    public void onEnable() {
        cache = new ClanCache(this);
        invites = new ClanInvites(this);

        //Configs
        STAFF_CHAT_CONFIG = new BooleanConfig("ad_clan_" + getServer().getIdentifier() + "_spy", false);
        SUFFIX_CONFIG = new BooleanConfig("ad_clan_" + getServer().getIdentifier() + "_usesuffix", true);
        addConfig(STAFF_CHAT_CONFIG);
        addConfig(SUFFIX_CONFIG);
        addOption(MAX_MEMBERS);
        //Listeners
        messages = new ClanMessages(this);
        registerListener(messages);
        registerListener(new ClanListeners(this));
        registerListener(new TitleIntegration(this));
        //Commands
        registerCommand(new CmdClan(this));

        //Permissions
        USE_COLOR_IN_TAG = new PermissionDescription("Usar Cor Tag Clan", generatePermission("usecolorstag"), "Pode usar cores na tag do clan");
        addOption(new PermissionDescription("Ver mensagem dos outros", VIEW_CLAN_MESSAGES, "espionar as mensagens dos clans"));
        addOption(new PermissionDescription("Clan Suffixes", CAN_USE_CLAN_SUFFIX, "permite que os membros do clan desta pessoa possam usar o suffixo no nome"));

        //Cria um canal de chat do clan
        if (getServer().hasAddon(ServerChatAddon.class)) {
            channel = new ClanChatChannel(this);
            a(ServerChatAddon.class).registerChannel(channel);
        }
        //Registra um objeto de clan no chat, pra poder passar o mouse por cima e mostrar info
        C.registerType(Clan.class, this::processClanChat);
    }


    private static final List<String> banned = Arrays.asList(
            "admin",
            "mod",
            "vip",
            "ceo",
            "dev",
            "ytb",
            "staff"
    );


    private TextComponent processClanChat(Object ob, C.MsgVars vars) {
        if (ob instanceof Clan) {
            Clan c = (Clan) ob;
            BaseComponent[] t = TextComponent.fromLegacyText(c.getColorTag());
            TextComponent tx = new TextComponent(t);
            BaseComponent[] desc = new ComponentBuilder(c.getName())
                    .color(ChatColor.AQUA)
                    .append("\n")
                    .append("Fundado: ")
                    .color(ChatColor.BLUE)
                    .append(DateUtils.formatDate(c.getFounded()))
                    .color(ChatColor.WHITE)
                    .append("\n")
                    .append("Fundador: ")
                    .color(ChatColor.BLUE)
                    .append(RedstoneGangSpigot.getPlayer(c.getFounder()).getName())
                    .color(ChatColor.WHITE)
                    .create();
            tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, desc));
            return tx;
        }
        return new TextComponent("");

    }

    public boolean canAttack(Player attacker, Player attacked) {
        if (attacker.hasMetadata("NPC") || attacked.hasMetadata("NPC")) return true;

        Clan c1 = getCache().getMember(attacked).getClan();
        Clan c2 = getCache().getMember(attacker).getClan();
        if (c1 != null && c2 != null) {
            if (c1.equals(c2)) {
                boolean ff = c1.getProps().isFf();
                if (!ff) {
                    ClanCheckFriendlyFire event = new ClanCheckFriendlyFire(attacked, attacked);
                    Bukkit.getPluginManager().callEvent(event);
                    return event.canAttack();
                }
            }
        }
        return true;

    }


    public String isTagValid(Player p, String tag) {
        String colorless = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', tag));
        if (tag.length() > 15) {
            return "Tag muito grande!";
        }
        if (banned.contains(colorless.toLowerCase())) {
            return "Está tag esta proibida de ser usada!";
        }
        if (tag.contains("&")) {
            if (p != null && !getUser(p).hasPermission(USE_COLOR_IN_TAG)) {
                return "Você não pode usar cores na tag!";
            }
            for (String s : new String[]{"o", "m", "n", "k", "l", "r"}) {
                if (tag.contains("&" + s) || tag.contains("&" + s.toUpperCase())) {
                    return "Você não pode usar a cor " + s + " na sua tag!";
                }
            }
        }
        if (colorless.length() < 3 || colorless.length() > 5) {
            return "A tag pode ter de 3 a 5 letras/numeros!";
        }
        if (!colorless.matches("^[a-zA-Z0-9]*")) {
            return "Você só pode usar numeros e letras na tag!";
        }
        return null;
    }


    public String isNameValid(String nome) {
        if (nome.contains("&")) {
            return "O nome não pode conter cores!";
        }
        if (nome.length() < 5 || nome.length() > 32) {
            return "O nome precisa ter entre 5 e 32 letras/numeros!";
        }

        if (!nome.matches("^[a-zA-Z0-9_ ]*")) {
            return "Você só pode usar numeros e letras no nome!";
        }
        return null;
    }


    public ClanCache getCache() {
        return cache;
    }

    public ClanInvites getInvites() {
        return invites;
    }


    public String getIdentifier() {
        return getServer().getIdentifier();
    }

    public void network(Object... ob) {
        ob = ArrayUtils.insertFirst(ob, "clan", getIdentifier());
        network().sendMessage(ob);
    }

    public void networkLocal(Object... ob) {
        ob = ArrayUtils.insertFirst(ob, "clan", getIdentifier());
        network().sendMessageLocal(ob);
    }
}
