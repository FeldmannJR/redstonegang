package dev.feldmann.redstonegang.common.utils.msgs;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Msg {


    protected static HashMap<String, BiFunction<Object, MsgVars, TextComponent>> tokens = new HashMap<>();
    protected static HashMap<Class, BiFunction<Object, MsgVars, TextComponent>> processType = new HashMap<>();
    protected static HashMap<Class, BiFunction<Object, MsgVars, TextComponent>> defaultProcessType = new HashMap<>();

    protected static void init() {
        if (tokens.isEmpty()) {
            tokens.put("##", Msg::processMoney);
            tokens.put("%%", Msg::processNormal);
            tokens.put("%cmd%", Msg::processCmd);
            tokens.put("%url%", Msg::processUrl);
            defaultProcessType.put(User.class, Msg::processUser);
        }
    }

    public static void registerType(Class type, BiFunction<Object, MsgVars, TextComponent> fun) {
        processType.put(type, fun);
    }

    public static void registerToken(String tok, BiFunction<Object, MsgVars, TextComponent> function) {
        tokens.put(tok, function);
    }

    private static BiFunction<Object, MsgVars, TextComponent> findProcessor(Object obj, HashMap<Class, BiFunction<Object, MsgVars, TextComponent>> map) {
        for (Class c : map.keySet()) {
            if (c.isInstance(obj)) {
                return map.get(c);
            }
        }
        return null;
    }

    public static void info(User user, String msg, Object... obj) {
        send(user, MsgType.INFO, msg, obj);
    }

    public static void error(User user, String msg, Object... obj) {
        send(user, MsgType.ERROR, msg, obj);
    }

    public static void send(User user, MsgType type, String msg, Object... obj) {
        user.sendMessage(msgText(type, msg, obj));
    }

    public static String msg(MsgType type, String msg, Object... obj) {
        return msgText(type, msg, obj).toLegacyText();
    }

    public static TextComponent msgText(MsgType type, String msg, Object... obj) {
        init();
        msg = msg.replace("$$", "##");
        return transform(msg, new MsgVars(type, obj));
    }


    public static String itemDesc(String msg, Object... obj) {
        return msg(MsgType.ITEM_DESC, msg, obj);
    }

    public static String item(String msg, Object... obj) {
        return msg(MsgType.ITEM, msg, obj);
    }

    public static String sell(String msg, Object... obj) {
        return msg(MsgType.SELL, msg, obj);
    }

    public static String buy(String msg, Object... obj) {
        return msg(MsgType.BUY, msg, obj);
    }


    private static String firstToken(String msg) {
        String first = null;
        for (String tok : tokens.keySet()) {
            if (!msg.contains(tok)) {
                continue;
            }
            if (first == null || msg.indexOf(tok) < msg.indexOf(first)) {
                first = tok;
            }
        }
        return first;

    }

    private static TextComponent transform(String msg, MsgVars obj) {
        TextComponent base;
        if (obj.type != null && obj.type.prefix != null) {
            base = new TextComponent(TextComponent.fromLegacyText(obj.type.prefix));
        } else {
            base = new TextComponent("");
        }
        TextComponent last = base;
        MSG:
        while (msg != null) {
            String tok = firstToken(msg);
            if (tok == null) {
                if (obj.type != null) {
                    base.addExtra(getColored(msg, obj.type.baseColor));
                } else {
                    base.addExtra(new TextComponent(TextComponent.fromLegacyText(msg)));
                }
                break;
            }
            String[] split = breakString(msg, tok);
            //Não achou nenhum token
            if (split == null) {
                continue;
            }


            if (split[0] != null) {
                if (obj.type != null) {
                    base.addExtra(getColored(split[0], obj.type.baseColor));
                } else {
                    TextComponent extra = new TextComponent(TextComponent.fromLegacyText(split[0]));
                    base.addExtra(extra);
                    last = extra;

                }
            }
            TextComponent next = processNext(tok, obj);
            if (obj.type == null) {
                copy(getLast(last), next);

            }
            base.addExtra(next);
            msg = split[1];
        }
        return base;
    }

    private static void copy(BaseComponent from, BaseComponent to) {
        forEachExtra(to, (c) -> {
            c.setColor(null);
        });
        to.setColor(from.getColor());
        to.setBold(from.isBold());
        to.setStrikethrough(from.isStrikethrough());
        to.setObfuscated(from.isObfuscated());
        to.setUnderlined(from.isUnderlined());
    }

    private static void forEachExtra(BaseComponent component, Consumer<BaseComponent> function) {
        if (component == null || component.getExtra() == null) return;
        for (BaseComponent extra : component.getExtra()) {
            function.accept(extra);
            forEachExtra(extra, function);
        }


    }

    private static BaseComponent getLast(TextComponent component) {
        BaseComponent last = component;
        while (last.getExtra() != null && last.getExtra().size() != 0) {
            last = last.getExtra().get(component.getExtra().size() - 1);
        }
        return last;
    }


    private static TextComponent processNext(String token, MsgVars vars) {
        if (vars.msgIndex >= vars.object.length) {
            return new TextComponent("");
        }
        int index = vars.msgIndex;
        vars.msgIndex++;
        return processObject(token, vars.object[index], vars);
    }

    private static TextComponent processMoney(Object ob, MsgVars vars) {
        String value = ob.toString();
        if (ob instanceof String) {
            value = ob + " moedas";
        }
        if (ob instanceof Integer || ob instanceof Float || ob instanceof Double || ob instanceof Long) {
            if (ob instanceof Float || ob instanceof Double) {
                if (((double) ob == Math.floor((double) ob)) && !Double.isInfinite((double) ob)) {
                    Long la = Long.valueOf(((Double) ob).longValue());
                    value = NumberUtils.convertToString(la);
                } else {

                    Double d = NumberUtils.round((double) ob, 2);
                    value = "" + String.format("%.1f", d);
                }
            } else {
                value = ob.toString();
            }
            Number number = (Number) ob;
            value += " moeda";
            if (number.intValue() != 0) {
                value += "s";
            }
        }
        return getColored(value, vars.type != null ? vars.type.moneyColor : null);

    }

    private static TextComponent processNormal(Object ob, MsgVars vars) {
        BiFunction<Object, MsgVars, TextComponent> processor = findProcessor(ob, processType);
        if (processor != null) {
            return processor.apply(ob, vars);
        }
        processor = findProcessor(ob, defaultProcessType);
        if (processor != null) {
            return processor.apply(ob, vars);
        }
        if (ob instanceof Double || ob instanceof Float) {
            String value = "" + NumberUtils.round((double) ob, 2);
            return getColored(value, vars.type != null ? vars.type.numberColor : null);
        }

        if (ob instanceof String || ob instanceof Integer || ob instanceof Long) {
            return getColored(ob.toString(), vars.type != null ? vars.type.numberColor : null);
        }

        return new TextComponent("");

    }


    private static TextComponent processObject(String token, Object ob, MsgVars vars) {
        return tokens.get(token).apply(ob, vars);
    }

    private static TextComponent processCmd(Object o, MsgVars msgVars) {
        TextComponent tx = new TextComponent(o.toString());
        if (msgVars.type != null) {
            tx.setColor(msgVars.type.numberColor);
        }
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, o.toString()));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Clique aqui para executar!").color(ChatColor.WHITE).create()));
        return tx;
    }

    private static TextComponent processUrl(Object o, MsgVars msgVars) {
        TextComponent tx;
        if (msgVars.type == null) {
            tx = new TextComponent(TextComponent.fromLegacyText(o.toString()));
        } else {
            tx = new TextComponent(o.toString());
        }
        if (msgVars.type != null) {
            tx.setColor(msgVars.type.numberColor);
        }
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, o.toString()));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Clique aqui para abrir o link!").color(ChatColor.WHITE).create()));
        return tx;
    }

    public static TextComponent processUser(Object ob, MsgVars vars) {
        if (ob instanceof User) {
            User pl = (User) ob;
            ChatColor color = null;
            if (vars.type != null) {
                color = vars.type.otherPlayerColor;
                if (vars.playerCount == 0) {
                    color = vars.type.playerColor;
                }
            }
            vars.playerCount++;
            TextComponent tc = new TextComponent(pl.getDisplayName());
            tc.setColor(color);
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("ID: " + pl.getId()).color(ChatColor.YELLOW).create()));
            return tc;
        }
        return new TextComponent(ob.toString());
    }

    private static TextComponent getColored(String text, ChatColor color) {
        TextComponent component = new TextComponent(text);
        if (color != null) {
            component.setColor(color);
        }
        return component;
    }

    public static String[] breakString(String msg, String token) {
        if (msg == null || msg.isEmpty() || !msg.contains(token)) return null;
        String[] split = msg.split(token, 2);
        if (split.length == 2) {
            return split;
        }
        if (split.length == 1) {
            if (msg.startsWith(token)) {
                return new String[]{null, split[0]};
            } else {
                return new String[]{split[0], null};

            }
        }
        return null;
    }


    public static class MsgVars {
        public int msgIndex = 0;
        public int playerCount = 0;
        public Object[] object = null;
        public MsgType type;

        public MsgVars(MsgType type, Object... obj) {
            this.type = type;
            if (obj == null) obj = new Object[0];
            this.object = obj;
        }
    }
}
