package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.player.config.types.EnumConfig;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerChangeConfigEvent;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ConfigButton extends Button {

    public static Cooldown cd = new Cooldown(1000, "altera config");

    private ConfigType config;
    private ItemStack defaultItemstack;
    private User pl;

    public ConfigButton(User pl, ItemStack i, ConfigType config) {
        super(i);
        this.pl = pl;
        this.defaultItemstack = i.clone();
        this.config = config;
        i = updateValue(i);
        setItemStack(i);
    }

    public ItemStack updateValue(ItemStack it) {
        String lore;
        if (config instanceof BooleanConfig) {
            BooleanConfig cfg = (BooleanConfig) config;
            if (pl.getConfig(cfg)) {
                lore = C.msg(MsgType.ON, "Ligado");
            } else {
                lore = C.msg(MsgType.OFF, "Desligado");
            }
        } else if (config instanceof EnumConfig) {
            EnumConfig cfg = (EnumConfig) config;
            String name = cfg.getName((Enum) pl.getConfig(cfg)).replace("_", " ");
            lore = C.msg(MsgType.INFO, name);
        } else {
            lore = "ERROR";
        }
        ItemUtils.addLore(it, lore);
        return it;
    }

    @Override
    public void click(Player p, Menu m, ClickType click) {
        if (cd.isCooldownWithMessage(p.getUniqueId())) {
            return;
        }
        Object atual = pl.getConfig(this.config);
        Object next = config.next(atual);
        if (next != null) {
            if (!Wire.callEvent(new PlayerChangeConfigEvent(p, this.config, atual, next))) {
                pl.setConfig(config, next);
                cd.addCooldown(p.getUniqueId());
                ItemStack newitem = defaultItemstack.clone();
                updateValue(newitem);
                setItemStack(newitem);
            }
        }
    }

}
