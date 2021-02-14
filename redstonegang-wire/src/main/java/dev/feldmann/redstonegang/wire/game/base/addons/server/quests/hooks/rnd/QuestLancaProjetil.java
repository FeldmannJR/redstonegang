package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.rnd;


import dev.feldmann.redstonegang.wire.base.cmds.arguments.Argument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.EnumArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.QuestInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.hooks.QuestContador;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class QuestLancaProjetil extends QuestContador {

    public static EnumArgument<Projetil> PROJETIL = new EnumArgument<>("projetil", Projetil.class, false);

    Projetil proj;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void projectShoot(ProjectileLaunchEvent ev) {
        if (ev.getEntity().getShooter() instanceof Player) {
            Player p = (Player) ev.getEntity().getShooter();
            // if (HashMapUtil.HasMetaSaved("pokeball", ev.getEntity().getUniqueId())) {
            //    return;
            //}
            QuestInfo fazendo = getFazendo(p);
            if (fazendo != null) {
                if (ev.getEntity().getType() == proj.ent) {
                    fazendo.faz();
                }
            }
        }
    }

    @Override
    public Argument[] getArgs() {
        return new Argument[]{QUANTIDADE, PROJETIL};
    }


    @Override
    public ItemStack toItemStack() {
        return new ItemStack(proj.mat);
    }

    @Override
    public String suggestName() {
        return "Atire " + contador + " " + proj.getNome();
    }

    @Override
    public String toString() {
        return "Atire " + contador + " " + proj.name();

    }

    @Override
    public boolean createQuestHook(Player p, Arguments args) {
        this.proj = args.get(PROJETIL);
        this.contador = args.get(QUANTIDADE);
        return true;

    }

    public enum Projetil {

        Flecha(EntityType.ARROW, Material.ARROW),
        Bola_De_Neve(EntityType.SNOWBALL, Material.SNOW_BALL),
        Ovo(EntityType.EGG, Material.EGG),
        Ender_Pearl(EntityType.ENDER_PEARL, Material.ENDER_PEARL);

        public EntityType ent;
        public Material mat;

        Projetil(EntityType ent, Material m) {
            this.ent = ent;
            this.mat = m;
        }

        public String getNome() {
            return name().replace("_", " ");
        }
    }
}
