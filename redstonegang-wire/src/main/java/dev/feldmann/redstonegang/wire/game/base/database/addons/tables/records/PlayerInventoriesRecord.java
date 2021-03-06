/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records;


import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.PlayerInventories;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlayerInventoriesRecord extends UpdatableRecordImpl<PlayerInventoriesRecord> implements Record15<Integer, byte[], byte[], byte[], Integer, String, Double, Integer, Double, Double, Double, Integer, String, String, Integer> {

    private static final long serialVersionUID = 560614438;

    /**
     * Setter for <code>player_inventories.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>player_inventories.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>player_inventories.invContents</code>.
     */
    public void setInvcontents(byte... value) {
        set(1, value);
    }

    /**
     * Getter for <code>player_inventories.invContents</code>.
     */
    public byte[] getInvcontents() {
        return (byte[]) get(1);
    }

    /**
     * Setter for <code>player_inventories.armorContents</code>.
     */
    public void setArmorcontents(byte... value) {
        set(2, value);
    }

    /**
     * Getter for <code>player_inventories.armorContents</code>.
     */
    public byte[] getArmorcontents() {
        return (byte[]) get(2);
    }

    /**
     * Setter for <code>player_inventories.enderContents</code>.
     */
    public void setEndercontents(byte... value) {
        set(3, value);
    }

    /**
     * Getter for <code>player_inventories.enderContents</code>.
     */
    public byte[] getEndercontents() {
        return (byte[]) get(3);
    }

    /**
     * Setter for <code>player_inventories.selectedItem</code>.
     */
    public void setSelecteditem(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>player_inventories.selectedItem</code>.
     */
    public Integer getSelecteditem() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>player_inventories.potions</code>.
     */
    public void setPotions(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>player_inventories.potions</code>.
     */
    public String getPotions() {
        return (String) get(5);
    }

    /**
     * Setter for <code>player_inventories.health</code>.
     */
    public void setHealth(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>player_inventories.health</code>.
     */
    public Double getHealth() {
        return (Double) get(6);
    }

    /**
     * Setter for <code>player_inventories.foodLevel</code>.
     */
    public void setFoodlevel(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>player_inventories.foodLevel</code>.
     */
    public Integer getFoodlevel() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>player_inventories.foodExhaustion</code>.
     */
    public void setFoodexhaustion(Double value) {
        set(8, value);
    }

    /**
     * Getter for <code>player_inventories.foodExhaustion</code>.
     */
    public Double getFoodexhaustion() {
        return (Double) get(8);
    }

    /**
     * Setter for <code>player_inventories.foodSaturation</code>.
     */
    public void setFoodsaturation(Double value) {
        set(9, value);
    }

    /**
     * Getter for <code>player_inventories.foodSaturation</code>.
     */
    public Double getFoodsaturation() {
        return (Double) get(9);
    }

    /**
     * Setter for <code>player_inventories.exp</code>.
     */
    public void setExp(Double value) {
        set(10, value);
    }

    /**
     * Getter for <code>player_inventories.exp</code>.
     */
    public Double getExp() {
        return (Double) get(10);
    }

    /**
     * Setter for <code>player_inventories.level</code>.
     */
    public void setLevel(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>player_inventories.level</code>.
     */
    public Integer getLevel() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>player_inventories.location</code>.
     */
    public void setLocation(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>player_inventories.location</code>.
     */
    public String getLocation() {
        return (String) get(12);
    }

    /**
     * Setter for <code>player_inventories.teleport_location</code>.
     */
    public void setTeleportLocation(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>player_inventories.teleport_location</code>.
     */
    public String getTeleportLocation() {
        return (String) get(13);
    }

    /**
     * Setter for <code>player_inventories.gamemode</code>.
     */
    public void setGamemode(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>player_inventories.gamemode</code>.
     */
    public Integer getGamemode() {
        return (Integer) get(14);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, byte[], byte[], byte[], Integer, String, Double, Integer, Double, Double, Double, Integer, String, String, Integer> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, byte[], byte[], byte[], Integer, String, Double, Integer, Double, Double, Double, Integer, String, String, Integer> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PlayerInventories.PLAYER_INVENTORIES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<byte[]> field2() {
        return PlayerInventories.PLAYER_INVENTORIES.INVCONTENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<byte[]> field3() {
        return PlayerInventories.PLAYER_INVENTORIES.ARMORCONTENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<byte[]> field4() {
        return PlayerInventories.PLAYER_INVENTORIES.ENDERCONTENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return PlayerInventories.PLAYER_INVENTORIES.SELECTEDITEM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return PlayerInventories.PLAYER_INVENTORIES.POTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field7() {
        return PlayerInventories.PLAYER_INVENTORIES.HEALTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return PlayerInventories.PLAYER_INVENTORIES.FOODLEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field9() {
        return PlayerInventories.PLAYER_INVENTORIES.FOODEXHAUSTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field10() {
        return PlayerInventories.PLAYER_INVENTORIES.FOODSATURATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field11() {
        return PlayerInventories.PLAYER_INVENTORIES.EXP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return PlayerInventories.PLAYER_INVENTORIES.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return PlayerInventories.PLAYER_INVENTORIES.LOCATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return PlayerInventories.PLAYER_INVENTORIES.TELEPORT_LOCATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return PlayerInventories.PLAYER_INVENTORIES.GAMEMODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] component2() {
        return getInvcontents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] component3() {
        return getArmorcontents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] component4() {
        return getEndercontents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getSelecteditem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getPotions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component7() {
        return getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getFoodlevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component9() {
        return getFoodexhaustion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component10() {
        return getFoodsaturation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component11() {
        return getExp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getTeleportLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component15() {
        return getGamemode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] value2() {
        return getInvcontents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] value3() {
        return getArmorcontents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] value4() {
        return getEndercontents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getSelecteditem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPotions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value7() {
        return getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getFoodlevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value9() {
        return getFoodexhaustion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value10() {
        return getFoodsaturation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value11() {
        return getExp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getTeleportLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getGamemode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value2(byte... value) {
        setInvcontents(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value3(byte... value) {
        setArmorcontents(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value4(byte... value) {
        setEndercontents(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value5(Integer value) {
        setSelecteditem(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value6(String value) {
        setPotions(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value7(Double value) {
        setHealth(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value8(Integer value) {
        setFoodlevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value9(Double value) {
        setFoodexhaustion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value10(Double value) {
        setFoodsaturation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value11(Double value) {
        setExp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value12(Integer value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value13(String value) {
        setLocation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value14(String value) {
        setTeleportLocation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord value15(Integer value) {
        setGamemode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventoriesRecord values(Integer value1, byte[] value2, byte[] value3, byte[] value4, Integer value5, String value6, Double value7, Integer value8, Double value9, Double value10, Double value11, Integer value12, String value13, String value14, Integer value15) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlayerInventoriesRecord
     */
    public PlayerInventoriesRecord() {
        super(PlayerInventories.PLAYER_INVENTORIES);
    }

    /**
     * Create a detached, initialised PlayerInventoriesRecord
     */
    public PlayerInventoriesRecord(Integer id, byte[] invcontents, byte[] armorcontents, byte[] endercontents, Integer selecteditem, String potions, Double health, Integer foodlevel, Double foodexhaustion, Double foodsaturation, Double exp, Integer level, String location, String teleportLocation, Integer gamemode) {
        super(PlayerInventories.PLAYER_INVENTORIES);

        set(0, id);
        set(1, invcontents);
        set(2, armorcontents);
        set(3, endercontents);
        set(4, selecteditem);
        set(5, potions);
        set(6, health);
        set(7, foodlevel);
        set(8, foodexhaustion);
        set(9, foodsaturation);
        set(10, exp);
        set(11, level);
        set(12, location);
        set(13, teleportLocation);
        set(14, gamemode);
    }
}
