package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroAddon;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SellMenu;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class SelectItemReparo extends Menu {


    FerreiroAddon addon;

    public SelectItemReparo(Player p, FerreiroAddon addon) {
        super("Selecionar Item", 4);
        this.addon = addon;
        addItens(p);
        add(27, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new FerreiroMenu(p, addon).open(p);
            }
        });

    }


    public void addItens(Player p) {
        for (ItemStack it : p.getInventory()) {
            if (it != null && it.getType() != Material.AIR) {

                ItemStack clone = it.clone();
                if (addon.canRepair(it)) {
                    ItemStack display = clone.clone();
                    int reparos = addon.getReparada(clone);
                    int preco = (addon.precoReparo) * (reparos != 0 ? ((reparos) * addon.multiplicadorPorReparo) : 1);
                    ItemUtils.addLore(display, C.msg(MsgType.BUY, "Mão de obra: ##", preco));
                    ItemUtils.addLore(display, C.itemDesc("Tempo: %%", TimeUtils.millisToString(addon.getTempoReparo(clone, p))));

                    addNext(new Button(display) {
                        @Override
                        public void click(Player p, Menu m, ClickType click) {
                            if (!InventoryHelper.inventoryContains(p.getInventory(), clone, clone.getAmount())) {
                                C.error(p, "Ocorreu um erro!");
                                p.closeInventory();
                            }


                            new SellMenu("Reparar item", addon.a(EconomyAddon.class).getCurrency(), preco, display) {
                                @Override
                                public boolean compra(Player p, Menu m) {
                                    if (!InventoryHelper.inventoryContains(p.getInventory(), clone, clone.getAmount())) {
                                        C.error(p, "Ocorreu um erro!");
                                        p.closeInventory();
                                    }
                                    if (addon.repara(p, clone)) {
                                        InventoryHelper.removeInventoryItems(p.getInventory(), clone, clone.getAmount());
                                        return true;
                                    }
                                    p.closeInventory();
                                    return false;
                                }

                                @Override
                                public void quit(Player p, QuitType type) {
                                    if (type == QuitType.SUCCESS) {
                                        new FerreiroMenu(p, addon).open(p);
                                    } else if (type == QuitType.BACK) {
                                        new SelectItemReparo(p, addon).open(p);
                                    }
                                }
                            }.open(p);


                        }
                    });
                }
            }
        }

    }
}
