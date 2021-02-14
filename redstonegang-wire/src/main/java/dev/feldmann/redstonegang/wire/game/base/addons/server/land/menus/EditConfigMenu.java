package dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlag;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.CItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class EditConfigMenu extends Menu {

    private final Land t;
    private Cooldown changeConfig = new Cooldown(500);
    private boolean adm;
    LandAddon manager;

    public EditConfigMenu(LandAddon manager, Land t) {
        this(manager, t, false);
    }

    public EditConfigMenu(LandAddon manager, Land t, boolean adm) {
        super("Editando Terreno", 4);
        this.t = t;
        this.manager = manager;
        this.adm = adm;
        addButtons();
    }

    public boolean can(Player p) {
        if (adm) {
            return p.hasPermission(LandAddon.TERRENOS_ADMIN);
        } else {
            return manager.canEditTerreno(p, t);
        }
    }

    public void addButtons() {
        for (LandFlag prop : manager.getFlagsManager().all()) {
            addNext(new Button(buildItemStack(prop, t.getFlags().get(prop))) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    if (can(p)) {
                        if (changeConfig.isCooldown(p.getUniqueId())) {
                            return;
                        }
                        if (!manager.getFlagsManager().canEdit(t.getOwner(), prop)) {
                            C.error(p, "Você não tem permissão para editar esta opção!");
                            return;
                        }
                        t.getFlags().set(prop, !t.getFlags().get(prop));
                        setItemStack(buildItemStack(prop, t.getFlags().get(prop)));
                        t.saveProperties();
                        changeConfig.addCooldown(p.getUniqueId());
                    } else {
                        p.closeInventory();
                    }

                }
            });
        }
        int slot = 18;
        for (PlayerProperty prop : PlayerProperty.values()) {
            if (prop.isAll()) {
                add(slot++, new Button(buildItemStack(prop, t.getFlags().get(prop))) {
                    @Override
                    public void click(Player p, Menu m, ClickType click) {
                        if (changeConfig.isCooldown(p.getUniqueId())) {
                            return;
                        }
                        t.getFlags().set(prop, !t.getFlags().get(prop));
                        setItemStack(buildItemStack(prop, t.getFlags().get(prop)));
                        t.saveProperties();
                        changeConfig.addCooldown(p.getUniqueId());
                    }
                });
            }
        }
        add(getSize() - 1, new Button(ItemBuilder.item(Material.SKULL_ITEM).playerSkull("Steve").name(C.item("Membros")).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (can(p)) {
                    new ListMembersMenu(manager, t, adm).open(p);
                } else {
                    p.closeInventory();
                }
            }
        });
    }

    public ItemStack buildItemStack(LandFlag prop, boolean ativo) {

        CItemBuilder builder = CItemBuilder.item(prop.getIcon().clone());
        builder.descBreak(prop.getDescription());
        if (ativo) {
            builder.name(MsgType.ITEM_CAN, prop.getName());
            builder.lore(MsgType.ON, "Ativo");
        } else {
            builder.name(MsgType.ITEM_CANT, prop.getName());
            builder.lore(MsgType.OFF, "Desativado");
        }
        if (!this.manager.getFlagsManager().canEdit(t.getOwner(), prop)) {
            builder.lore(MsgType.ERROR, "Você não tem permissão!");
        }
        return builder.build();
    }

    public ItemStack buildItemStack(PlayerProperty prop, boolean b) {

        CItemBuilder builder = CItemBuilder.item(prop.getItem().clone());
        builder.descBreak("Esta configuração altera se todos os jogadores do servidor podem fazer está ação no seu terreno!");
        if (b) {
            builder.name(MsgType.ITEM_CAN, prop.getName());
            builder.lore(MsgType.ON, "Ativo");
        } else {
            builder.name(MsgType.ITEM_CANT, prop.getName());
            builder.lore(MsgType.OFF, "Desativado");
        }
        return builder.build();
    }
}
