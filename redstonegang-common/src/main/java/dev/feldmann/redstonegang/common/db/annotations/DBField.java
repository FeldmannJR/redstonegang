package dev.feldmann.redstonegang.common.db.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface DBField {

    /*
    * Ao mudar este nome ele deixa de pegar o nome do campo e sim este
    * */
    String nome() default "";

    /*
    * Se for uma string não vai usar varchar e sim text
    * */
    boolean isText() default false;

    /*
    * Se for true não vai tentar dar alter table
    * */
    boolean onlyRead() default false;

}
