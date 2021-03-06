/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.Expirations;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


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
public class ExpirationsRecord extends UpdatableRecordImpl<ExpirationsRecord> implements Record8<Long, Integer, Integer, String, Long, UInteger, Timestamp, Timestamp> {

    private static final long serialVersionUID = -350317344;

    /**
     * Setter for <code>redstonegang_common.expirations.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.days</code>.
     */
    public void setDays(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.days</code>.
     */
    public Integer getDays() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.type</code>.
     */
    public void setType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.type</code>.
     */
    public String getType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.parent</code>.
     */
    public void setParent(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.parent</code>.
     */
    public Long getParent() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.code_id</code>.
     */
    public void setCodeId(UInteger value) {
        set(5, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.code_id</code>.
     */
    public UInteger getCodeId() {
        return (UInteger) get(5);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.start</code>.
     */
    public void setStart(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.start</code>.
     */
    public Timestamp getStart() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>redstonegang_common.expirations.end</code>.
     */
    public void setEnd(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>redstonegang_common.expirations.end</code>.
     */
    public Timestamp getEnd() {
        return (Timestamp) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Integer, Integer, String, Long, UInteger, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Integer, Integer, String, Long, UInteger, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Expirations.EXPIRATIONS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Expirations.EXPIRATIONS.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Expirations.EXPIRATIONS.DAYS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Expirations.EXPIRATIONS.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return Expirations.EXPIRATIONS.PARENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field6() {
        return Expirations.EXPIRATIONS.CODE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return Expirations.EXPIRATIONS.START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return Expirations.EXPIRATIONS.END;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getDays();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component5() {
        return getParent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component6() {
        return getCodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getDays();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getParent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value6() {
        return getCodeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value3(Integer value) {
        setDays(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value4(String value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value5(Long value) {
        setParent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value6(UInteger value) {
        setCodeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value7(Timestamp value) {
        setStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord value8(Timestamp value) {
        setEnd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExpirationsRecord values(Long value1, Integer value2, Integer value3, String value4, Long value5, UInteger value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ExpirationsRecord
     */
    public ExpirationsRecord() {
        super(Expirations.EXPIRATIONS);
    }

    /**
     * Create a detached, initialised ExpirationsRecord
     */
    public ExpirationsRecord(Long id, Integer userId, Integer days, String type, Long parent, UInteger codeId, Timestamp start, Timestamp end) {
        super(Expirations.EXPIRATIONS);

        set(0, id);
        set(1, userId);
        set(2, days);
        set(3, type);
        set(4, parent);
        set(5, codeId);
        set(6, start);
        set(7, end);
    }
}
