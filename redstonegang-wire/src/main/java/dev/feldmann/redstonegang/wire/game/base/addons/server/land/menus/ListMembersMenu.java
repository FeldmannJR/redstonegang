package dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.SelectUserButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class ListMembersMenu extends MultiplePageMenu<PlayerProperties> {
    LandAddon manager;
    Land ter;
    private Button prev;
    private boolean adm;

    public ListMembersMenu(LandAddon manager, Land t) {
        this(manager, t, false);
    }

    public ListMembersMenu(LandAddon manager, Land t, boolean adm) {
        super("Membros Terreno");
        this.ter = t;
        this.manager = manager;
        this.adm = adm;
        prev = new BackButton("Voltar para a config do terreno") {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (can(p)) {
                    new EditConfigMenu(manager, ter).open(p);
                } else {
                    p.closeInventory();
                }
            }
        };
        addPlayerButton(49);
    }

    public void addPlayerButton(int slot) {
        add(slot, new SelectUserButton(CItemBuilder.item(Material.EMERALD)
                .name("Adicionar Membro")
                .descBreak("Adiciona um jogador para poder mexer somente neste terreno!")
                .build()) {
            @Override
            public void reject(Player p) {
                reopen(p);
            }

            @Override
            public void accept(Player p, User target) {
                if (can(p)) {
                    PlayerProperties properties = ter.getPlayerProperty(target.getId());
                    new EditPlayerProperty(manager, properties, 0, adm).open(p);
                } else {
                    p.closeInventory();
                }
            }
        });
    }

    public void reopen(Player p) {
        new ListMembersMenu(this.manager, this.ter, this.adm);
    }


    public boolean can(Player p) {
        if (adm) {
            return p.hasPermission(LandAddon.TERRENOS_ADMIN);
        } else {
            return manager.canEditTerreno(p, ter);
        }
    }

    @Override
    public Button getButton(PlayerProperties ob, int page) {
        String pname = RedstoneGangSpigot.getPlayer(ob.getPlayerId()).getName();
        return new Button(ItemBuilder.item(Material.SKULL_ITEM).name(C.item(pname)).playerSkull(pname).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (can(p)) {
                    new EditPlayerProperty(manager, ob, page, adm).open(p);
                } else {
                    p.closeInventory();
                }
            }
        };
    }

    @Override
    public Button getPreviousButton() {
        return prev;
    }

    @Override
    public List<PlayerProperties> getAll() {
        return new ArrayList(ter.getPlayerProps().values());
    }
}
