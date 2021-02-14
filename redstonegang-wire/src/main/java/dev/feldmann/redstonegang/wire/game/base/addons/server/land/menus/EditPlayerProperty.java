package dev.feldmann.redstonegang.wire.game.base.addons.server.land.menus;

import dev.feldmann.redstonegang.common.player.permissions.PermissionValue;
import dev.feldmann.redstonegang.common.utils.Cooldown;

import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperties;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.BackButton;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class EditPlayerProperty extends Menu {

    PlayerProperties props;
    private Cooldown changeConfig = new Cooldown(500);
    private int prevPage;
    private boolean adm;
    LandAddon manager;

    public EditPlayerProperty(LandAddon manager, PlayerProperties prop, int prevPage, boolean adm) {
        super("Editando " + RedstoneGangSpigot.getPlayer(prop.getPlayerId()).getName() + (prop.getType() == PlayerProperties.PLAYER ? " Global" : ""), 2);
        this.manager = manager;
        this.adm = adm;
        this.props = prop;
        this.prevPage = prevPage;
        addButtons();
    }

    public boolean can(Player p) {
        if (adm) {
            return p.hasPermission(LandAddon.TERRENOS_ADMIN);
        } else {

            return manager.canEditTerreno(p, manager.getTerrenoById(props.getOwnerId()));
        }
    }

    public void addButtons() {
        for (PlayerProperty prop : PlayerProperty.values()) {
            addNext(new Button(buildItemStack(prop, props.get(prop))) {
                @Override
                public void click(Player p, Menu m, ClickType click) {

                    if (!changeConfig.isCooldown(p.getUniqueId())) {
                        if (props.getType() == PlayerProperties.TERRENO) {
                            if (!can(p)) {
                                p.closeInventory();
                            }
                        }

                        PermissionValue val = props.get(prop);
                        if (val == PermissionValue.ALLOW) {
                            val = PermissionValue.DENY;
                        } else if (val == PermissionValue.DENY) {
                            if (props.getType() == PlayerProperties.TERRENO) {
                                val = PermissionValue.NONE;
                            } else {
                                val = PermissionValue.ALLOW;
                            }
                        } else if (val == PermissionValue.NONE) {
                            val = PermissionValue.ALLOW;
                        }
                        props.set(prop, val);
                        setItemStack(buildItemStack(prop, props.get(prop)));
                        changeConfig.addCooldown(p.getUniqueId());
                    }


                }
            });
        }


        //Remove button
        add(getSize() - 1, new Button(ItemBuilder.item(Material.BARRIER).name(C.msg(MsgType.ITEM_CANT, "Remover")).build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (props.getType() == PlayerProperties.TERRENO) {
                    int tid = props.getOwnerId();
                    Land terreno = manager.getTerrenoById(tid);
                    if (can(p)) {
                        terreno.removePlayerPropery(props.getPlayerId());
                        p.closeInventory();
                    } else {
                        p.closeInventory();
                    }
                } else {
                    manager.getPlayer(props.getOwnerId()).removeProperty(props.getPlayerId());
                    p.closeInventory();
                }
            }
        });
        //Voltar para os membros
        add(getSize() - 9, new BackButton() {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (props.getType() == PlayerProperties.TERRENO) {
                    int tid = props.getOwnerId();
                    Land terreno = manager.getTerrenoById(tid);
                    if (can(p)) {
                        new ListMembersMenu(manager, terreno, adm).open(p, prevPage);
                    } else {
                        p.closeInventory();
                    }
                } else {
                    new ListGlobalMenu(manager, props.getOwnerId()).open(p);
                }
            }
        });
    }

    public ItemStack buildItemStack(PlayerProperty prop, PermissionValue value) {

        ItemStack it = prop.getItem().clone();
        if (value == PermissionValue.ALLOW) {
            ItemUtils.setItemName(it, C.msg(MsgType.ITEM_CAN, prop.getName()));
            ItemUtils.addLore(it, C.msg(MsgType.ON, "Ativo"));
        } else if (value == PermissionValue.DENY) {
            ItemUtils.setItemName(it, C.msg(MsgType.ITEM_CANT, prop.getName()));
            ItemUtils.addLore(it, C.msg(MsgType.OFF, "Desativado"));
        } else {
            ItemUtils.setItemName(it, C.msg(MsgType.ITEM, prop.getName()));
            ItemUtils.addLore(it, C.itemDesc("Usar configuração global"));

        }
        return it;
    }

}
