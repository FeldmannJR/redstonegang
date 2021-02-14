package dev.feldmann.redstonegang.wire.game.base.objects.annotations;

import dev.feldmann.redstonegang.wire.game.base.objects.API;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Dependencies {
    Class<? extends Addon>[] hard() default {};
    Class<? extends Addon>[] soft() default {};
    Class<? extends API>[] apis() default {};

}
