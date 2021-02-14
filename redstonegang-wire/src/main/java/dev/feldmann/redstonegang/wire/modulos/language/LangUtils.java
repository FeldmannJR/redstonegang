
package dev.feldmann.redstonegang.wire.modulos.language;

import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageRegistry;
import dev.feldmann.redstonegang.wire.modulos.language.lang.convert.EnumLang;

/**
 * Created by Meow J on 6/20/2015.
 *
 * @author Meow J
 */
public class LangUtils extends Modulo {


    public static String defaultLanguage = "pt_BR";

    @Override
    public void onEnable() {

        EnumLang.init();
        LanguageRegistry.INSTANCE = new LanguageRegistry();
        register(new CmdNome());
    }

    @Override
    public void onDisable() {
        EnumLang.clean();
    }

    /**
     * Display a info message
     *
     * @param msg message to be sent
     */
    public void info(String msg) {
        log(msg);
    }

    /**
     * Display a warning message
     *
     * @param msg message to be sent
     */
    public void warn(String msg) {
        log(msg);
    }


}
