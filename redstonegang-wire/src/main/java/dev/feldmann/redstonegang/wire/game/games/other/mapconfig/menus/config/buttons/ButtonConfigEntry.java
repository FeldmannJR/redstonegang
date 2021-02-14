package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapConfig;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigInfo;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ButtonConfigEntry extends ButtonEntry {


    public ButtonConfigEntry(MapConfigEntry en, MapConfigGame game, int page) {
        super(en, game, page);
    }

    @Override
    public void click(Player p, Menu m, ClickType click) {
        MapConfigInfo pinfo = game.config().getInfo(p);
        pinfo.editandoConfig = en.getNome();
        p.sendMessage("§eDigita no chat o valor da config " + en.getNome() + ", caso queira cancelar digita x");
        p.closeInventory();
    }


    @Override
    public boolean exists(MapConfig conf, MapConfigEntry en) {
        return conf.getKeysConfigs().contains(en.getNome().toLowerCase());
    }

    @Override
    public Material getIcon() {
        return Material.COMMAND;
    }

    @Override
    public ItemStack gera(MapConfigEntry en, MapConfigGame game) {
        ItemStack it = super.gera(en, game);
        ItemUtils.addLore(it, "§fAtual: §e" + game.getConfigurando().getConfig().getConfig(en.getNome()));
        return it;
    }
}
