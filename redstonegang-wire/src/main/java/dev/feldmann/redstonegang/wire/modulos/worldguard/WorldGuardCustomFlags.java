package dev.feldmann.redstonegang.wire.modulos.worldguard;

import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.integrations.CustomFlags;


public class WorldGuardCustomFlags extends Modulo {
    boolean have = false;
    CustomFlags custom;

    @Override
    public void onLoad() {
        try {
            Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin");
            have = true;
        } catch (ClassNotFoundException e) {
            have = false;
        }
        if (have) {
            custom = new CustomFlags();
            custom.onLoad();
        }
    }

    @Override
    public void onEnable() {
        if(custom!=null){
            custom.onEnable();
        }
    }

    @Override
    public void onDisable() {

    }
}
