package dev.feldmann.redstonegang.wire.modulos.citizens.commands;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Op;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.SelectCustomSkinMenu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.MobType;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.util.NMS;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NpcSkin extends PlayerCmd {
    public NpcSkin() {
        super("npcskin", "seta uma skin custom no npc");
        setExecutePerm(Op.INSTANCE);
    }

    @Override
    public void command(Player player, Arguments args) {
        if (validate(player) == null) {
            return;
        }
        Response<String[]> skins = RedstoneGang.instance().webapi().skins().custom();
        if (skins.hasFailed()) {
            C.error(player, "Ocorreu um erro ao baixar as skins!");
            return;
        }
        new SelectCustomSkinMenu(skins.collect()) {
            @Override
            public void selectSkin(Player p, String skin) {
                NPC npc = validate(p);
                if (npc != null) {
                    setSkin(npc, "rg-" + skin);
                    C.info(p, "Skin %% setada!", skin);
                    p.closeInventory();
                }

            }
        }.open(player);
    }

    private NPC validate(Player player) {
        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(player);
        if (npc == null) {
            C.error(player, "Você não tem um npc selecionado! Use /npc sel!");
            return null;
        }

        EntityType type = ((MobType) npc.getTrait(MobType.class)).getType();
        if (type != EntityType.PLAYER) {
            C.error(player, "Este npc não é um jogador!");
            return null;
        }
        return npc;
    }

    private void setSkin(NPC npc, String name) {

        npc.data().setPersistent("player-skin-name", name);
        if (npc.isSpawned()) {
            SkinnableEntity skinnable = NMS.getSkinnable(npc.getEntity());
            if (skinnable != null) {
                skinnable.setSkinName(name);
            }
        }
    }
}
