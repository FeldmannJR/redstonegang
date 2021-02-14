package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapConfig;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigInfo;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ButtonRegionEntry extends ButtonEntry {


    public ButtonRegionEntry(MapConfigEntry en, MapConfigGame game, int page) {
        super(en, game, page);
    }

    @Override
    public boolean exists(MapConfig conf, MapConfigEntry en) {
        return conf.getKeysRegions().contains(en.getNome());
    }

    @Override
    public Material getIcon() {
        return Material.BEACON;
    }

    @Override
    public void click(Player p, Menu m, ClickType click) {
        if (checkSeq(p, click)) {
            return;
        }
        MapConfigInfo pinfo = game.config().getInfo(p);
        if (pinfo.rg1 == null || pinfo.rg2 == null) {
            p.sendMessage("§cVocê precisa selecionar uma região antes!");
            return;
        }
        Mapa configurando = game.getConfigurando();
        if (configurando != null) {
            String nome = en.getNome();
            if (en.getOption() == SaveOption.SEQUENTIAL) {
                nome = game.getNextName(game.getConfigurando().getConfig().getKeysRegions(), nome);
            }
            configurando.getConfig().addRegion(nome, new Hitbox(pinfo.rg1, pinfo.rg2, true));
            pinfo.rg1 = null;
            pinfo.rg2 = null;
            ((MultiplePageMenu) m).loadPage(page);
            p.sendMessage("§eRegião " + nome + " adicionada!");
        }
    }


}
