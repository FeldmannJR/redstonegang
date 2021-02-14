package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapConfig;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigInfo;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ButtonBlockEntry extends ButtonEntry {


    public ButtonBlockEntry(MapConfigEntry en, MapConfigGame game, int page) {
        super(en, game, page);
    }

    @Override
    public void click(Player p, Menu m, ClickType click) {
        if (checkSeq(p, click)) {
            return;
        }
        MapConfigInfo pinfo = game.config().getInfo(p);
        if (pinfo.block == null) {
            p.sendMessage("§cVocê precisa selecionar uma bloco antes!");
            return;
        }

        Mapa configurando = game.getConfigurando();
        if (configurando != null) {
            String nome = en.getNome();
            if (en.getOption() == SaveOption.SEQUENTIAL) {
                nome = game.getNextName(game.getConfigurando().getConfig().getKeysLocations(), nome);
            }
            configurando.getConfig().addLocation(nome, new Location(game.getConfigurando().getWorld(), pinfo.block.getX(), pinfo.block.getY(), pinfo.block.getZ()));
            game.update();
            p.sendMessage("Bloco " + nome + " adicionado");
            pinfo.block = null;
            ((MultiplePageMenu) m).loadPage(page);
        }
    }


    @Override
    public boolean exists(MapConfig conf, MapConfigEntry en) {
        return conf.getKeysLocations().contains(en.getNome());
    }

    @Override
    public Material getIcon() {
        return Material.STONE;
    }
}
