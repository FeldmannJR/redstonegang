/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package dev.feldmann.redstonegang.wire.modulos.language.locale;

import org.bukkit.entity.Player;

/**
 * Created by Meow J on 6/20/2015.
 * <p>
 * Language helper
 *
 * @author Meow J
 */
public class LocaleHelper {

    /**
     * Return the language of the player
     *
     * @param player The player to be analyzed
     * @return the language of the player(in Java locale format)
     */
    public static String getPlayerLanguage(Player player) {
        return "pt_BR";
    }
}
