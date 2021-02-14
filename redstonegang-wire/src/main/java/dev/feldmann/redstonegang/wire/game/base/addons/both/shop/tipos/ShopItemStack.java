package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.MenuVerPacote;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit.MenuEditPacotes;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SelectInventory;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ShopItemStack extends ShopValuable {

    ItemStack[] itens = new ItemStack[0];

    @Override
    public void click(Player p) {
        int tem = itens.length;
        while (tem % 9 != 0) {
            tem++;
        }

        MenuVerPacote m = new MenuVerPacote(nome, this, tem + 9, addon);
        if (itens.length == 1) {
            m.add(13, new DummyButton(itens[0].clone()));
        } else {
            for (ItemStack it : itens) {
                if (it != null) {
                    m.addToSquare(9, 35, new DummyButton(it.clone()));

                }
            }
        }
        m.open(p);
    }

    @Override
    public ItemStack getIcone() {
        if (this.icone == null && this.itens != null && this.itens.length > 0 && this.itens[0] != null) {
            return this.itens[0].clone();
        }
        return super.getIcone();
    }

    public void setItens(ItemStack[] itens) {
        this.itens = itens;
    }

    public ItemStack[] getItens() {
        return itens;
    }

    public int sizeItens() {
        int x = 0;
        for (ItemStack it : itens) {
            if (it != null && it.getType() != Material.AIR) {
                x++;
            }
        }
        return x;
    }

    @Override
    public boolean compraPacote(Player p) {
        int free = 0;
        for (int x = 0; x < p.getInventory().getSize(); x++) {
            if (p.getInventory().getItem(x) == null) {
                free++;
            }
        }
        if (free < sizeItens()) {
            p.sendMessage("§cInventário lotado !!!");
            addon.openPlayer(p, pageid);
            return false;
        }
        for (ItemStack it : itens) {
            if (it != null) {
                ItemStack item = it.clone();
                p.getInventory().addItem(item);
            }
        }
        addon.openPlayer(p, pageid);
        comprou(p);
        return true;
    }

    @Override
    public void editPacket(Menu m) {
        super.editPacket(m);
        ShopItemStack click = this;
        m.addNext(new Button(ItemBuilder.item(Material.CHEST).name("§aSetar Itens").lore("§f").build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                selectItens(player, false);
            }
        });
    }

    public void selectItens(Player player, boolean add) {
        ShopItemStack click = this;
        new SelectInventory("Set Itens", 3, click.getItens(), (p, its) -> {
            click.setItens(its);
            if (add) {
                addon.add(click);
            } else {
                addon.save(click);
            }
            p.sendMessage("§aItens salvos!");
            new MenuEditPacotes(click, addon).open(player);

        }).open(player);
    }


}
