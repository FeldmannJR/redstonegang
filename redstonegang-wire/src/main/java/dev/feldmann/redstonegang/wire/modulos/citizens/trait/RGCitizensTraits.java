package dev.feldmann.redstonegang.wire.modulos.citizens.trait;

import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.modulos.citizens.commands.NpcSkin;
import dev.feldmann.redstonegang.wire.modulos.citizens.trait.commands.NpcBook;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;

public class RGCitizensTraits extends Modulo {

    public static void register() {

    }

    @Override
    public void onEnable() {
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(BookTrait.class));
        register(new NpcBook());
        register(new NpcSkin());
    }

    @Override
    public void onDisable() {

    }
}
