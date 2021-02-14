package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.ShopClick;

import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.MenuVerPacote;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuSelecionaKits;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.CorreioAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ShopPacoteAleatorio extends ShopValuable {

    public List<Integer> pacotes = new ArrayList();

    public static Random rnd = new Random();

    public void valida() {
        Iterator<Integer> ite = pacotes.iterator();
        while (ite.hasNext()) {
            Integer n = ite.next();
            ShopClick c = addon.getClickById(n);
            if (c == null || !(c.getClass() == ShopItemStack.class)) {
                ite.remove();
                addon.save(this);
            }
        }
    }

    @Override
    public boolean compraPacote(Player p) {
        valida();
        int s = pacotes.get(rnd.nextInt(pacotes.size()));
        ShopItemStack clickById = (ShopItemStack) addon.getClickById(s);
        ItemStack[] copy = new ItemStack[clickById.itens.length];
        for (int x = 0; x < clickById.itens.length; x++) {
            if (clickById.itens[x] != null)
                copy[x] = clickById.itens[x].clone();
        }
        p.sendMessage("§a§l[SHOP] §e§lOs itens foram enviados para o carteiro!");
        addon.a(CorreioAddon.class).sendFromServer(addon.getPlayerId(p), "Pacote Aleatório:" + clickById.nome, 0, copy);
        comprou(p);
        p.closeInventory();
        return true;
    }

    @Override
    public void click(Player p) {

        int tem = pacotes.size();
        if (tem <= 1) {
            p.sendMessage("§7Pacote bugado!");
            return;
        }
        while (tem % 9 != 0) {
            tem++;
        }

        MenuVerPacote m = new MenuVerPacote("Pacote Aleatório", this, tem + 9, addon);
        m.add(8, getComprarButton());
        m.add(4, new DummyButton(buildItemStack()));
        valida();
        for (int x : pacotes) {
            ShopItemStack click = (ShopItemStack) addon.getClickById(x);
            ItemStack it = click.getIcone();
            ItemUtils.setItemName(it, "§a§l" + click.nome);
            ItemUtils.addLore(it, "§fValor: " + addon.currency.getValue(click.preco));
            ItemUtils.addLore(it, "§ePode vir este pacote! ");
            m.addToSquare(9, 53, new DummyButton(it));
        }
        m.open(p);

    }


    public void selectKits(Player player) {
        new MenuSelecionaKits(this, addon).open(player);

    }

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopPacoteAleatorio aleatorio = this;
        m.addNext(new Button(ItemBuilder.item(Material.ENDER_CHEST).name("§e§lSelecionar Kits").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                selectKits(player);
            }
        });
    }

}
