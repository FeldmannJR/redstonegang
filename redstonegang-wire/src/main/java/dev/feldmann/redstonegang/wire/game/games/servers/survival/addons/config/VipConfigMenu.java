package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.config;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops.ChestShopAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.Survival;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.ConfigButton;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class VipConfigMenu extends Menu {

    Survival surv;

    public VipConfigMenu(Survival surv, User pl) {
        super("Configurações VIP", 3);
        this.surv = surv;
        addButtons(pl);
    }

    public void addButtons(User pl) {
        add(13, new ConfigButton(pl, CItemBuilder.item(Material.CHEST).name(C.item("Mensagens Loja")).lore("Mostrar mensagens de", "jogadores usando sua", "loja!").build(), surv.getAddon(ChestShopAddon.class).SHOW_MESSAGES));
        add(0, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new ConfigMenu(surv, pl).open(p);
            }
        });
    }

}
