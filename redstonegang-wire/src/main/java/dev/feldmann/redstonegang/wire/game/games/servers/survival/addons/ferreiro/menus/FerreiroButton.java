package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroItem;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.inventory.ItemStack;

public abstract class FerreiroButton extends Button {
    FerreiroItem item;

    public FerreiroButton(FerreiroItem item) {
        super(item.buildDisplay());
        this.item = item;
        updateTime();
    }

    public void updateTime() {
        ItemStack item = this.item.buildDisplay();
        if (this.item.canRemove()) {
            ItemUtils.addLore(item, C.msg(MsgType.ITEM_CAN, "Clique aqui para pegar o item!"));
        } else {
            long total = (this.item.getReadyTime().getTime() - this.item.getStartTime().getTime());
            long passou = this.item.getReadyTime().getTime() - System.currentTimeMillis();
            double pct = ((double)passou / (double)total);
            item.setDurability((short) ((float)item.getType().getMaxDurability() * pct));

            ItemUtils.addLore(item, C.msg(MsgType.ITEM_CANT, "Pode pegar em:"));
            ItemUtils.addLore(item, C.msg(MsgType.ITEM_DESC, "%%", TimeUtils.millisToString(this.item.getReadyTime().getTime() - System.currentTimeMillis())));

        }
        setItemStack(item);
    }


}
