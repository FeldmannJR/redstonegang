package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.*;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.EnumArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.RemainStringsArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.StringArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.utils.items.book.BookStackUtil;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;

public class Book extends SimpleCmd
{
    private static final EnumArgument<BookCmds> COMANDO = new EnumArgument<>("comando", BookCmds.class, false);
    private static final StringArgument ARGUMENTO = new RemainStringsArgument("argumento", true);

    public Book()
    {
        super("book", "Manipula o Livro que o jogador tem na mão.", "book", COMANDO, ARGUMENTO);
    }

    @Override
    public void command(Player p, Arguments a)
    {
        BookCmds cmd = a.get(COMANDO);
        boolean hasArgumento = a.isPresent(ARGUMENTO);
        switch (cmd)
        {
            case SETAUTOR:
                if(hasArgumento)
                {
                    String newAuthor = a.get(ARGUMENTO);
                    BookStackUtil.setAuthor(p.getItemInHand(), newAuthor);
                    C.info(p,"Livro alterado para author %%.", newAuthor);
                }
                else
                {
                    C.error(p, "Informe o novo author.");
                }
                break;
            case SETTITLE:
                if(hasArgumento)
                {
                    String newTitle = a.get(ARGUMENTO);
                    BookStackUtil.setTitle(p.getItemInHand(), newTitle);
                    C.info(p,"Livro alterado para o título %%.", newTitle);
                }
                else
                {
                    C.error(p, "Informe o novo título.");
                }
                break;
            case LOCK:
                if (BookStackUtil.isLocked(p.getItemInHand()))
                {
                    C.error(p, "O livro já está bloqueado!");
                }
                else
                {
                    BookStackUtil.lock(p.getItemInHand());
                    C.info(p, "O livro foi bloqueado.");
                }
                break;
            case UNLOCK:
                if (BookStackUtil.isLocked(p.getItemInHand()))
                {
                    BookStackUtil.unLock(p.getItemInHand());
                    C.info(p, "O livro foi desbloqueado.");
                }
                else
                {
                    C.error(p, "O livro já está desbloqueado!");
                }
                break;
            case CMDBOOK:
                C.info(p, "Comando não desenvolvido.");
                break;
            default:
                C.error(p, "Comando inválido.");
                break;
        }
        p.updateInventory();
    }

    public enum BookCmds
    {
        SETAUTOR,
        SETTITLE,
        LOCK,
        UNLOCK,
        CMDBOOK
    }
}
