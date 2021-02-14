package dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandPlayer;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.CloseButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectUserButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class ListGlobalMenu extends MultiplePageMenu<PlayerProperties> {

    int pId;
    private Button prev;
    LandAddon manager;

    public ListGlobalMenu(LandAddon manager, int pId) {
        super("Amigo Terrenos");
        this.pId = pId;
        prev = new CloseButton();
        this.manager = manager;
        addPlayerButton(49);
    }

    public void addPlayerButton(int slot) {
        add(slot, new SelectUserButton(CItemBuilder.item(Material.EMERALD)
                .name("Adicionar")
                .descBreak("Adiciona um jogador para poder mexer em TODOS os seus terrenos!")
                .build()) {
            @Override
            public void reject(Player p) {
                new ListGlobalMenu(manager, pId).open(p);
            }

            @Override
            public void accept(Player p, User target) {
                PlayerProperties property = player().getProperty(target.getId());
                if (property == null) {
                    property = player().add(target.getId());
                }
                new EditPlayerProperty(manager, property, 0, false).open(p);
            }
        });
    }

    @Override
    public Button getButton(PlayerProperties ob, int page) {
        String pname = RedstoneGangSpigot.getPlayer(ob.getPlayerId()).getName();
        return new Button(ItemBuilder.item(Material.SKULL_ITEM).name(C.item(pname)).playerSkull(pname).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new EditPlayerProperty(manager, player().getProperty(ob.getPlayerId()), page, false).open(p);
            }
        };
    }

    public LandPlayer player() {
        return manager.getPlayer(pId);
    }

    @Override
    public Button getPreviousButton() {
        return prev;
    }

    @Override
    public List<PlayerProperties> getAll() {
        return new ArrayList(player().getGlobalProps().values());
    }
}
