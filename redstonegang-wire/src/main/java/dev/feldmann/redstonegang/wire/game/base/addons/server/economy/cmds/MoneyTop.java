package dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.money.MoneyTopEntry;
import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.List;

public class MoneyTop extends RedstoneCmd {
    EconomyAddon addon;

    public MoneyTop(EconomyAddon addon) {
        super("ricos", "lista de pessoas mais ricas do servidor");
        setAlias("moneytop");
        setPermission("rg.economy.cmd.top");
        this.addon = addon;
    }

    @Override
    public void command(CommandSender sender, Arguments args) {

        C.send(sender, null, "§4§lMais Ricos do Servidor");
        List<MoneyTopEntry> top = getTop();
        for (int x = 0; x < Math.min(5, top.size()); x++) {
            MoneyTopEntry entry = top.get(x);
            Double value = (Double) entry.getValue();
            ChatColor c = ChatColor.YELLOW;
            if (x == 0) c = ChatColor.GOLD;
            C.send(sender, null, c + "  " + (x + 1) + "º. %%§7 -§f %%", RedstoneGang.getPlayer(entry.getPlayerId()), NumberUtils.convertToString(value.longValue()));
        }
        if (!(sender instanceof Player)) {
            return;
        }
        Player.Spigot player = ((Player) sender).spigot();

        for (int[] bounds : new int[][]{new int[]{5, 20}, new int[]{20, 30}, new int[]{30, 40}, new int[]{40, 50}}) {
            TextComponent resto = getTextComponent(bounds[0], bounds[1], "  " + (bounds[0] + 1) + "º ao " + bounds[1] + "º");
            if (resto == null) {
                break;
            }
            player.sendMessage(resto);
        }
    }

    private TextComponent getTextComponent(int start, int end, String text) {
        List<MoneyTopEntry> top = getTop();
        if (top.size() < start) {
            return null;
        }
        TextComponent hover = new TextComponent();
        for (int i = start; i < Math.min(end, top.size()); i++) {
            MoneyTopEntry entry = top.get(i);

            Double value = (Double) entry.getValue();

            hover.addExtra(new TextComponent(
                    TextComponent.fromLegacyText(
                            "§e" + (i + 1) + "º. "
                                    + entry.getPlayerName() + "§7 - §f"
                                    + NumberUtils.convertToString(value.longValue()) + "\n"
                    )));
        }
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(text));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.getExtra().toArray(new BaseComponent[0])));
        return component;

    }

    private List<MoneyTopEntry> cache = null;
    private long lastUpdate = -1;

    public List<MoneyTopEntry> getTop() {
        if (cache == null || lastUpdate < System.currentTimeMillis() - 10000) {
            cache = addon.getDb().getTop(50);
            lastUpdate = System.currentTimeMillis();
        }
        return cache;
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }
}
