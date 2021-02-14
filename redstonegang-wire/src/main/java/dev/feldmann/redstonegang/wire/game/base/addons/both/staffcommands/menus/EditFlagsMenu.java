package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.menus;

import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.StaffCommandsAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands.VanishFlag;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class EditFlagsMenu extends Menu {

    StaffCommandsAddon addon;

    public EditFlagsMenu(StaffCommandsAddon addon) {
        super("Editar Flags", 1);
        this.addon = addon;
    }

    @Override
    public void open(Player p) {
        for (VanishFlag flag : VanishFlag.values()) {
            addFlag(flag, p);
        }
        super.open(p);
    }

    public void addFlag(VanishFlag flag, Player player) {
        addNext(new Button(getItemStack(flag, addon.getFlag(player, flag))) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                boolean set = !addon.getFlag(p, flag);
                addon.setFlag(p, flag, set);
                setItemStack(getItemStack(flag, set));
            }
        });
    }

    private ItemStack getItemStack(VanishFlag flag, boolean ativo) {
        ItemBuilder builder = CItemBuilder.item(ativo ? Material.EMERALD : Material.BARRIER)
                .name(StringUtils.capitalizeFirstChar(flag.getAction()));
        if (ativo) {
            builder.lore(MsgType.ON, "Pode");
        } else {
            builder.lore(MsgType.OFF, "Não Pode");
        }
        return builder.build();
    }


}
