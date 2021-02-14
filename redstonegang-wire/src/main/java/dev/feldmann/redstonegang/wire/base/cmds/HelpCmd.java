package dev.feldmann.redstonegang.wire.base.cmds;

import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HelpCmd extends RedstoneCmd {

    private static final IntegerArgument PAGE = new IntegerArgument("pagina", 1, 100);


    public HelpCmd() {
        super("ajuda", "ve a ajuda do comando", PAGE);
        setAlias("help");
    }

    @Override
    public void command(CommandSender sender, Arguments args) {
        showHelp(sender, args.get(PAGE));

    }


    private ArrayList<List<TextComponent>> buildHelp(CommandSender sender) {
        ArrayList<List<TextComponent>> helpPages = new ArrayList();

        List<TextComponent> page = new ArrayList();
        int li = 0;
        for (RedstoneCmd sub : getParent().cmds) {
            if (sub.getExecutePerm() != null && !sub.getExecutePerm().canExecute(sender)) {
                continue;
            }
            if (sub instanceof HelpCmd) {
                continue;
            }
            ComponentBuilder b = new ComponentBuilder("  " + sub.getName());
            b.color(ChatColor.YELLOW);
            String c = sub.buildUsage();
            b.append(c.isEmpty() ? "" : (" " + c));
            b.append(" ⇾ ").color(ChatColor.WHITE);
            b.append(sub.getDescription()).color(ChatColor.GRAY);
            BaseComponent[] base = b.create();
            //Conta o tamanho de cada base element java é uma delicia
            int t = Stream.of(base).mapToInt(s -> s.toPlainText().length()).sum();

            int lines = 1 + (t / 53);
            if (li + lines > 12) {
                helpPages.add(page);
                page = new ArrayList<>();
                li = 0;
            }
            li += lines;
            TextComponent cp = new TextComponent(base);
            if (sub.getLongHelp() != null) {
                ComponentBuilder longHelp = null;

                for (String s : StringUtils.quebra(40, sub.getLongHelp())) {
                    if (longHelp == null) {
                        longHelp = new ComponentBuilder("");
                    } else {
                        longHelp.append("\n");
                    }
                    longHelp.append(s).color(ChatColor.GRAY);
                }
                cp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, longHelp.create()));
            }
            cp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + sub.getFullCommand()));
            page.add(cp);
        }
        if (!page.isEmpty()) {
            helpPages.add(page);
        }
        return helpPages;
    }

    public void showHelp(CommandSender sender, int page) {
        ArrayList<List<TextComponent>> helpPages = buildHelp(sender);
        if (page > helpPages.size()) {
            C.error(sender, "Esta pagina de ajuda não existe!");
            return;
        }
        //Se a pagina não for a primeira "limpa" a tela do player

        if (sender instanceof Player) {
            for (int x = 0; x < 3; x++) {
                sender.sendMessage("      ");
            }
        }

        sender.sendMessage(" §9§lAjuda §f" + StringUtils.capitalizeFirstChar(getParent().getName()) + " ");
        //Manda a ajuda
        for (TextComponent c : helpPages.get(page - 1)) {
            if (sender instanceof Player) {
                ((Player) sender).spigot().sendMessage(c);
            } else {
                sender.sendMessage(c.toPlainText());
            }
        }
        //Builda os clicks
        TextComponent nav = new TextComponent("");
        if (page > 1) {
            TextComponent voltar = new TextComponent("§2<< Voltar");
            voltar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + getFullCommand() + " " + (page - 1)));
            nav.addExtra(voltar);
        } else {
            nav.addExtra(StringUtils.generateSpaces("§e<< Voltar".length()) + "§e");
        }
        nav.addExtra(StringUtils.generateSpaces(15));
        nav.addExtra("[" + page + "]");
        nav.addExtra(StringUtils.generateSpaces(15));
        if (page + 1 <= helpPages.size()) {
            TextComponent avancar = new TextComponent("§aAvançar >>");
            avancar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + getFullCommand() + " " + (page + 1)));
            nav.addExtra(avancar);
        }
        if (sender instanceof Player) {
            if (!nav.toPlainText().isEmpty()) {
                ((Player) sender).spigot().sendMessage(nav);
            }
        }

    }
}
