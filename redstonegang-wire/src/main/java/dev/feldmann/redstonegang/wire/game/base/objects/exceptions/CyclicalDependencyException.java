package dev.feldmann.redstonegang.wire.game.base.objects.exceptions;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

public class CyclicalDependencyException extends Exception {

    Class<? extends Addon> addon;

    public CyclicalDependencyException(Class<? extends Addon> addon) {
        super("Addon with cyclic dependency " + addon.getSimpleName());
        this.addon = addon;
    }

    public Class<? extends Addon> getAddon() {
        return addon;
    }
}
