package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.cmds.staff;

import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.EnumArgument;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.IntegerArgument;
import dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds.SimpleCmd;
import dev.feldmann.redstonegang.wire.modulos.language.LangUtils;
import dev.feldmann.redstonegang.wire.modulos.language.lang.LanguageHelper;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Enchant extends SimpleCmd {

    private static final EnumArgument<EnchantTypes> ENCANTAMENTO = new EnumArgument<>("encantamento", EnchantTypes.class, false);
    private static final IntegerArgument LEVEL = new IntegerArgument("level", false, 1, 1, Integer.MAX_VALUE);

    public Enchant() {
        super("enchant", "Encanta o item que o jogador tem na mão.", "enchant", ENCANTAMENTO, LEVEL);
    }

    @Override
    public void command(Player p, Arguments a) {
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
        {
            C.error(p,"Coloque algum item na sua mão.");
            return;
        }
        EnchantTypes encantamento = a.get(ENCANTAMENTO);
        Integer level = a.get(LEVEL);
        ItemStack it = p.getItemInHand().clone();
        p.getItemInHand().addUnsafeEnchantment(encantamento.getType(), level);
        p.updateInventory();
        String enchantDisplayName = LanguageHelper.getEnchantmentDisplayName(encantamento.getType(), level, LangUtils.defaultLanguage);
        C.info(p, "Item %% encantado com %%.", it, enchantDisplayName);
    }

    @Override
    public boolean canOverride() {
        return true;
    }

    public enum EnchantTypes
    {
        // 0
        PROTECTION_ENVIRONMENTAL(Enchantment.PROTECTION_ENVIRONMENTAL),
        // 1
        PROTECTION_FIRE(Enchantment.PROTECTION_FIRE),
        // 2
        PROTECTION_FALL(Enchantment.PROTECTION_FALL),
        // 3
        PROTECTION_EXPLOSIONS(Enchantment.PROTECTION_EXPLOSIONS),
        // 4
        PROTECTION_PROJECTILE(Enchantment.PROTECTION_PROJECTILE),
        // 5
        OXYGEN(Enchantment.OXYGEN),
        // 6
        WATER_WORKER(Enchantment.WATER_WORKER),
        // 7
        THORNS(Enchantment.THORNS),
        // 8
        DEPTH_STRIDER(Enchantment.DEPTH_STRIDER),
        // 16
        DAMAGE_ALL(Enchantment.DAMAGE_ALL),
        // 17
        DAMAGE_UNDEAD(Enchantment.DAMAGE_UNDEAD),
        // 18
        DAMAGE_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS),
        // 19
        KNOCKBACK(Enchantment.KNOCKBACK),
        // 20
        FIRE_ASPECT(Enchantment.FIRE_ASPECT),
        // 21
        LOOT_BONUS_MOBS(Enchantment.LOOT_BONUS_MOBS),
        // 32
        DIG_SPEED(Enchantment.DIG_SPEED),
        // 33
        SILK_TOUCH(Enchantment.SILK_TOUCH),
        // 34
        DURABILITY(Enchantment.DURABILITY),
        // 35
        LOOT_BONUS_BLOCKS(Enchantment.LOOT_BONUS_BLOCKS),
        // 48
        ARROW_DAMAGE(Enchantment.ARROW_DAMAGE),
        // 49
        ARROW_KNOCKBACK(Enchantment.ARROW_KNOCKBACK),
        // 50
        ARROW_FIRE(Enchantment.ARROW_FIRE),
        // 51
        ARROW_INFINITE(Enchantment.ARROW_INFINITE),
        // 61
        LUCK(Enchantment.LUCK),
        // 62
        LURE(Enchantment.LURE);

        private final Enchantment type;

        EnchantTypes(Enchantment type)
        {
            this.type = type;
        }

        public Enchantment getType()
        {
            return this.type;
        }
    }
}