/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.records;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.Quests;

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
public class QuestsRecord extends UpdatableRecordImpl<QuestsRecord> implements Record5<Integer, String, String, String, String> {

    private static final long serialVersionUID = -1875240531;

    /**
     * Setter for <code>quests.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>quests.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>quests.vars</code>.
     */
    public void setVars(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>quests.vars</code>.
     */
    public String getVars() {
        return (String) get(1);
    }

    /**
     * Setter for <code>quests.nome</code>.
     */
    public void setNome(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>quests.nome</code>.
     */
    public String getNome() {
        return (String) get(2);
    }

    /**
     * Setter for <code>quests.hook</code>.
     */
    public void setHook(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>quests.hook</code>.
     */
    public String getHook() {
        return (String) get(3);
    }

    /**
     * Setter for <code>quests.desc</code>.
     */
    public void setDesc(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>quests.desc</code>.
     */
    public String getDesc() {
        return (String) get(4);
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
    public Row5<Integer, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Quests.QUESTS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Quests.QUESTS.VARS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Quests.QUESTS.NOME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Quests.QUESTS.HOOK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Quests.QUESTS.DESC;
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
        return getVars();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getNome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getHook();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getDesc();
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
        return getVars();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getNome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getHook();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestsRecord value2(String value) {
        setVars(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestsRecord value3(String value) {
        setNome(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestsRecord value4(String value) {
        setHook(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestsRecord value5(String value) {
        setDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestsRecord values(Integer value1, String value2, String value3, String value4, String value5) {
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
     * Create a detached QuestsRecord
     */
    public QuestsRecord() {
        super(Quests.QUESTS);
    }

    /**
     * Create a detached, initialised QuestsRecord
     */
    public QuestsRecord(Integer id, String vars, String nome, String hook, String desc) {
        super(Quests.QUESTS);

        set(0, id);
        set(1, vars);
        set(2, nome);
        set(3, hook);
        set(4, desc);
    }
}
