package dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.menus;

import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.ConfigurableMapAPI;
import dev.feldmann.redstonegang.wire.game.base.apis.configurablemap.internal.ConfigurableNPC;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class SelectNpcMenu extends MultiplePageMenu<String> {
    ConfigurableMapAPI api;

    public SelectNpcMenu(ConfigurableMapAPI api) {
        super("Escolher Npc");
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
        NPC atual = api.getConfig(name, ConfigurableNPC.class);
        String atualName = atual != null ? atual.getName() : "Não setado";
        return new Button(ItemBuilder.item(Material.SKULL_ITEM).skull(SkullType.CREEPER).name(C.item(name)).lore(C.itemDesc("Atual: %%", atualName)).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                NPC select = CitizensAPI.getDefaultNPCSelector().getSelected(p);
                if (select == null) {
                    C.error(p, "Você precisa selecionar um npc com /npc sel!");
                    p.closeInventory();
                    return;
                }
                api.setConfig(name, ConfigurableNPC.class, select);
                p.closeInventory();
                C.info(p, "Setado!");
            }
        };
    }


    @Override
    public List<String> getAll() {
        List<String> npc = new ArrayList<>();
        for (String name : api.getRequired().keySet()) {
            if (api.getRequired().get(name).equals(ConfigurableNPC.class)) {
                npc.add(name);
            }
        }
        return npc;
    }
}
