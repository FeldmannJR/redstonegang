package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.scoreboard;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.currencies.Cash;
import dev.feldmann.redstonegang.common.currencies.Currencies;
import dev.feldmann.redstonegang.common.currencies.Currency;
import dev.feldmann.redstonegang.common.currencies.CurrencyDouble;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.Group;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestPoints;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerChangeConfigEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.modulos.scoreboard.ScoreboardManager;
import dev.feldmann.redstonegang.wire.permissions.events.PlayerChangeGroupEvent;
import dev.feldmann.redstonegang.wire.plugin.events.PlayerCurrencyChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;

public class SideBar extends BaseListener {

    private String displayName = "§4§lRedstoneGang";
    private int cargoLine;
    private int moneyLine;
    private String moneyPrefix = "§a§f";
    private int cashLine;
    private String cashPrefix = "§b§f";
    private int questPointsLine;
    private String questPrefix = "§c§f";


    HashMap<Integer, String> lines = new HashMap<>();
    SurvivalScoreboard addon;

    public SideBar(SurvivalScoreboard addon) {
        this.addon = addon;
    }

    public void setup() {
        Cash cash = Currencies.CASH;
        CurrencyDouble moedas = addon.a(EconomyAddon.class).getCurrency();
        QuestPoints quest = addon.a(QuestAddon.class).currency;
        addLine("§e§lVIP");
        cargoLine = addLine("§d...");
        addLine("§2");
        addLine(moedas.getColor() + "§l" + StringUtils.capitalizeFirstChar(moedas.getMoedaName(2)));
        moneyLine = addLine(moneyPrefix + "...");
        addLine("§1");
        addLine(cash.getColor() + cash.getMoedaName(2));
        cashLine = addLine(cashPrefix + "...");
        addLine("§0");
        addLine(quest.getColor() + "§l" + quest.getMoedaName(2));
        questPointsLine = addLine(questPrefix + "...");
        addLine("----------------");
    }

    public int addLine(String line) {
        int start = 15;
        int slot = start - lines.size();
        lines.put(slot, line);
        return slot;
    }

    public void display(Player p) {
        Objective sidebar = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        ScoreboardManager.setDisplayName(p, DisplaySlot.SIDEBAR, displayName);
        for (Integer line : lines.keySet()) {
            ScoreboardManager.setScoreLine(p, line, lines.get(line));
        }
        updateCargo(p);
        updateMoney(p);
        updateCash(p);
        updateQuests(p);
    }

    public void hide(Player p) {
        ScoreboardManager.removeSlot(p, DisplaySlot.SIDEBAR);
    }

    public void updateCurrency(Player p, Currency currency, int line, String prefix) {
        int playerId = addon.getPlayerId(p);
        addon.runAsync(() -> currency.get(playerId), (money) -> {
            if (p.isOnline()) {
                if (money instanceof Double) {
                    money = ((Double) money).longValue();
                }
                if (money instanceof Integer) {
                    money = ((Integer) money).longValue();
                }

                String value = currency == Currencies.CASH ? money + "" : NumberUtils.convertToString((long) money);
                ScoreboardManager.setScoreLine(p, line, prefix + value);
            }
        });
    }

    public void updateMoney(Player p) {
        this.updateCurrency(p, addon.a(EconomyAddon.class).getCurrency(), moneyLine, moneyPrefix);
    }

    public void updateCash(Player p) {
        this.updateCurrency(p, Currencies.CASH, cashLine, cashPrefix);
    }

    public void updateQuests(Player p) {
        this.updateCurrency(p, addon.a(QuestAddon.class).currency, this.questPointsLine, questPrefix);
    }

    public void updateCargo(Player p) {
        String cargo = "Sem Vip! Use /loja";
        User user = addon.getUser(p);
        Group group = user.permissions().getGroup();
        if (group != null && !group.isDefaultGroup()) {
            cargo = group.getDisplayName();
        }
        ScoreboardManager.setScoreLine(p, cargoLine, cargo);
    }

    @EventHandler
    public void groupChange(PlayerChangeGroupEvent ev) {
        User user = ev.getPlayer();
        Player player = RedstoneGangSpigot.getOnlinePlayer(user);
        if (player != null && player.isOnline()) {
            if (user.getConfig(addon.USE_SIDEBAR))
                updateCargo(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void configChanged(PlayerChangeConfigEvent ev) {
        if (ev.getConfig() == addon.USE_SIDEBAR) {
            Boolean value = (Boolean) ev.getNewvalue();
            if (value != null) {
                if (value) {
                    this.display(ev.getPlayer());
                } else {
                    this.hide(ev.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void moneyUpdate(PlayerCurrencyChangeEvent ev) {
        int player = ev.getPlayerId();
        User cached = RedstoneGang.instance().user().getCachedUser(player);
        if (cached != null && cached.getConfig(addon.USE_SIDEBAR)) {
            Player p = RedstoneGangSpigot.getOnlinePlayer(player);
            if (p != null && p.isOnline()) {

                if (ev.getCurrency() == addon.a(EconomyAddon.class).getCurrency()) {
                    updateMoney(p);
                }
                if (ev.getCurrency() == Currencies.CASH) {
                    updateCash(p);
                }
                if (ev.getCurrency() == addon.a(QuestAddon.class).currency) {
                    updateQuests(p);
                }
            }

        }
    }
}
