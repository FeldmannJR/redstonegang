package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.load;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectStringButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CriarMapaMenu extends Menu {

    MapConfigGame game;

    public CriarMapaMenu(MapConfigGame game) {
        super("Selecionar Game", 6);
        this.game = game;
        for (String folder : game.mapas().getAllFolders()) {
            RedstoneGang.instance().debug("Adicionado folder " + folder);
            addButton(folder);
        }
    }

    public void addButton(String folder) {
        int qtd = game.getMaps(folder).size();
        addNext(new SelectStringButton(CItemBuilder.item(Material.CHEST).name(folder).lore("Existe: " + qtd).build()) {
            @Override
            public void accept(String string, Player p) {
                MapResponse response = game.mapas().create(folder, string);
                if (response == null || response.file) {
                    C.error(p, "Não consegui criar o mapa, possívelmente ele já existia!");
                    return;
                }
                try {
                    C.info(p, "Puxei a info Game:%%  Name:%%", response.game, response.name);
                    if (game.mapas().getFileManager().createNewVoidWorld(response, false)) {
                        C.info(p, "Mapa criado com sucesso!");
                        game.setConfigurando(response);
                    } else {
                        C.error(p, "Mapa não foi criado! Ocorreu um erro!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    C.error(p, "Ocorreu um erro!");
                }


            }
        });
    }


}
