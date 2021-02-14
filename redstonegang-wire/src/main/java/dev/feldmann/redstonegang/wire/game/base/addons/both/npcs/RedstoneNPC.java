/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Classe criada por Carlos Andre Feldmann Junior
 * Apoio: Isaias Finger, Gabriel Slomka, Gabriel Augusto Souza
 * Skype: junior.feldmann
 * GitHub: https://github.com/feldmannjr
 * Facebook: https://www.facebook.com/carlosandre.feldmannjunior
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.npcs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Carlos
 */
public class RedstoneNPC {

    Location loc;
    String nome;
    String skin;
    EntityType et;
    net.citizensnpcs.api.npc.NPC citizens;
    boolean voa;
    boolean realLook = false;
    List<BiConsumer<Player, RedstoneNPC>> clickEvents = new ArrayList();


    public RedstoneNPC(Location loc, EntityType et, String nome) {
        this.loc = loc;
        this.nome = nome;
        this.et = et;
    }

    public void setRealLook(boolean realLook) {
        this.realLook = realLook;
    }

    public void setCitizens(net.citizensnpcs.api.npc.NPC citizens) {
        this.citizens = citizens;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getSkin() {
        return skin;
    }

    public void setFly(boolean voa) {
        this.voa = voa;
    }

    public void setEntityEntity(EntityType et) {
        this.et = et;
    }

    public String getNome() {
        return nome;
    }

    public net.citizensnpcs.api.npc.NPC getCitizens() {
        return citizens;
    }

    public EntityType getType() {
        return et;
    }


}
