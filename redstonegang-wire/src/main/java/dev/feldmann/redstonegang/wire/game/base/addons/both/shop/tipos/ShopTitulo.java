package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.tipos;


import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ShopTitulo extends ShopValuable {

    public String titulo = "";
    public boolean allColors = true;

    @Override
    public boolean compraPacote(Player p) {
        return false;
    }

    @Override
    public void click(Player p) {
      /*  MenuVerPacote m = new MenuVerPacote("Comprar Titulo", this, 3, addon);
        m.removeButton(8);
        int pode = 0;
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                MaterialData data = Cores.getData(c);
                if (!Titulos.have(p, titulo, c)) {
                    ItemStack it = data.toItemStack(1);
                    ItemUtils.SetItemName(it, "§fComprar cor: " + c + Cores.getNome(c));
                    m.addButtonToSquare(9, 26, new Button(0, it) {
                        @Override
                        public void click(Player player, Menu menu, ClickType clickType) {
                            compraCor(p, c);
                        }
                    });
                    pode++;
                }
            }
        }
        if (pode == 0) {
            m.addButton(new NothingButton(0, ItemBuilder.item(Material.BARRIER).name("§7Você já comprou todos!").build()));
        }
        m.open(p);*/
    }

    public void editPacket(Menu m) {/*
        super.editPacket(m);
        ShopTitulo t = this;
        m.addButtonNextSlot(new Button(ItemBuilder.item(Material.NAME_TAG).name("§aSetar Titulo").lore("§f" + titulo).build()) {
            @Override
            public void click(Player player, Menu menu, ClickType clickType) {
                new AnvilGUI(EpicSurvival.instancia, player, nome, (p, string) -> {
                    if (fail) return null;
                    t.titulo = string;
                    ShopManager.save(t);
                    new MenuEditPacotes(t).open(p);
                    return null;
                }, 60);
            }
        });*/
    }

    public void compraCor(Player p, ChatColor cor) {
        /*
        new Venda("Titulo " + titulo + " cor " + Cores.getNome(cor), m, preco, new ItemStack(Material.NAME_TAG), "") {
            @Override
            public boolean compra(Player player, Inventory inventory) {
                TituloDB.addTitulo(player.getUniqueId(), titulo, cor);
                comprou(p);
                ShopManager.openPlayer(player, pageid);
                return true;
            }

            @Override
            public void sai(Player player) {
                ShopManager.openPlayer(player, pageid);
            }
        }.create(p);
*/
    }
}
