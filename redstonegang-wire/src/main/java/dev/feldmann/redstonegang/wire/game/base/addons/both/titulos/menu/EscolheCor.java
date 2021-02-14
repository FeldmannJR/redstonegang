package dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.menu;

import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.Titulo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.TituloCor;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events.PlayerChangeTitleEvent;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.Cores;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


import java.util.Collection;

public class EscolheCor extends Menu {

    Titulo t;
    TituloAddon addon;

    public EscolheCor(Titulo t, TituloAddon addon) {
        super("Escolhe Cor", calcSize(t.getCores()));
        this.t = t;
        this.addon = addon;
        addButtons();
    }

    public void addButtons() {
        Collection<TituloCor> cs = t.getCor().values();
        for (TituloCor c : cs) {
            String corstring = c.getCor();
            if (corstring.startsWith("§")) {
                char corchar = corstring.charAt(1);
                ChatColor chatColor = ChatColor.getByChar(corchar);

                String nome = (corstring + Cores.getNome(chatColor));
                if (corstring.length() > 2) {
                    char last = corstring.charAt(corstring.length() - 1);
                    ChatColor lastColor = ChatColor.getByChar(last);
                    if (lastColor != null) {
                        if (lastColor == ChatColor.BOLD) {
                            nome += " " + Cores.getNome(lastColor);
                        }
                    }
                }
                ItemStack it = ItemBuilder.item(Cores.getData(chatColor)).name(nome).build();
                if (c.getDesc() != null) {
                    for (String d : StringUtils.quebra(25, c.getDesc())) {
                        ItemUtils.addLore(it, C.itemDesc(d));
                    }
                }
                if (c.getAdded() != null) {
                    ItemUtils.addLore(it, C.msg(MsgType.ITEM_CAN, DateUtils.formatDate(c.getAdded())));
                }
                addNext(new Button(it) {
                    @Override
                    public void click(Player p, Menu m, ClickType click) {
                        int pid = addon.getPlayerId(p);
                        if (addon.getTitulos(addon.getPlayerId(p)).has(c)) {
                            if (!Wire.callEvent(new PlayerChangeTitleEvent(p, addon.getTitulos(pid).getAtivo(), c))) {
                                addon.setAtivo(addon.getPlayerId(p), c);
                                C.error(p, "Titulo atualizado!!");
                            }
                        } else {
                            C.error(p, "Ocorreu um erro!");
                        }
                        p.closeInventory();

                    }
                });
            }
        }

    }


}
