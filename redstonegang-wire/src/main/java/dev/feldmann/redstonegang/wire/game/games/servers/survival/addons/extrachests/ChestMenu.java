package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests;

import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ChestMenu extends Menu {
    ChestData data;
    public boolean closed = false;

    public ChestMenu(ChestData data) {
        super(data.getType().getNome(), data.getType() == ChestList.ENDERCHEST ? data.getType().getSize() + 9 : data.getType().getSize());
        this.data = data;
        setMoveItens(true);
        for (int x = 0; x < data.getItens().length; x++) {
            if (data.getItens()[x] != null)
                addItemStack(x, data.getItens()[x]);
        }
        addClose(this::close);
        if (data.getType() == ChestList.ENDERCHEST) {
            int start = data.getType().getSize();
            for (ChestList c : ChestList.values()) {
                if (c.getSlot() != null)
                    add(start + c.getSlot(), new Button(c.buildItemstack(data.getpId())) {
                        @Override
                        public void click(Player p, Menu m, ClickType click) {
                            if (c.hasPermission(data.getpId())) {
                                data.getManager().getPlayer(data.getpId()).getData(c).getMenu().open(p);
                            }
                        }
                    });
            }
            for (int x = start; x < start + 9; x++) {
                if (getButton(x) == null)
                    add(x, new DummyButton(ItemBuilder.item(Material.STAINED_GLASS_PANE).color(DyeColor.GRAY).name(" ").build()));
            }
        }
    }


    public void close(Player p) {
        if (closed) {
            return;
        }
        for (int x = 0; x < data.getType().getSize(); x++) {
            if (getButton(x) == null) {
                ItemStack it = getContents()[x];
                data.getItens()[x] = it;
            } else {
                data.getItens()[x] = null;
            }
        }
        data.getManager().save(data.getpId(), data.getType());
        if (getViewers() == 1) {
            data.resetMenu();
            closed = true;
        }
    }
}

