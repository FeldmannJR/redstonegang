package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.config;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.ConfigButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class StaffConfigMenu extends Menu {

    Survival surv;

    public StaffConfigMenu(Survival surv, User pl) {
        super("Staff Config", 3);
        this.surv = surv;
        add(0, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new ConfigMenu(surv, pl).open(p);
            }
        });
        addConfigs(pl);
    }

    public void addConfigs(User pl) {
        if (pl.permissions().hasPermission(ClanAddon.VIEW_CLAN_MESSAGES)) {
            addNext(new ConfigButton(pl, ItemBuilder.item(Material.EYE_OF_ENDER).name(C.item("Espionar chat dos clans")).build(), surv.a(ClanAddon.class).STAFF_CHAT_CONFIG));
        }
        if (surv.hasAddon(LandAddon.class)) {
            LandAddon terrenos = surv.a(LandAddon.class);
            if (pl.permissions().hasPermission(LandAddon.TERRENOS_ADMIN)) {
                addNext(new ConfigButton(pl, ItemBuilder.item(Material.DIAMOND_SPADE).name(C.item("Quebrar em qualquer lugar nos terrenos")).build(), terrenos.BYPASS_CONFIG));
            }
            if (pl.permissions().hasPermission(LandAddon.TERRENOS_WILD)) {
                addNext(new ConfigButton(pl, ItemBuilder.item(Material.GRASS).name(C.item("Quebrar onde não tem dono")).build(), terrenos.BYPASS_WILD_CONFIG));
            }
        }
    }

}
