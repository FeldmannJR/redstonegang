package dev.feldmann.redstonegang.wire.modulos.citizens.trait.commands;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.perm.Op;
import dev.feldmann.redstonegang.wire.base.cmds.types.PlayerCmd;
import dev.feldmann.redstonegang.wire.modulos.citizens.trait.BookTrait;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NpcBook extends PlayerCmd {
    public NpcBook() {
        super("npcbook", "seta pra mostrar um livro no npc");
        setExecutePerm(Op.INSTANCE);
    }

    @Override
    public void command(Player player, Arguments args) {
        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(player);
        if (npc == null) {
            C.error(player, "Você não tem um npc selecionado! Use /npc sel!");
            return;
        }
        if (!npc.hasTrait(BookTrait.class)) {
            npc.addTrait(BookTrait.class);
        }
        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() != Material.WRITTEN_BOOK) {
            C.error(player, "Você precisa estar com um livro na mão!");
            return;
        }
        npc.getTrait(BookTrait.class).setBook(item.clone());

        C.info(player, "Livro setado no npc!");
    }
}
