package dev.feldmann.redstonegang.wire.modulos.menus;

import dev.feldmann.redstonegang.common.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Container;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class FurnaceMenu extends Menu {

    boolean arrowAnim = false;
    boolean fireAnim = false;

    public FurnaceMenu(String titulo) {
        super(titulo, 1);
    }

    @Override
    protected void createInventory() {
        this.inv = Bukkit.createInventory(null, InventoryType.FURNACE, "§l" + invTitle);
    }

    public void setFireAnim(boolean fireAnim) {
        this.fireAnim = fireAnim;
    }

    public void setArrowAnim(boolean arrowAnim) {
        this.arrowAnim = arrowAnim;
    }

    public void setFuel(Button b) {
        add(1, b);
    }

    public void setResult(Button b) {
        add(2, b);
    }

    public void setIngredient(Button b) {
        add(0, b);
    }

    private int tick = 0;

    @Override
    public void onTick() {
        if (fireAnim || arrowAnim) {
            tick++;
            anims();
        }
    }

    @Override
    public void open(Player p) {
        super.open(p);
    }

    public void anims() {
        if (!inv.getViewers().isEmpty()) {
            for (HumanEntity ent : inv.getViewers()) {
                if (MenuManager.getOpenMenu(ent) == this) {
                    int va = MathUtils.lerp(0, 200, (float) ((tick % 25)) / 25);
                    CraftHumanEntity che = (CraftHumanEntity) ent;
                    Container container = che.getHandle().activeContainer;

                    if (che instanceof CraftPlayer) {
                        CraftPlayer cp = (CraftPlayer) che;
                        if (arrowAnim) {
                            cp.getHandle().setContainerData(container, 2, va);
                            cp.getHandle().setContainerData(container, 3, 200);
                        }
                        if (fireAnim) {
                            cp.getHandle().setContainerData(container, 0, va);
                            cp.getHandle().setContainerData(container, 1, 200);
                        }

                    }


                }
            }
        }

    }

}
