package dev.feldmann.redstonegang.wire.game.base.addons.minigames.state.states;

import dev.feldmann.redstonegang.wire.game.base.Game;
import dev.feldmann.redstonegang.wire.game.base.addons.minigames.state.GameState;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import dev.feldmann.redstonegang.wire.utils.player.ActionBar;
import org.bukkit.event.EventHandler;

public class PreGameStateController extends Addon {

    private static int tempoComeca = 180;
    private static int reduz = 10;

    Game game;
    private int comeca = tempoComeca;

    public PreGameStateController(Game g) {
        this.game = g;
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (game.getState() == GameState.PREGAME) {
            if (ev.getType() == UpdateType.SEC_1) {
                int min = game.getMinJogadres();
                int max = game.getMaxJogadores();
                int atual = game.getPlayers(false).size();
                if (min > atual) {
                    comeca = tempoComeca;
                    ActionBar.sendActionBar("§dFaltam " + (min - atual) + " jogadores para começar!");
                    return;
                }
                if (atual >= max) {
                    if (comeca > reduz) {
                        comeca = reduz;
                    }
                }
                if (comeca <= 0) {
                    game.setState(GameState.GAME);
                } else {
                    ActionBar.sendActionBar("§6Começando jogo em " + comeca + " segundos!");
                }
                comeca--;
            }
        }

    }

}
