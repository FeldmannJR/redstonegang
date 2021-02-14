package dev.feldmann.redstonegang.wire.game.base.objects.exceptions;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

public class HardDependencyNotFoundException extends Exception {



    public HardDependencyNotFoundException(Class<? extends Addon> addon, Class<? extends Addon> dependency) {
        super("Hard dependency(" + dependency.getSimpleName() + ") not found in addon" + addon.getSimpleName());
    }


}
