/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.PunishmentsReasons;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class PunishmentsReasonsRecord extends UpdatableRecordImpl<PunishmentsReasonsRecord> implements Record5<Integer, String, String, String, Byte> {

    private static final long serialVersionUID = 224910107;

    /**
     * Setter for <code>redstonegang_common.punishments_reasons.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>redstonegang_common.punishments_reasons.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>redstonegang_common.punishments_reasons.type</code>.
     */
    public void setType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>redstonegang_common.punishments_reasons.type</code>.
     */
    public String getType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>redstonegang_common.punishments_reasons.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>redstonegang_common.punishments_reasons.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>redstonegang_common.punishments_reasons.note</code>.
     */
    public void setNote(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>redstonegang_common.punishments_reasons.note</code>.
     */
    public String getNote() {
        return (String) get(3);
    }

    /**
     * Setter for <code>redstonegang_common.punishments_reasons.can_remove</code>.
     */
    public void setCanRemove(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>redstonegang_common.punishments_reasons.can_remove</code>.
     */
    public Byte getCanRemove() {
        return (Byte) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, Byte> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, Byte> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PunishmentsReasons.PUNISHMENTS_REASONS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PunishmentsReasons.PUNISHMENTS_REASONS.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return PunishmentsReasons.PUNISHMENTS_REASONS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return PunishmentsReasons.PUNISHMENTS_REASONS.NOTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return PunishmentsReasons.PUNISHMENTS_REASONS.CAN_REMOVE;
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
    public String component2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getCanRemove();
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
    public String value2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getCanRemove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PunishmentsReasonsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PunishmentsReasonsRecord value2(String value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PunishmentsReasonsRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PunishmentsReasonsRecord value4(String value) {
        setNote(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PunishmentsReasonsRecord value5(Byte value) {
        setCanRemove(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PunishmentsReasonsRecord values(Integer value1, String value2, String value3, String value4, Byte value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PunishmentsReasonsRecord
     */
    public PunishmentsReasonsRecord() {
        super(PunishmentsReasons.PUNISHMENTS_REASONS);
    }

    /**
     * Create a detached, initialised PunishmentsReasonsRecord
     */
    public PunishmentsReasonsRecord(Integer id, String type, String name, String note, Byte canRemove) {
        super(PunishmentsReasons.PUNISHMENTS_REASONS);

        set(0, id);
        set(1, type);
        set(2, name);
        set(3, note);
        set(4, canRemove);
    }
}
