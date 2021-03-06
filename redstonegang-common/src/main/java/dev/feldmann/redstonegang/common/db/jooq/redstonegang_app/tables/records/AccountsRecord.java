/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.tables.records;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_app.tables.Accounts;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class AccountsRecord extends UpdatableRecordImpl<AccountsRecord> implements Record7<UInteger, String, Long, Boolean, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 2039749990;

    /**
     * Setter for <code>redstonegang_app.accounts.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>redstonegang_app.accounts.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>redstonegang_app.accounts.forum_id</code>.
     */
    public void setForumId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.forum_id</code>.
     */
    public Long getForumId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>redstonegang_app.accounts.premium</code>.
     */
    public void setPremium(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.premium</code>.
     */
    public Boolean getPremium() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>redstonegang_app.accounts.remember_token</code>.
     */
    public void setRememberToken(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.remember_token</code>.
     */
    public String getRememberToken() {
        return (String) get(4);
    }

    /**
     * Setter for <code>redstonegang_app.accounts.created_at</code>.
     */
    public void setCreatedAt(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>redstonegang_app.accounts.updated_at</code>.
     */
    public void setUpdatedAt(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>redstonegang_app.accounts.updated_at</code>.
     */
    public Timestamp getUpdatedAt() {
        return (Timestamp) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UInteger> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<UInteger, String, Long, Boolean, String, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<UInteger, String, Long, Boolean, String, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return Accounts.ACCOUNTS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Accounts.ACCOUNTS.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return Accounts.ACCOUNTS.FORUM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return Accounts.ACCOUNTS.PREMIUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Accounts.ACCOUNTS.REMEMBER_TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return Accounts.ACCOUNTS.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return Accounts.ACCOUNTS.UPDATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component3() {
        return getForumId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getPremium();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getRememberToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getForumId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getPremium();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getRememberToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpdatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value2(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value3(Long value) {
        setForumId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value4(Boolean value) {
        setPremium(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value5(String value) {
        setRememberToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value6(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord value7(Timestamp value) {
        setUpdatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountsRecord values(UInteger value1, String value2, Long value3, Boolean value4, String value5, Timestamp value6, Timestamp value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountsRecord
     */
    public AccountsRecord() {
        super(Accounts.ACCOUNTS);
    }

    /**
     * Create a detached, initialised AccountsRecord
     */
    public AccountsRecord(UInteger id, String username, Long forumId, Boolean premium, String rememberToken, Timestamp createdAt, Timestamp updatedAt) {
        super(Accounts.ACCOUNTS);

        set(0, id);
        set(1, username);
        set(2, forumId);
        set(3, premium);
        set(4, rememberToken);
        set(5, createdAt);
        set(6, updatedAt);
    }
}
