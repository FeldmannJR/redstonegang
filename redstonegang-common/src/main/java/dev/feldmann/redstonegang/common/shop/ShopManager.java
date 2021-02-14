package dev.feldmann.redstonegang.common.shop;


import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.shop.magento.MagentoCode;
import dev.feldmann.redstonegang.common.shop.magento.MagentoDB;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.common.utils.msgs.Msg;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import net.md_5.bungee.api.ChatColor;
import org.jooq.types.UInteger;

public class ShopManager
{
    private ExpirationManager expirations;
    private MagentoDB magento_db;

    public MsgType VIP = new MsgType("§6§lVIP> ", ChatColor.GOLD, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE);
    public MsgType RUBI = new MsgType("§4§lRubis> ", ChatColor.WHITE, ChatColor.RED, ChatColor.RED, ChatColor.DARK_RED, ChatColor.DARK_RED, ChatColor.DARK_RED);

    public ShopManager()
    {
        this.expirations = new ExpirationManager(this);
        this.magento_db = new MagentoDB();
    }

    public boolean tryToActivateCode(User user, String code)
    {
        MagentoCode mcode = this.magento_db.loadCode(code);
        if (mcode == null || mcode.isLocked() || mcode.isActive())
        {
            return false;
        }

        /*
         *  Desabilitar o código, WARNING: Alguma maneira de evitar problemas de concorrencia
         *  (2 negos ativar ao mesmo tempo em servers diferentes, botar um lock em um código acho que é uma boa)
         *  Exemplo aqui bota um lock no código, e caso já tivesse um lock não deixava ativar
         */
        mcode.lock();

        String type = mcode.getData().getType();
        Integer typeAmount = mcode.getData().getTypeAmount();

        boolean isCash = false;
        boolean success = false;

        if (type.startsWith("RUBI"))
        {
            isCash = true;
        }

        if (isCash)
        {
            success = activateCash(user, typeAmount);
        }
        else
        {
            success = activateVip(user, mcode.getData().getCodeId(), type, typeAmount);
        }

        if (success)
        {
            mcode.getData().setActivedate(DateUtils.getCurrentTime());
            mcode.getData().setUserId(user.getId());
            mcode.getData().setLock((byte) 1);
            mcode.getData().store();
            return true;
        }
        mcode.unLock();
        return false;
    }

    public boolean activateCash(User user, int cash)
    {
        user.addCash(cash);
        Msg.send(user, RUBI, "§fVocê ativou %% §4§lRubis§f!", cash);
        RedstoneGang.instance().broadcastMessage(Msg.msgText(RUBI, "O jogador %% acabou de ativar %% Rubis!", user, cash));
        return true;
    }

    /**
     * @param type Tipo do código no magento
     * @param code_id Id do código no magento
     * @param dias Duração do VIP se dias for = 0 é permanente
     */
    public boolean activateVip(User user, UInteger code_id, String type, int dias)
    {
        VipType vip = VipType.valueOf(type.toUpperCase());
        if (vip == null)
        {
            return false;
        }

        boolean activated = expirations.activatePackage(user, code_id, vip.getGroup().getNome(), dias);
        if (activated) {
            RedstoneGang.instance().broadcastMessage(Msg.msgText(VIP, "O jogador %% acabou de ativar VIP %%!", user, vip.getGroup().getDisplayName()));
            return true;
        }
        return false;
    }


}
