package dev.feldmann.redstonegang.wire.game.base.addons.server.correio.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioMsg;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.StatusMsg;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.ConfirmarButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class VerMensagemMenu extends Menu {

    private Player player;
    private int page;
    private CorreioMsg msg;
    private StatusMsg filtro;
    private CorreioAddon.Caixa cx;

    public VerMensagemMenu(Player player, CorreioMsg msg, CorreioAddon.Caixa cx, StatusMsg filtro, int page) {
        super("Mensagem de " + msg.getRemetenteNome(), 5);
        this.page = page;
        this.filtro = filtro;
        this.player = player;
        this.msg = msg;
        this.cx = cx;
        this.addBorder();
        setReadButton();
        addDummyButtons();
        addDeleteMessageButton();
        addItensButton();
        addBackButton();
    }


    public void setReadButton() {
        if (cx == CorreioAddon.Caixa.SAIDA) {
            return;
        }
        add(3, new Button(getItem(msg.isRead())) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                msg.setRead(!msg.isRead());
                setItemStack(VerMensagemMenu.this.getItem(msg.isRead()));
            }
        });
    }

    public ItemStack getItem(boolean b) {
        return
                CItemBuilder.item(b ? Material.BOOK_AND_QUILL : Material.BOOK)
                        .name(b ? "Mensagem Lida" : "Mensagem não Lida")
                        .desc(b ? "Clique para marcar como não lida!" : "Clique aqui para marcar como lida.").build();

    }

    public void addDeleteMessageButton() {
        if (cx == CorreioAddon.Caixa.SAIDA) {
            return;
        }
        add(5, new ConfirmarButton(ItemBuilder.item(Material.BARRIER).name(C.msg(MsgType.ITEM_CANT, "Deletar")).desc("Clique aqui para deletar").desc("a mensagem!").build(), "Deletar a mensagem", "Tem certeza que quer@deletar a mensagem!") {
            @Override
            public void confirmar(Player p) {
                if (msg.delete()) {
                    p.playSound(p.getLocation(), Sound.EAT, 1, (float) 0.5);
                    new CorreioCaixaServerMenu(msg.getAddon(), cx, p, filtro).open(p, page);
                } else {
                    p.closeInventory();
                    msg.getAddon().logPlayer(p, "§cOcorreu um erro ao excluir a carta.");
                }
            }

            @Override
            public void recusar(Player p) {
                new VerMensagemMenu(p, msg, cx, filtro, page).open(p);
            }
        });
    }

    public void addItensButton() {
        add(24, new Button(
                CItemBuilder.item(Material.CHEST)
                        .name("Itens: ")
                        .desc("Contém %% itens.", msg.getItens().length)
                        .desc(" ")
                        .desc("Clique aqui para pegar")
                        .desc("os itens da carta!")
                        .build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (msg.getItens().length != 0) {
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                    new CorreioVerItensMenu(msg, p, cx, filtro, page).open(p);
                } else {
                    player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
                }
            }
        });
    }

    public void addDummyButtons() {
        add(13, new DummyButton(
                CItemBuilder.item(Material.WATCH).name("Enviado:")
                        .desc("Data: %%", CorreioAddon.fData.format(msg.getData()))
                        .desc("Hora: %%", CorreioAddon.fHora.format(msg.getData())).build())
        );
        add(20, new DummyButton(ItemBuilder.item(Material.DOUBLE_PLANT).name(C.item("Moedas: %%", msg.getCoins())).build()));
        if (cx == CorreioAddon.Caixa.ENTRADA) {
            add(22, new DummyButton(ItemBuilder.item(Material.REDSTONE).name(C.item("Remetente: %%", msg.getRemetenteNome())).build()));
        } else {
            add(22, new DummyButton(ItemBuilder.item(Material.REDSTONE).name(C.item("Destinatario: %%", msg.getDestinatario())).build()));
        }
    }

    public void addBackButton() {
        add(36, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new CorreioCaixaServerMenu(msg.getAddon(), cx, p, filtro).open(p, page);
            }
        });
    }


}
