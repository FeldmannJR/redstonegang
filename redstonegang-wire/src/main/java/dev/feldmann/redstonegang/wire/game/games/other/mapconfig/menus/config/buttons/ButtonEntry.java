package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapConfig;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class ButtonEntry extends Button {

    MapConfigGame game;
    MapConfigEntry en;
    int page;

    public ButtonEntry(MapConfigEntry en, MapConfigGame game, int page) {
        super(null);
        this.game = game;
        this.en = en;
        this.page = page;
        this.setItemStack(gera(en, game));
    }

    public ItemStack gera(MapConfigEntry en, MapConfigGame game) {
        String cor = "§c";
        String setado;
        if (en.getOption() == SaveOption.OPTIONAL) {
            cor = "§e";
        }
        MapConfig config = game.getConfigurando().getConfig();
        if (exists(config, en)) {
            cor = "§a";
            setado = "§aExiste!";
        } else {
            setado = "§eNão Existe!";
        }
        ItemStack it = ItemBuilder.item(getIcon()).name(cor + en.getNome()).lore("§f" + en.getDesc(), en.getOption().getNome(), setado).build();
        if (en.getOption() == SaveOption.SEQUENTIAL) {
            ItemUtils.addLore(it, "§7Para colocar em sequencia clique com shift!");
        }
        return it;
    }

    public boolean checkSeq(Player p, ClickType tipo) {
        if (en.getOption() == SaveOption.SEQUENTIAL && tipo.isShiftClick()) {
            game.config().getInfo(p).seq = en;
            p.sendMessage("§dPara sair deste modo use shift+click!");
            p.closeInventory();
            return true;
        }
        return false;
    }

    public abstract boolean exists(MapConfig conf, MapConfigEntry en);

    public abstract Material getIcon();
}
