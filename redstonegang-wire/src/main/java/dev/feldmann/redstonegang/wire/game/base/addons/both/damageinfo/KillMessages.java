package dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;

import java.util.Random;

public class KillMessages {

    public static String getMessageMorte(Player p, DeathInfo m, String nome, String mensagem, boolean nopvp) {
        if (m != null && m.getLastPlayerDamage() != null) {
            HitInfo last = m.getLastPlayerDamage();
            String[] podeser = new String[]{"matou", "aniquilou", "dizimou", "humilhou", "bateu em", "deu um sumiço em", "detonou", "fuzilou", "acabou com"};
            String foi = podeser[new Random().nextInt(podeser.length)];
            String matador = last.getKillerDisplayname();
            if (last.getType() == HitType.PHYSICAL) {
                if (last.item == null) {
                    return mensagem + "" + nome + matador + mensagem + " " + foi + " " + nome + p.getName() + mensagem + " com seus Punhos !";
                } else {
                    return mensagem + "" + nome + matador + mensagem + " " + foi + " " + nome + p.getName() + mensagem + " com " + nome + last.getItemName() + mensagem + " !";
                }

            }
            if (last.getType() == HitType.ARROW) {
                return mensagem + nome + matador + mensagem + " " + foi + " " + nome + p.getName() + mensagem + " com seu " + nome + last.getItemName() + mensagem + " !";
            }
            if (last.getType() == HitType.MOB) {
                return mensagem + nome + p.getName() + mensagem + " morreu para um Lacaio de " + nome + matador + mensagem + " !";
            }
            if (last.getType() == HitType.EGG) {
                return mensagem + nome + matador + mensagem + " matou " + nome + p.getName() + mensagem + " com " + nome + "uma ovada " + mensagem + "!";
            }
            if (last.getType() == HitType.SNOW_BALL) {
                return mensagem + nome + matador + mensagem + " matou " + nome + p.getName() + mensagem + " com " + nome + "uma bola de neve " + mensagem + "!";
            }
            if (last.getType() == HitType.THORNS) {
                return mensagem + nome + matador + mensagem + " matou " + nome + p.getName() + mensagem + " com " + nome + "sua armadura bolada que tem espinhos" + mensagem + " !";
            }
            if (last.getType() == HitType.POISON) {
                return mensagem + nome + matador + mensagem + " matou " + nome + p.getName() + mensagem + " envenenado !";
            }
            if (last.getType() == HitType.POTION) {
                return mensagem + nome + matador + mensagem + " tacou uma poção de dano em " + nome + p.getName() + mensagem + " e matou ele !";
            }
            if (last.getType() == HitType.EXPLOSION) {
                return mensagem + nome + matador + mensagem + " explodiu " + nome + p.getName() + mensagem + " com uma TNT!";
            }
            if (last.getType() == HitType.CREEPER) {
                return mensagem + nome + matador + mensagem + " explodiu " + nome + p.getName() + mensagem + " com um §2§lCreeper!";
            }
            return mensagem + nome + matador + mensagem + " matou " + nome + p.getName() + mensagem + " !";
        }
        if (nopvp)
            return getMessageMorteNoPvP(p, m, nome, mensagem);
        return null;
    }

    public static String getMessageMorteNoPvP(Player p, DeathInfo m, String nome, String mensagem) {
        if (m != null && m.getLastHit() != null) {
            HitInfo last = m.getLastHit();
            if (last.getDamager() != null) {
                if (last.getType() == HitType.PHYSICAL) {
                    if (last.item != null) {
                            return mensagem + "O jogador " + nome + p.getName() + mensagem + " foi espancado por um(a) " + nome + StringUtils.capitalize(last.getDamager().getType().name().replace("_", " ").toLowerCase() + mensagem + " com um(a) " + last.getItemName() + mensagem + " !");

                    }
                    return mensagem + "O jogador " + nome + p.getName() + mensagem + " foi espancado por um(a) " + nome + StringUtils.capitalize(last.getDamager().getType().name().replace("_", " ").toLowerCase() + " " + mensagem + " !");
                }
                if (last.getType() == HitType.ARROW) {
                    return mensagem + "O jogador " + nome + p.getName() + mensagem + " recebeu uma chuva de flechas de um esqueleto !";
                }
            }
            if (last.getType() == HitType.CACTUS) {
                return mensagem + "O jogador " + nome + p.getName() + mensagem + " morreu se espetando em um cactus!";
            }
            if (last.getType() == HitType.DROWN) {
                return mensagem + "O jogador " + nome + p.getName() + mensagem + " morreu perdeu seu oxigênio em baixo da água e se afogou!";
            }
            if (last.getType() == HitType.FALLING_BLOCK) {
                return mensagem + "O jogador " + nome + p.getName() + mensagem + " foi esmagado por um bloco!";
            }
            if (last.getType() == HitType.FIRE) {
                return mensagem + "O jogador " + nome + p.getName() + mensagem + " virou um churrasquinho! Que delicia!";
            }
            if (last.getType() == HitType.VOID) {
                return mensagem + "O jogador " + nome + p.getName() + mensagem + " caiu no vazio e morreu!";
            }
            if (last.getType() == HitType.EXPLOSION) {
                if (last.getDamager() != null) {
                    if (last.getDamager() instanceof Creeper) {
                        return mensagem + "O jogador " + nome + p.getName() + mensagem + " foi explodido por um " + nome + "Creeper" + mensagem + " !";
                    }
                }
                return mensagem + "O jogador " + nome + p.getName() + mensagem + " explodiu em pedacinhos!";
            }
            if (last.getType() == HitType.FALL) {
                int x = new Random().nextInt(2);
                if (x == 1) {
                    return mensagem + "O jogador " + nome + p.getName() + mensagem + " caiu de um lugar muito alto e morreu!";
                } else {
                    return mensagem + "O jogador " + nome + p.getName() + mensagem + " pensou que conseguia voar!";
                }
            }
            if (last.getType() == HitType.POTION) {
                if (last.getDamager() != null && last.getDamager() instanceof Witch) {
                    return mensagem + "O jogador " + nome + p.getName() + mensagem + " morreu para uma poção de uma " + nome + " Bruxa" + mensagem + " !";
                }
            }
        }
        int x = new Random().nextInt(5);
        if (x
                == 0) {
            return mensagem + "O jogador " + nome + p.getName() + mensagem + " foi para um lugar melhor !";
        }
        if (x
                == 1) {
            return mensagem + "O jogador " + nome + p.getName() + mensagem + " bateu as botas!";
        }
        if (x
                == 2) {
            return mensagem + "O jogador " + nome + p.getName() + mensagem + " dormiu para sempre!";
        }
        if (x
                == 3) {
            return mensagem + "O jogador " + nome + p.getName() + mensagem + " achou uma maneira de se matar!";
        }
        return mensagem + "O jogador " + nome
                + p.getName()
                + mensagem
                + " é um gênio e conseguiu se matar!";
    }
}
