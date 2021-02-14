package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.menus;

import dev.feldmann.redstonegang.common.utils.formaters.time.TimeUtils;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroAddon;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.FerreiroCraft;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CraftMenu extends Menu {

    FerreiroAddon addon;

    public CraftMenu(FerreiroAddon addon) {
        super(addon.getNpcName(), 3);
        this.addon = addon;

    }

    @Override
    public void open(Player p) {
        removeAllButtons();
        addCrafts(p);
        super.open(p);
    }

    public void addCrafts(Player p) {
        add(18, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                new FerreiroMenu(p, addon).open(p);
            }
        });
        for (FerreiroCraft craft : addon.ferreiroItems) {
            ItemStack item = craft.getResult().clone();
            ItemUtils.addLore(item, C.msg(MsgType.BUY, "Mão de obra: ##", craft.getPreco()));
            ItemUtils.addLore(item, C.itemDesc("Tempo: %%", TimeUtils.millisToString(addon.getTempo(craft.getTempo(), p))));

            ItemUtils.addLore(item, C.item("Precisa:"));
            for (ItemStack precisa : craft.getPrecisa()) {
                ItemUtils.addLore(item, " §7- §f" + precisa.getAmount() + "x §b" + LanguageHelper.getItemName(precisa));
            }
            addNext(new Button(item) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    addon.craft(p, craft);
                    new FerreiroMenu(p, addon).open(p);
                }
            });
        }
    }

}
