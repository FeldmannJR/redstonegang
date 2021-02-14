package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus;

import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroCache;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroItem;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class FerreiroMenu extends Menu {
    FerreiroAddon addon;

    public FerreiroMenu(Player player, FerreiroAddon addon) {
        super(addon.getNpcName(), 5);
        this.addon = addon;
        addButtons(player);
        addItens(player);
    }

    public void addItens(Player player) {
        for (int x = 27; x < 27 + 18; x++) {
            removeButton(x);
        }
        FerreiroCache cache = addon.getCache(player);
        int slot = 27;
        for (FerreiroItem item : cache.sorted()) {
            add(slot++, new FerreiroButton(item) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    if (item.canRemove()) {
                        if (addon.retiraItem(p, item)) {
                            addItens(p);
                        }
                    }
                }
            });
        }
    }

    public int countRepair(Player p) {
        int x = 0;
        for (ItemStack it : p.getInventory()) {
            if (it != null && it.getType() != Material.AIR) {
                if (addon.canRepair(it)) {
                    x++;
                }
            }
        }
        return x;
    }

    public void addButtons(Player player) {
        add(11, new Button(
                ItemBuilder.item(Material.WORKBENCH)
                        .name(C.item("Fazer Items"))
                        .lore(C.itemDesc("Clique aqui para o"), C.itemDesc(addon.getNpcName() + " fazer itens"), C.itemDesc("para você")).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new CraftMenu(addon).open(p);
            }
        });
        if (addon.getUser(player).hasPermission(addon.CAN_DISENCHANT)) {
            add(13, new Button(
                    CItemBuilder.item(Material.ENCHANTED_BOOK)
                            .name("Desencantar Item")
                            .descBreak("Clique aqui para desencantar itens!")
                            .build()
            ) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    new SelectItemDisenchant(p, addon).open(p);
                }
            });
        } else {
            add(13,
                    CItemBuilder
                            .item(Material.BARRIER)
                            .name(MsgType.ERROR, "Desencantar Item")
                            .descBreak("Somente vips podem desencantar itens no " + addon.getNpcName() + "!")
                            .build());
        }
        add(15, new Button(
                ItemBuilder.item(Material.ANVIL)
                        .name(C.item("Reparar Items"))
                        .lore(C.itemDesc("Clique aqui para o"), C.itemDesc(addon.getNpcName() + " reparar itens"), C.itemDesc("para você")).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {

                if (countRepair(p) == 0) {
                    C.error(p, "Você não tem um item no seu inventário com menos de 50% da durabilidade!");
                    return;
                }
                new SelectItemReparo(p, addon).open(p);
            }
        });
    }

    int tick = 0;

    @Override
    public void onTick() {
        tick++;
        if (tick != 20) return;
        tick = 0;
        for (Button b : getButtons()) {
            if (b instanceof FerreiroButton) {
                ((FerreiroButton) b).updateTime();
            }
        }
    }


}
