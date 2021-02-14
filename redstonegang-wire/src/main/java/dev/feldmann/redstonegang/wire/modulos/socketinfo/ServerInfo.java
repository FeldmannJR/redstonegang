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
package dev.feldmann.redstonegang.wire.modulos.socketinfo;

import dev.feldmann.redstonegang.wire.game.base.Games;

import java.util.HashMap;

/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public class ServerInfo implements Cloneable {


    //Id da sala no DB (-1== não tem no db, famoso hub)
    public int id;
    //Tipo do jogo na sala
    public Games game;
    //Nome do bungee
    public String name;
    //Quantos negos tem online
    public int online;
    //Maximo de nego onloine
    public int maxonline;
    //Dono da sala 0 == oficial
    public int owner;
    //Status da sala, se ta aberta ou fechada, reiniciando, ou em manutenção
    public ServerStatus status;
    //Mensagem para botar nos menus, só enfeite
    public String lastMsg;



    //Variaveis custom
    public HashMap<String, String> vars = new HashMap();


    public ServerInfo clona() {
        try {
            return (ServerInfo) super.clone();
        } catch (Exception e) {
        }
        return null;
    }

    public boolean isOficial() {
        return owner == 0;
    }
}
