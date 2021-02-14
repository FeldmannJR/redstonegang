package dev.feldmann.redstonegang.wire.game.games.other.login;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LoginEntry extends ServerEntry {
    public LoginEntry() {
        super(Login.class);
    }

    @Override
    public String getMapsFolder() {
        return "login";
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.COMMAND);
    }


    @Override
    public String getNome() {
        return "Login";
    }
}
