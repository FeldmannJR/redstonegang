package dev.feldmann.redstonegang.wire.game.base.addons.server.correio.menus;

import dev.feldmann.redstonegang.common.utils.EnumUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioMsg;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.StatusMsg;
import dev.feldmann.redstonegang.wire.modulos.menus.*;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageBorderMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Collection;
import java.util.List;

public class CorreioCaixaServerMenu extends MultiplePageBorderMenu<CorreioMsg> {

    private CorreioAddon addon;

    Player p;
    StatusMsg msg;
    private CorreioAddon.Caixa cx = CorreioAddon.Caixa.ENTRADA;


    public CorreioCaixaServerMenu(CorreioAddon addon, CorreioAddon.Caixa cx, Player player, StatusMsg msg) {
        super("Mensagens do servidor");
        this.addon = addon;
        this.p = player;
        this.msg = msg;
        this.cx = cx;
    }


    @Override
    public void loadPage(int page) {
        super.loadPage(page);
        addFiltroLidas();
    }


    private void addFiltroLidas() {
        ItemBuilder naoLidas = CItemBuilder.item(msg.getMaterial())
                .name(msg.getNome())
                .lore("Clique aqui para alterar para", EnumUtils.next(msg).getNome());

        add(4, new Button(naoLidas.build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new CorreioCaixaServerMenu(addon, cx, p, EnumUtils.next(msg)).open(p, currentPage);
            }
        });
    }

    @Override
    public Button getButton(CorreioMsg ob, int page) {
        Button b = new Button(CorreioAddon.getHeadCorreioMsg(CorreioAddon.Caixa.ENTRADA, ob, "Clique para ver!", true)) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new VerMensagemMenu(p, ob, cx, msg, currentPage).open(p);
            }
        };

        return b;
    }

    /*
     * Ele puxa do db a cada pagina nova então ta sussa
     * Vou sobrescrever o metodo que usa o getall então ele nunca é chamado
     * */
    @Override
    public List getAll() {
        return null;
    }

    @Override
    public int getPages() {
        return addon.getTotalCaixa(CorreioAddon.Caixa.ENTRADA, addon.getPlayerId(p), msg);
    }

    @Override
    public Collection<CorreioMsg> getPage(int page) {
        return addon.getCaixa(CorreioAddon.Caixa.ENTRADA, addon.getPlayerId(p), msg, page);
    }

}
