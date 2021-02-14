package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import dev.feldmann.redstonegang.wire.utils.items.InventoryHelper;
import dev.feldmann.redstonegang.wire.utils.items.ItemUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FloatItem {


    //multiplica o rate de buy/sell por este valor para não escalar tão rapido
    private static final double rateModifier = 1;

    private double lastMod = 1;

    int id;
    ItemStack item;
    int shopId;
    double sellPrice;
    double buyPrice;
    double lowPercentage;
    double highPercentage;
    //Qual a diferença maxima de itens que vai afetar o valor, o resto ele capa
    int maxDifference = 10000;
    double available;
    int maxAvailable = 0;
    double perMinuteRegen = 0;
    Integer slot = null;


    ItemTransactionManager transactions;
    private FloatShopAddon addon;

    public FloatItem(FloatShopAddon addon, int id) {
        this.id = id;
        this.addon = addon;
        transactions = new ItemTransactionManager(this, addon.db);
    }

    public FloatItem(FloatShopAddon addon, int id, ItemStack item, double sellPrice, double buyPrice, double available, int maxAvailable) {
        this.id = id;
        this.item = item;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.highPercentage = FloatShopAddon.defaultHighPercentage;
        this.lowPercentage = FloatShopAddon.defaultLowPercentage;
        this.available = available;
        this.maxAvailable = maxAvailable;
        this.addon = addon;
        transactions = new ItemTransactionManager(this, addon.db);
    }


    public double getSellPrice() {
        return sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getAvailable() {
        return available;
    }

    public boolean useStock() {
        return maxAvailable > 0;
    }

    public boolean isSaved() {
        return id != -1;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean sell(Player p, int quantity) {
        if (sellPrice <= 0) {
            C.error(p, "Você não pode vender este item!");
            return false;
        }
        int itemAmount = item.getAmount() * quantity;
        // Não tem no inventário
        if (!InventoryHelper.inventoryContains(p.getInventory(), item, itemAmount)) {
            C.error(p, "Você não tem %% %% para vender !", itemAmount + "x", ItemUtils.clone(getItem(), 1));
            return false;
        }

        double perItemPrice = calculateSellPrice();
        double finalPrice = perItemPrice * quantity;
        InventoryHelper.removeInventoryItems(p.getInventory(), item, itemAmount);
        addon.a(EconomyAddon.class).add(p, finalPrice);
        transactions.addSell(quantity);
        C.info(p, "Você vendeu %% %% por ## !", itemAmount + "x", ItemUtils.clone(getItem(), 1), finalPrice);
        if (useStock())
            addStock(quantity);

        return true;
    }

    public boolean buy(Player p, int qtd) {
        if (buyPrice <= 0) {
            C.error(p, "Você não pode comprar este item!");
            return false;
        }
        double price = calculateBuyPrice() * qtd;
        if (useStock() && available < qtd) {
            C.error(p, "A loja não tem estoque do item!");
            return false;
        }
        if (!addon.a(EconomyAddon.class).hasWithMessage(p, price)) {
            return false;
        }

        int freeSpace = InventoryHelper.getInventoryFreeSpace(p, item);
        int slots = qtd * item.getAmount();
        if (freeSpace < slots) {
            C.error(p, "Você não tem espaço livre no inventário para comprar esta quantidade!");
            return false;
        }
        InventoryHelper.give(p, item, slots);
        addon.a(EconomyAddon.class).remove(p, price);
        transactions.addBuy(qtd);
        if (useStock())
            available -= qtd;
        C.info(p, "Você comprou %% %% por ## !", slots + "x", ItemUtils.clone(getItem(), 1), price);
        return true;
    }


    public void addStock(double qtd) {
        available += qtd;
        available = Math.min(available, maxAvailable);
        addon.db.setAvailable(id, available);
    }

    public void setAvailable(double available) {
        this.available = Math.min(available, maxAvailable);
        save();
    }

    public double calculateSellPrice() {
        if (highPercentage == 1 && lowPercentage == 1) {
            return sellPrice;
        }
        if (lastMod == -1)
            refreshMod();
        return sellPrice * lastMod;
    }

    public boolean isItemFloat() {
        return highPercentage != 1 || lowPercentage != 1;
    }

    public double calculateBuyPrice() {
        if (highPercentage == 1 && lowPercentage == 1) {
            return buyPrice;
        }
        if (lastMod == -1)
            refreshMod();
        return buyPrice * lastMod;
    }

    void refreshMod() {
        lastMod = calculateMod();
    }

    private double calculateMod() {
        double rate = calculateRate();
        if (rate == 0) {
            return 1;
        }
        boolean rateSignal = rate > 0;
        rate = Math.abs(rate);
        rate = Math.min(rate, maxDifference);
        double mod;
        double pct = rate / maxDifference;
        //Mais pessoas comprando
        if (rateSignal) {
            mod = 1 + ((highPercentage - 1) * pct);
        } else {
            //Mais pessoas vendendo
            mod = 1 - ((1 - lowPercentage) * pct);
        }
        return mod;
    }

    public double getRatePercentage() {
        return getFixedRate() / maxDifference;
    }

    private double getFixedRate() {
        double rate = calculateRate();
        if (rate > 0) {
            return Math.min(maxDifference, rate);
        }
        return Math.max(-maxDifference, rate);
    }

    public double calculateRate() {
        return rateModifier * transactions.calculateDiff();
    }

    public void removeUnvalidTransactions() {
        transactions.clean();
    }

    public void doRegenTick() {
        if (!useStock()) return;
        addStock(perMinuteRegen);
    }

    public String getVarianceString() {
        double rate = getRatePercentage();
        String quantificador = null;
        double abs = Math.abs(rate);
        if (rate >= 0.95) {
            return "Tá caro demais, vende logo!";
        }
        if (abs >= 0.8) {
            quantificador = "muito";
        } else if (abs >= 0.6) {
            quantificador = "";
        } else if (abs >= 0.4) {
            quantificador = "moderadamente";
        } else if (abs >= 0.1) {
            quantificador = "pouco";
        }

        if (rate <= -0.95) {
            return "Ta muito barato, compra logo!";
        }
        if (quantificador != null)
            if (rate > 0) {
                return "Preço " + quantificador + (quantificador.isEmpty() ? "" : " ") + "alto";
            } else {
                return "Preço " + quantificador + (quantificador.isEmpty() ? "" : " ") + "baixo";
            }

        return "Preço Normal!";
    }

    public int getId() {
        return id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
        save();
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
        save();
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
        save();
    }

    public double getHighPercentage() {
        return highPercentage;
    }

    public void setHighPercentage(double highPercentage) {
        this.highPercentage = highPercentage;
        save();
    }

    public void setLowPercentage(double lowPercentage) {
        this.lowPercentage = lowPercentage;
        save();
    }

    public int getMaxDifference() {
        return maxDifference;
    }

    public void setMaxDifference(int maxDifference) {
        this.maxDifference = maxDifference;
        save();
    }

    public void setMaxAvailable(int maxAvailable) {
        this.maxAvailable = maxAvailable;
        save();
    }

    public int getMaxAvailable() {
        return maxAvailable;
    }

    public void setPerMinuteRegen(double perMinuteRegen) {
        this.perMinuteRegen = perMinuteRegen;
        save();
    }

    public double getPerMinuteRegen() {
        return perMinuteRegen;
    }

    public double getLowPercentage() {
        return lowPercentage;
    }

    private void save() {
        this.addon.db.saveItem(this);
    }

    public Integer getSlot() {
        return slot;
    }
}
