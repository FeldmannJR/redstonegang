package dev.feldmann.redstonegang.wire.base.modulos;

import dev.feldmann.redstonegang.common.utils.ObjectLoader;

import java.util.ArrayList;
import java.util.List;

public class ModuloManager {

    List<Modulo> modulos = new ArrayList();


    public void onLoad() {
        load();
        for (Modulo m : modulos) {
            m.onLoad();
        }
    }

    public void onEnable() {
        for (Modulo m : modulos) {
            m.onEnable();
        }
    }

    public void onDisable() {
        for (Modulo m : modulos) {
            m.onDisable();
        }
        modulos.clear();
    }

    private void load() {
        this.modulos = ObjectLoader.load("dev.feldmann.redstonegang.wire.modulos.", Modulo.class);
    }
}
