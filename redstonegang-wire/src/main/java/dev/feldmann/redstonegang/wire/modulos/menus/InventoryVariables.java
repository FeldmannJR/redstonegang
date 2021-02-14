/*
 *  _   __   _   _____   _____       ___       ___  ___   _____  
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___| 
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |     
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |     
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___  
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____| 
 * 
 * Projeto feito por Carlos Andre Feldmann Junior, Isaias Finger e Gabriel Augusto Souza
 */
package dev.feldmann.redstonegang.wire.modulos.menus;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;

/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public class InventoryVariables {

    public static HashMap<Integer, HashMap<String, Object>> variables = new HashMap();

    public static Object getObject(Inventory i, String name) {
        if (getHashMap(i).containsKey(name)) {
            return getHashMap(i).get(name);
        }
        return null;
    }

    public static void setObject(Inventory i, String name, Object ob) {
        getHashMap(i).put(name, ob);
    }

    public static HashMap<String, Object> getHashMap(Inventory i) {
        if (!variables.containsKey(i.hashCode())) {
            variables.put(i.hashCode(), new HashMap<String, Object>());
        }
        return variables.get(i.hashCode());

    }

}
