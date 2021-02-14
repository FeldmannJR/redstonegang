package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.menu;

import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.Titulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloCor;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloPlayer;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class EscolheTitulo extends MultiplePageMenu<Titulo> {

    TituloAddon addon;
    int pid;
    Button back;

    public EscolheTitulo(int pid, TituloAddon addon, Button back) {
        super("Escolhendo titulo");
        this.addon = addon;
        this.pid = pid;
        this.back = back;
    }

    @Override
    public Button getPreviousButton() {
        return back;
    }

    @Override
    public Button getButton(Titulo ob, int page) {
        String name = C.msg(MsgType.ITEM, ob.getProcessedName());
        TituloCor ativo = addon.getTitulos(ob.getOwner()).getAtivo();

        List<String> lore = new ArrayList<>();
        if (ob.useColors()) {
            lore.add(C.msg(MsgType.ITEM_DESC, "%% cores!", ob.getCores()));
        }
        if (ativo != null && ativo.getParent() == ob) {
            lore.add(C.msg(MsgType.ITEM_CAN, "Ativo!"));
        }
        return new Button(ItemBuilder.item(ob.getMaterial()).name(name).lore(lore).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (ob.useColors()) {
                    new EscolheCor(ob, addon).open(p);
                    return;
                } else {
                    TituloCor cor = ob.getCor().values().iterator().next();
                    addon.setAtivo(ob.getOwner(), cor);
                }
            }
        };

    }

    @Override
    public List<Titulo> getAll() {
        TituloPlayer p = addon.getTitulos(pid);
        return new ArrayList<>(p.getTitulos().values());
    }
}
