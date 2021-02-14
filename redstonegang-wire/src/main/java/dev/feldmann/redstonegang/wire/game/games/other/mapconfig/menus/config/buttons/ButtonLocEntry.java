package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons;

import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.MapConfig;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps.Mapa;
import dev.feldmann.redstonegang.wire.game.base.addons.both.npcs.NPCAddon;
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

public class ButtonLocEntry extends ButtonEntry {


    public ButtonLocEntry(MapConfigEntry en, MapConfigGame game, int page) {
        super(en, game, page);
    }

    @Override
    public boolean exists(MapConfig conf, MapConfigEntry en) {
        return conf.getKeysLocations().contains(en.getNome());
    }

    @Override
    public Material getIcon() {
        return Material.BANNER;
    }

    @Override
    public void click(Player p, Menu m, ClickType click) {
        if (checkSeq(p, click)) {
            return;
        }
        MapConfigInfo pinfo = game.config().getInfo(p);
        if (pinfo.loc == null) {
            p.sendMessage("§cVocê precisa selecionar uma localização antes!");
            return;
        }

        Mapa configurando = game.getConfigurando();
        if (configurando != null) {
            String nome = en.getNome();
            if (en.getOption() == SaveOption.SEQUENTIAL) {
                nome = game.getNextName(game.getConfigurando().getConfig().getKeysLocations(), nome);
            }
            Location loc = pinfo.loc;
            pinfo.loc = null;
            game.a(NPCAddon.class).removerNPC(pinfo.locnpc);

            configurando.getConfig().addLocation(nome, loc);
            game.update();
            p.sendMessage("Location " + nome + " adicionada");
            ((MultiplePageMenu)m).loadPage(page);
        }
    }

}
