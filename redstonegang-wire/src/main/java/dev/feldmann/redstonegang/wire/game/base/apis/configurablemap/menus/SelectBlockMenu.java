package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.menus;

import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal.ConfigurableBlock;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectBlockMenu extends MultiplePageMenu<String> {
    ConfigurableMapAPI api;

    public SelectBlockMenu(ConfigurableMapAPI api) {
        super("Escolher Bloco");
        this.api = api;
    }

    @Override
    public Button getButton(String name, int page) {

        //  for (String name : redstonegang.getRequired().keySet()) {
        //            if (redstonegang.getRequired().get(name).equals(ConfigurableNPC.class)) {
        //                NPC atual = redstonegang.getConfig(name, ConfigurableNPC.class);
        //
        //            }
        //        }
        Block atual = api.getConfig(name, ConfigurableBlock.class);
        String atualName = atual != null ? atual.getWorld().getName() + "-" + atual.getX() + ":" + atual.getY() + ":" + atual.getZ() : "Não setado";
        return new Button(ItemBuilder.item(Material.DIRT).name(C.item(name)).lore(C.itemDesc("Atual: %%", atualName)).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                Block target = p.getTargetBlock((Set<Material>) null, 10);
                if (target == null) {
                    p.sendMessage("Olha pro bloco!");
                    return;
                }
                api.setConfig(name, ConfigurableBlock.class, target);
                p.closeInventory();
                C.info(p, "Setado!");
            }
        };
    }


    @Override
    public List<String> getAll() {
        List<String> npc = new ArrayList<>();
        for (String name : api.getRequired().keySet()) {
            if (api.getRequired().get(name).equals(ConfigurableBlock.class)) {
                npc.add(name);
            }
        }
        return npc;
    }
}
