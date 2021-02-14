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

public class Encantar extends SimpleCmd {

    private static final EnumArgument<EnchantTypes> ENCANTAMENTO = new EnumArgument<>("encantamento", EnchantTypes.class, false);
    private static final IntegerArgument LEVEL = new IntegerArgument("level", false, 1, 1, Integer.MAX_VALUE);

    public Encantar() {
        super("encantar", "Encanta o item que o jogador tem na mão.", "encantar", ENCANTAMENTO, LEVEL);
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
        PROTECAO(Enchantment.PROTECTION_ENVIRONMENTAL),
        // 1
        PROTECAO_FOGO(Enchantment.PROTECTION_FIRE),
        // 2
        PESO_PENA(Enchantment.PROTECTION_FALL),
        // 3
        PROTECAO_EXPLOSOES(Enchantment.PROTECTION_EXPLOSIONS),
        // 4
        PROTECAO_PROJETEIS(Enchantment.PROTECTION_PROJECTILE),
        // 5
        RESPIRACAO(Enchantment.OXYGEN),
        // 6
        AFINIDADE_AQUATICA(Enchantment.WATER_WORKER),
        // 7
        ESPINHOS(Enchantment.THORNS),
        // 8
        PASSOS_PROFUNDOS(Enchantment.DEPTH_STRIDER),
        // 16
        AFIACAO(Enchantment.DAMAGE_ALL),
        // 17
        JULGAMENTO(Enchantment.DAMAGE_UNDEAD),
        // 18
        RUINA_DOS_ARTROPODES(Enchantment.DAMAGE_ARTHROPODS),
        // 19
        REPULSAO(Enchantment.KNOCKBACK),
        // 20
        ASPECTO_FLAMEJANTE(Enchantment.FIRE_ASPECT),
        // 21
        PILHAGEM(Enchantment.LOOT_BONUS_MOBS),
        // 32
        EFICIENCIA(Enchantment.DIG_SPEED),
        // 33
        TOQUE_SUAVE(Enchantment.SILK_TOUCH),
        // 34
        INQUEBRAVEL(Enchantment.DURABILITY),
        // 35
        FORTUNA(Enchantment.LOOT_BONUS_BLOCKS),
        // 48
        FORCA(Enchantment.ARROW_DAMAGE),
        // 49
        IMPACTO(Enchantment.ARROW_KNOCKBACK),
        // 50
        CHAMA(Enchantment.ARROW_FIRE),
        // 51
        INFINIDADE(Enchantment.ARROW_INFINITE),
        // 61
        SORTE_DO_MAR(Enchantment.LUCK),
        // 62
        ISCA(Enchantment.LURE);

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