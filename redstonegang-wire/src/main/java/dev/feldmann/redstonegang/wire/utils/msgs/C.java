package dev.feldmann.redstonegang.wire.utils.msgs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.common.utils.msgs.Msg;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class C extends Msg {


    public static void initC() {
        defaultProcessType.put(ItemStack.class, C::processItemstack);
        defaultProcessType.put(Player.class, C::processOnlinePlayer);
    }

    public static void error(CommandSender sender, String msg, Object... obj) {
        send(sender, MsgType.ERROR, msg, obj);
    }

    public static void info(CommandSender sender, String msg, Object... obj) {
        send(sender, MsgType.INFO, msg, obj);
    }

    public static void send(CommandSender sender, boolean b, String msg, Object... obj) {
        send(sender, b ? MsgType.ON : MsgType.OFF, msg, obj);
    }

    public static void send(CommandSender sender, MsgType type, String msg, Object... obj) {
        if (sender instanceof Player) {
            ((Player) sender).spigot().sendMessage(msgText(type, msg, obj));
        } else {
            sender.sendMessage(msg(type, msg, obj));
        }
    }

    public static void broadcast(MsgType type, String msg, Object... obj) {
        TextComponent textComponent = msgText(type, msg, obj);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(textComponent);
        }
        Bukkit.getConsoleSender().sendMessage(textComponent.toLegacyText());
    }

    public static void permission(CommandSender player) {
        C.error(player, "Você não tem permissão para fazer isto");
    }

    public static String joinList(Iterable<String> strings, ChatColor... colors) {
        StringBuilder joined = new StringBuilder();
        int qtd = 0;
        for (String string : strings) {
            joined.append(colors[qtd % colors.length]);
            if (qtd > 0) {
                joined.append(" ");
            }
            joined.append(string);
            qtd++;
        }
        return joined.toString();
    }

    public static String joinList(Iterable<String> strings) {
        return joinList(strings, MsgType.INFO.baseColor, MsgType.INFO.numberColor);
    }


    private static TextComponent processItemstack(Object ob, MsgVars vars) {
        if (ob instanceof ItemStack) {
            String itname = LanguageHelper.getItemDisplayName((ItemStack) ob);
            if (((ItemStack) ob).getAmount() > 1) {
                itname = ((ItemStack) ob).getAmount() + "x " + itname;
            }
            TextComponent t = new TextComponent(itname);
            t.setColor(vars.type.numberColor);
            t.setHoverEvent(ItemConverter.hoverItem((ItemStack) ob));
            return t;
        }
        return new TextComponent(ob.toString());
    }

    private static TextComponent processOnlinePlayer(Object ob, MsgVars vars) {
        if (ob instanceof Player) {
            return processUser(RedstoneGangSpigot.getPlayer((Player) ob), vars);
        }
        return new TextComponent(ob.toString());
    }

    public static TextComponent processPlayer(Player p, boolean prefix, String customPrefix, String customSuffix, ChatColor cor) {
        MsgType t = new MsgType(ChatColor.WHITE, cor, cor, cor, cor, cor);
        MsgVars vars = new MsgVars(t, p);
        TextComponent tx;
        if (processType.containsKey(Player.class)) {
            tx = processType.get(Player.class).apply(p, vars);
        } else {
            tx = processUser(RedstoneGangSpigot.getPlayer(p), vars);
        }
        User rg = RedstoneGangSpigot.getPlayer(p);
        if (customPrefix != null) {
            TextComponent prtx = new TextComponent(TextComponent.fromLegacyText(customPrefix));
            prtx.addExtra(tx);
            tx = prtx;
        }

        if (prefix) {
            TextComponent prtx = new TextComponent(TextComponent.fromLegacyText(rg.getPrefix()));
            prtx.addExtra(tx);
            tx = prtx;
        }
        if (customSuffix != null) {
            TextComponent prtx = new TextComponent(TextComponent.fromLegacyText(customSuffix));
            tx.addExtra(prtx);

        }
        return tx;
    }


    public static void blank(Player p, int i) {
        for (int x = 0; x < i; x++) {
            p.sendMessage(" ");
        }
    }


}
