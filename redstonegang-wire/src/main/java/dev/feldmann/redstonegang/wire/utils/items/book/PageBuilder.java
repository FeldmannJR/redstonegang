package dev.feldmann.redstonegang.wire.utils.items.book;

import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.common.utils.msgs.CenterMessageUtils;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;

public class PageBuilder {
    ComponentBuilder main;

    public PageBuilder() {
    }

    public PageBuilder addLine(String line) {
        if (main != null) {
            main.append("\n");
        } else {
            main = new ComponentBuilder("");
        }
        main = main.append(line, ComponentBuilder.FormatRetention.NONE);

        return this;
    }

    public PageBuilder addText(String text) {

        if (main == null) {
            main = new ComponentBuilder("");
        }
        main = main.append(text);
        return this;
    }

    public PageBuilder addText(ChatColor color, String text) {
        if (main == null) {
            main = new ComponentBuilder("");
        }
        main = main.append(text);
        main = main.event((ClickEvent) null);
        main = main.event((HoverEvent) null);
        main = main.color(color);
        return this;
    }

    public PageBuilder addCommand(ChatColor cor, String command) {
        addText(cor, "/" + command);
        setHover("§e/" + command);
        return this;
    }

    public PageBuilder newLine() {
        if (main == null) {
            main = new ComponentBuilder("");
        }
        main = main.append("\n", ComponentBuilder.FormatRetention.NONE);
        return this;
    }

    public PageBuilder centerPage(String line) {
        return addLine(CenterMessageUtils.centralizeBook(line));
    }

    public PageBuilder gotoPage(int page) {
        main = main.event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, page + ""));
        return this;
    }

    public PageBuilder executeCommand(String command) {
        main = main.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    public PageBuilder setHoverBreak(ChatColor color, String message) {
        ComponentBuilder builder = null;
        for (String s : StringUtils.quebra(25, message)) {
            if (builder == null) {
                builder = new ComponentBuilder("");
            } else {
                builder.append("\n");
            }
            builder.append(s);
            builder.color(color);
        }
        setHover(builder.create());
        return this;
    }


    public PageBuilder setHover(BaseComponent... component) {
        main = main.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component));
        return this;
    }

    public PageBuilder setHover(String msg) {
        return setHover(TextComponent.fromLegacyText(msg));
    }

    public String create() {
        return ComponentSerializer.toString(main.create());
    }
}