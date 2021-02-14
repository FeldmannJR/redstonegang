package dev.feldmann.redstonegang.wire.game.base.addons.server.quests;

import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public abstract class QuestInfo {

    private QuestManager manager;

    int id = -1;
    int playerId;
    int quest;
    boolean completa = false;
    boolean newstreak;
    public boolean daily = false;
    Timestamp comecou;
    Timestamp terminar;

    Timestamp terminou;

    public boolean fail = false;


    public Timestamp getTerminar() {
        return terminar;
    }

    public boolean isCompleta() {
        return completa;
    }

    public void setManager(QuestManager manager) {
        this.manager = manager;
    }

    public boolean isAtiva() {
        return terminar.after(new Timestamp(System.currentTimeMillis()));
    }

    public void setNewstreak(boolean newstreak) {
        this.newstreak = newstreak;
    }

    public boolean isNewstreak() {
        return newstreak;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return RedstoneGangSpigot.getOnlinePlayer(playerId);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setComecou(Timestamp comecou) {
        this.comecou = comecou;
    }

    public Timestamp getComecou() {
        return comecou;
    }

    public void setQuest(int quest) {
        this.quest = quest;
    }

    public void setCompleta(boolean completa) {
        this.completa = completa;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Quest getQuest() {
        return manager.getQuestById(quest);
    }

    public void sendMsg() {
        TextComponent tx = new TextComponent();
        tx.setText("§9[Missão] §fProgresso missão diária " + getStatus() + " §e!");
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§b" + getQuest().nome), new TextComponent("\n§f" + getStatus())}));
        getPlayer().spigot().sendMessage(tx);
        getPlayer().playSound(getPlayer().getLocation(), Sound.CLICK, 1, 1);

    }

    public void finishQuest() {
        if (completa) return;
        this.completa = true;
        this.terminou = new Timestamp(System.currentTimeMillis());
        int deu = manager.giveReward(playerId);
        TextComponent tx = new TextComponent();
        tx.setText("§9[Missão] §aMissão diaria do dia " + manager.getDaily().getDia(playerId) + "º completa!");

        manager.getAddon().getServer().broadcast("§9[Missões] " + RedstoneGangSpigot.getPlayer(playerId).getNameWithPrefix() + " §7completou sua missão diária! §a[" + getQuest().nome + "] §7!");
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§b" + getQuest().nome)}));
        getPlayer().spigot().sendMessage(tx);
        getPlayer().playSound(getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
        getPlayer().sendMessage("§9[Missão] §fVocê ganhou §6" + deu + " §9QuestPoints §f!");
        getPlayer().sendMessage("§7Para trocar seus pontos fale com o §a" + QuestAddon.nomenpc + " §7no spawn!");
        saveDB();

    }

    public void saveDB() {
        manager.save(this);
    }

    public void faz() {
        faz(1);
    }

    public abstract void faz(int x);

    public abstract String getStatus();

    //UTILS
    public boolean isInteger(String s) {
        try {
            int x = Integer.valueOf(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;

    }


    public boolean isTodayDaily() {
        if (!daily) return false;
        Date d = new Date(System.currentTimeMillis());
        Calendar cd = Calendar.getInstance();
        cd.setTime(d);
        int dia = cd.get(Calendar.DAY_OF_MONTH);
        int mes = cd.get(Calendar.MONTH);
        int ano = cd.get(Calendar.YEAR);

        Calendar qc = Calendar.getInstance();
        qc.setTime(terminar);
        return dia == qc.get(Calendar.DAY_OF_MONTH) && mes == qc.get(Calendar.MONTH) && ano == qc.get(Calendar.YEAR);


    }
}

