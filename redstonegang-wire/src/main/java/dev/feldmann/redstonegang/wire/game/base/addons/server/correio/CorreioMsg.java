/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Projeto feito por Carlos Andre Feldmann Junior, Isaias Finger e Gabriel Augusto Souza
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.correio;

import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

/**
 * Skype: isaias.finger GitHub: https://github.com/net32
 *
 * @author NeT32
 */
public class CorreioMsg {

    private long id;
    private Integer remetente;
    private Integer destinatario;
    private String mensagem;
    private double coins;
    private ItemStack[] itens;
    private boolean itenstrans;
    private boolean aberta;
    private long data;
    private CorreioAddon addon;

    public CorreioMsg(CorreioAddon addon, long id, Integer remetente, int destinatario, String mensagem, double coins, ItemStack[] itens, boolean itenstrans, boolean aberta, long data) {
        this.id = id;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.coins = coins;
        this.itens = itens;
        this.itenstrans = itenstrans;
        this.aberta = aberta;
        this.data = data;
        this.addon = addon;
    }

    public CorreioMsg(CorreioAddon addon, Integer remetente, int destinatario, String mensagem, double coins, ItemStack[] itens) {
        this(addon, 0, remetente, destinatario, mensagem, 0, itens, false, false, System.currentTimeMillis());
    }

    public CorreioMsg(CorreioAddon addon, Integer remetente, int destinatario, String mensagem) {
        this(addon, 0, remetente, destinatario, mensagem, 0, null, false, false, System.currentTimeMillis());
    }

    public boolean isSaved() {
        return id != 0;
    }

    public long getId() {
        return this.id;
    }


    public CorreioMsg setSaveId(long id) {
        if (!this.isSaved()) {
            this.id = id;
        }
        return this;
    }


    public Date getData() {
        Date date = new Date();
        date.setTime(this.data);
        return date;
    }

    public boolean isRead() {
        return this.aberta;
    }

    public CorreioMsg read() {
        this.aberta = true;
        addon.db.saveMsg(this);
        return this;
    }

    public CorreioMsg unread() {
        this.aberta = false;
        addon.db.saveMsg(this);
        return this;
    }

    public void setRead(boolean read) {
        this.aberta = read;
        addon.db.saveMsg(this);

    }

    public boolean delete() {
        return addon.db.deleteMsg(this);
    }

    public Integer getRemetente() {
        return remetente;
    }

    public Integer getDestinatario() {
        return destinatario;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public String getTitulo() {
        if (this.getMensagem().length() > 20) {
            return this.getMensagem().substring(0, 20) + "...";
        } else {
            return this.getMensagem();
        }
    }

    public String getResumo() {
        if (this.getMensagem().length() > 30) {
            return this.getMensagem().substring(0, 30) + "...";
        } else {
            return this.getMensagem();
        }
    }

    public double getCoins() {
        return this.coins;
    }

    public ItemStack[] getItens() {
        if (this.itens != null) {
            return this.itens;
        } else {
            return new ItemStack[0];
        }
    }

    public boolean isItensTranferidos() {
        return this.itenstrans;
    }

    public boolean setTranferidos() {
        this.itenstrans = true;
        return addon.db.saveMsg(this);
    }

    public String getRemetenteNome() {
        if (remetente == null) {
            return "RedstoneGang";
        }
        return RedstoneGangSpigot.getPlayer(remetente).getDisplayName();
    }

    public String getDestinatarioNome() {
        return RedstoneGangSpigot.getPlayer(destinatario).getDisplayName();
    }

    public CorreioMsg notifica() {
        Player p = RedstoneGangSpigot.getOnlinePlayer(destinatario);
        String notificao = "§6Você recebeu uma Carta do(a) §f§l" + this.getRemetenteNome() + "§6, vá até o §fCarteiro §6para ler!";
        if (p != null) {
            addon.logPlayer(p, notificao);
        }
        return this;
    }

    public CorreioAddon getAddon() {
        return addon;
    }
}
