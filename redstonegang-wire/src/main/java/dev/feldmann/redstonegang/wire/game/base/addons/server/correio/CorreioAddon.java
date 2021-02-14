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

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.menus.CorreioCaixaServerMenu;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.MapConfigurable;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.AConfig;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.InvSync;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.player.PlayerUtils;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Skype: isaias.finger GitHub: https://github.com/net32
 *
 * @author NeT32
 * adaptado por Feldmann
 */
@Dependencies(soft = EconomyAddon.class, apis = ConfigurableMapAPI.class)
public class CorreioAddon extends Addon {


    public static final SimpleDateFormat fData = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat fHora = new SimpleDateFormat("HH:mm:ss");

    /*
     * TODO
     *
     * Mecanica Casamento com taxas. meta add flag . hide enchantments não
     * enviar para si mesmo. nao enviar caso nao o destino nao tenha lido a
     * ultima nao enviar caso seja um staffer. somente staffer para staffer.
     */

    @AConfig
    public static int precoEnvio = 5;

    @AConfig
    public static String logPrefix = "§8[§f§lCorreio§8]§e ";

    @MapConfigurable
    NPC carteiro;

    CorreioDB db;
    private String dbName;

    public enum Caixa {
        ENTRADA("Entrada"),
        SAIDA("Saída");
        private String nome;

        Caixa(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return this.nome;
        }
    }


    public CorreioAddon(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void onEnable() {
        db = new CorreioDB(this, dbName);
        registerCommand(new RedstoneCmd("carteiro") {
            @Override
            public void command(CommandSender sender, Arguments args) {
                sendItensToCorreio((Player) sender);
            }
        });
    }


    public void logPlayer(Player p, String msg) {
        p.sendMessage(logPrefix + msg);
    }

    public int getTotalCaixa(Caixa cx, int playerId, StatusMsg smsg) {
        return db.getTotalCaixa(cx, playerId, smsg);
    }

    public List<CorreioMsg> getCaixa(Caixa cx, int playerId, StatusMsg smsg, int pagina) {
        return db.getCaixa(cx, playerId, smsg, pagina);
    }

    public CorreioMsg sendFromServer(int destinatario, String mensagem, double coins, ItemStack[] itens) {
        return sendFromServer(destinatario, mensagem, coins, itens, true);
    }

    public CorreioMsg sendFromServer(int destinatario, String mensagem, double coins, ItemStack[] itens, boolean notifica) {
        CorreioMsg msg = new CorreioMsg(this, null, destinatario, mensagem, coins, itens);
        if (db.saveMsg(msg)) {
            if (notifica) {
                msg.notifica();
                return msg;
            }
        }
        return null;
    }

    public CorreioMsg sendFromWho(int remetente, int destinatario, String mensagem, double coins, ItemStack[] itens) {
        CorreioMsg msg = new CorreioMsg(this, remetente, destinatario, mensagem, coins, itens);
        if (db.saveMsg(msg)) {
            msg.notifica();
            return msg;
        }
        return null;
    }

    public CorreioMsg sendMensagem(Player remetente, int destinatario, String mensagem, double coins, ItemStack[] itens) {
        return sendMensagem(getPlayerId(remetente), destinatario, mensagem, coins, itens);
    }


    public CorreioMsg sendMensagem(int remetente, int destinatario, String mensagem, double coins, ItemStack[] itens) {
        CorreioMsg msg = new CorreioMsg(this, remetente, destinatario, mensagem, coins, itens);
        if (db.saveMsg(msg)) {
            msg.notifica();
            return msg;
        }
        return null;
    }

    public void sendItensToCorreio(Player p) {
        p.closeInventory();
        List<ItemStack> it = new ArrayList();
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType() != Material.AIR) {
                it.add(i);
            }
        }
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (i != null && i.getType() != Material.AIR) {
                it.add(i);
            }
        }
        if (p.getItemOnCursor() != null && p.getItemOnCursor().getType() != Material.AIR) {
            it.add(p.getItemOnCursor());
            p.setItemOnCursor(new ItemStack(Material.AIR));
        }
        List<List<ItemStack>> itens = new ArrayList();
        List<ItemStack> atual = new ArrayList();
        int foi = 0;
        for (ItemStack item : it) {
            if (foi >= 36) {
                itens.add(atual);
                atual = new ArrayList<>();
                foi = 0;
            }
            atual.add(item);
            foi++;
        }
        if (!atual.isEmpty()) {
            itens.add(atual);
        }
        boolean sent = false;
        int parte = 1;
        for (List<ItemStack> lista : itens) {
            if (!lista.isEmpty()) {
                ItemStack[] array = lista.toArray(new ItemStack[lista.size()]);
                if (itens.size() == 1) {
                    sendFromServer(getPlayerId(p), "Seus Itens ", 0, array, false);
                } else {
                    sendFromServer(getPlayerId(p), "Seus Itens " + parte + "/" + itens.size(), 0, array,
                            false);
                }
                sent = true;
            }
            parte++;
        }
        if (sent) {
            p.sendMessage("§b§lNão se preocupe, §fSeus itens foram enviados pelo correio!");
        }
        PlayerUtils.limpa(p);
        //GAMBIARRA
        if (!a(InvSync.class).isWaitingLoad(p)) {
            a(InvSync.class).save(p);
        }


    }

    @EventHandler
    private void JogadorEntrou(PlayerJoinEvent ev) {
        Player p = ev.getPlayer();
        List<CorreioMsg> caixa = getCaixa(CorreioAddon.Caixa.ENTRADA, RedstoneGangSpigot.getPlayer(p).getId(), StatusMsg.NAOLIDAS, 0);
        if (caixa.size() > 0) {
            p.sendMessage("§6-=-=-=- §f§lCORREIO §6-=-=-=-=-");
            logPlayer(p, "§6Você tem §f§l" + caixa.size() + " §6cartas não lidas.");
            logPlayer(p, "§6Vá até o §fCarteiro §6para ler!");
            p.sendMessage("§6-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        }
    }

    @EventHandler
    public void npc(NPCRightClickEvent ev) {
        if (carteiro != null && carteiro == ev.getNPC()) {
            new CorreioCaixaServerMenu(this, Caixa.ENTRADA, ev.getClicker(), StatusMsg.TODAS).open(ev.getClicker());
        }
    }

    public static ItemStack getHeadCorreioMsg(CorreioAddon.Caixa cx, CorreioMsg msg, String info, boolean withStatus) {
        ItemStack cabeca;
        if (msg.getRemetente() == null) {
            cabeca = new ItemStack(Material.REDSTONE);
            if (withStatus) {
                ItemUtils.setItemName(cabeca, getStatusCor(msg) + msg.getRemetenteNome());
                ItemUtils.addLore(cabeca, "§f> " + msg.getTitulo());
                ItemUtils.addLore(cabeca, " ");
                ItemUtils.addLore(cabeca, "§fData: §e" + fData.format(msg.getData()));
                ItemUtils.addLore(cabeca, "§fHora: §e" + fHora.format(msg.getData()));
            } else {
                ItemUtils.setItemName(cabeca, ChatColor.YELLOW + "" + ChatColor.BOLD + msg.getRemetenteNome());
            }
        } else {
            int quem = msg.getRemetente();
            if (cx.equals(Caixa.SAIDA)) {
                quem = msg.getDestinatario();
            }
            User pl = RedstoneGangSpigot.getPlayer(quem);
            cabeca = ItemUtils.getHead(pl.getDisplayName());
            if (withStatus) {
                ItemUtils.setItemName(cabeca, getStatusCor(msg) + pl.getDisplayName());
                ItemUtils.addLore(cabeca, "§f> §e" + msg.getTitulo());
                ItemUtils.addLore(cabeca, " ");
                ItemUtils.addLore(cabeca, "§fData: §e" + fData.format(msg.getData()));
                ItemUtils.addLore(cabeca, "§fHora: §e" + fHora.format(msg.getData()));
            } else {
                ItemUtils.setItemName(cabeca, ChatColor.YELLOW + "§l" + pl.getDisplayName());
            }
        }
        ItemUtils.addLore(cabeca, "§f ");
        ItemUtils.addLore(cabeca, "§f" + info);
        return cabeca;
    }

    public static String getStatusCor(CorreioMsg msg) {
        String cor;
        if (msg.isRead()) {
            cor = ChatColor.GREEN + "" + ChatColor.BOLD;
        } else {
            cor = ChatColor.RED + "" + ChatColor.BOLD;
        }
        return cor;
    }

}
