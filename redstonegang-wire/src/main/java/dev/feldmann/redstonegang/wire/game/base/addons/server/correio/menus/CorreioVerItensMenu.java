package dev.feldmann.redstonegang.wire.game.base.addons.server.correio.menus;


import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioMsg;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.StatusMsg;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MenuHelper;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CorreioVerItensMenu extends Menu {

    Player p;
    CorreioMsg msg;
    StatusMsg status;
    CorreioAddon.Caixa cx;
    int page;


    public CorreioVerItensMenu(CorreioMsg msg, Player p, CorreioAddon.Caixa cx, StatusMsg status, int page) {
        super("Carta > Itens", 5);
        this.msg = msg;
        this.status = status;
        this.page = page;
        this.p = p;
        this.cx = cx;
        addItens();
        addBorder();
        addButtonExtractItens();
        addBackButton();
    }

    public void addItens() {
        for (ItemStack item : msg.getItens()) {
            // final CorreioMsg fMsg = msg;
            addToSquare(9, 35, new DummyButton(item));
        }
    }

    public void addBackButton() {
        add(36, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                goBack(p);
            }
        });
    }

    public void goBack(Player p) {
        new CorreioCaixaServerMenu(msg.getAddon(), cx,p, status).open(p, page);

    }

    public void addButtonExtractItens() {

        if (msg.getDestinatario() == RedstoneGangSpigot.getPlayer(p).getId()) {

            if (!msg.isItensTranferidos()) {
                add(40, new Button(CItemBuilder.item(Material.HOPPER).name("Transferir Itens").desc("Clique aqui para transferir", "os itens para seu inventário!").build()) {
                    @Override
                    public void click(Player p, Menu m, ClickType click) {
                        if (!msg.isItensTranferidos()) {
                            int empty = InventoryHelper.getFreeSlots(p);
                            if (empty < msg.getItens().length) {
                                C.error(p, "Falta %% espaço vazio no seu inventário para pegar os itens!", msg.getItens().length - empty);
                                return;
                            }
                            if (msg.setTranferidos()) {
                                p.getInventory().addItem(msg.getItens());
                                msg.getAddon().logPlayer(p, "§eItens transferidos com sucesso!");
                                goBack(p);
                                p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, (float) 1.5);
                            } else {
                                p.closeInventory();
                                C.error(p, "Ocorreu um erro ao excluir a carta.");
                            }
                        }
                    }
                });
            } else {
                add(40, new DummyButton(CItemBuilder.item(Material.BARRIER).name("Itens já transferidos!").lore("Você já pegou os itens!").build()));
            }
        }
    }

    @Override
    protected void addBorder() {
        for (int slot : MenuHelper.buildSquare(0, 8)) {
            add(slot, new DummyButton(getBorderItemStack()));
        }
        for (int slot : MenuHelper.buildHollowSquare(36, 44)) {
            add(slot, new DummyButton(getBorderItemStack()));
        }
    }
}
