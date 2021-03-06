/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.records;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.QuestInfos;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class QuestInfosRecord extends UpdatableRecordImpl<QuestInfosRecord> implements Record10<Integer, String, Timestamp, Boolean, Boolean, Integer, Integer, Boolean, Timestamp, Timestamp> {

    private static final long serialVersionUID = 465189272;

    /**
     * Setter for <code>quest_infos.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>quest_infos.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>quest_infos.vars</code>.
     */
    public void setVars(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>quest_infos.vars</code>.
     */
    public String getVars() {
        return (String) get(1);
    }

    /**
     * Setter for <code>quest_infos.comecou</code>.
     */
    public void setComecou(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>quest_infos.comecou</code>.
     */
    public Timestamp getComecou() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>quest_infos.newstreak</code>.
     */
    public void setNewstreak(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>quest_infos.newstreak</code>.
     */
    public Boolean getNewstreak() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>quest_infos.daily</code>.
     */
    public void setDaily(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>quest_infos.daily</code>.
     */
    public Boolean getDaily() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>quest_infos.playerId</code>.
     */
    public void setPlayerid(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>quest_infos.playerId</code>.
     */
    public Integer getPlayerid() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>quest_infos.qid</code>.
     */
    public void setQid(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>quest_infos.qid</code>.
     */
    public Integer getQid() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>quest_infos.completa</code>.
     */
    public void setCompleta(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>quest_infos.completa</code>.
     */
    public Boolean getCompleta() {
        return (Boolean) get(7);
    }

    /**
     * Setter for <code>quest_infos.terminar</code>.
     */
    public void setTerminar(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>quest_infos.terminar</code>.
     */
    public Timestamp getTerminar() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>quest_infos.terminou</code>.
     */
    public void setTerminou(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>quest_infos.terminou</code>.
     */
    public Timestamp getTerminou() {
        return (Timestamp) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, Timestamp, Boolean, Boolean, Integer, Integer, Boolean, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, Timestamp, Boolean, Boolean, Integer, Integer, Boolean, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return QuestInfos.QUEST_INFOS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return QuestInfos.QUEST_INFOS.VARS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return QuestInfos.QUEST_INFOS.COMECOU;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return QuestInfos.QUEST_INFOS.NEWSTREAK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field5() {
        return QuestInfos.QUEST_INFOS.DAILY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return QuestInfos.QUEST_INFOS.PLAYERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return QuestInfos.QUEST_INFOS.QID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field8() {
        return QuestInfos.QUEST_INFOS.COMPLETA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return QuestInfos.QUEST_INFOS.TERMINAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return QuestInfos.QUEST_INFOS.TERMINOU;
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
    public Timestamp component3() {
        return getComecou();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getNewstreak();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component5() {
        return getDaily();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getPlayerid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getQid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component8() {
        return getCompleta();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getTerminar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getTerminou();
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
    public Timestamp value3() {
        return getComecou();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getNewstreak();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value5() {
        return getDaily();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getPlayerid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getQid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value8() {
        return getCompleta();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getTerminar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getTerminou();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value2(String value) {
        setVars(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value3(Timestamp value) {
        setComecou(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value4(Boolean value) {
        setNewstreak(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value5(Boolean value) {
        setDaily(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value6(Integer value) {
        setPlayerid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value7(Integer value) {
        setQid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value8(Boolean value) {
        setCompleta(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value9(Timestamp value) {
        setTerminar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord value10(Timestamp value) {
        setTerminou(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestInfosRecord values(Integer value1, String value2, Timestamp value3, Boolean value4, Boolean value5, Integer value6, Integer value7, Boolean value8, Timestamp value9, Timestamp value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuestInfosRecord
     */
    public QuestInfosRecord() {
        super(QuestInfos.QUEST_INFOS);
    }

    /**
     * Create a detached, initialised QuestInfosRecord
     */
    public QuestInfosRecord(Integer id, String vars, Timestamp comecou, Boolean newstreak, Boolean daily, Integer playerid, Integer qid, Boolean completa, Timestamp terminar, Timestamp terminou) {
        super(QuestInfos.QUEST_INFOS);

        set(0, id);
        set(1, vars);
        set(2, comecou);
        set(3, newstreak);
        set(4, daily);
        set(5, playerid);
        set(6, qid);
        set(7, completa);
        set(8, terminar);
        set(9, terminou);
    }
}
