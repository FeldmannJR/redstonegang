package dev.feldmann.redstonegang.common.shop.magento;

import dev.feldmann.redstonegang.common.db.Database;
import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_magento.Tables.*;

import dev.feldmann.redstonegang.common.db.jooq.redstonegang_magento.tables.records.DrmagentoGamecodesCodeRecord;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import java.util.ArrayList;
import java.util.List;

public class MagentoDB extends Database
{
    public MagentoDB()
    {
        super("redstonegang_magento", false);
    }

    @Override
    public void createTables()
    {
        //
    }

    public List<MagentoCode> loadCodes(Condition cond)
    {
        List<MagentoCode> codes = new ArrayList<>();
        Result<Record> rs = dsl()
                .select().from(DRMAGENTO_GAMECODES_CODE)
                .where(cond)
                .fetch();

        for (Record r : rs)
        {
            MagentoCode code = new MagentoCode((DrmagentoGamecodesCodeRecord) r);
            codes.add(code);
        }
        return codes;
    }

    public MagentoCode loadCode(String pin_code)
    {
        List<MagentoCode> codes = loadCodes(DRMAGENTO_GAMECODES_CODE.PINCODE.equalIgnoreCase(pin_code));
        if (codes.size() >= 1)
        {
            return codes.get(0);
        }
        return null;
    }
}
