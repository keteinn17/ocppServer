/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables.records;


import eu.chargetime.ocpp.jsonserverimplementation.db.tables.User;
import org.joda.time.LocalDate;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record10<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>stevedb.user.user_pk</code>.
     */
    public UserRecord setUserPk(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.user_pk</code>.
     */
    public Integer getUserPk() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stevedb.user.ocpp_tag_pk</code>.
     */
    public UserRecord setOcppTagPk(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.ocpp_tag_pk</code>.
     */
    public Integer getOcppTagPk() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>stevedb.user.address_pk</code>.
     */
    public UserRecord setAddressPk(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.address_pk</code>.
     */
    public Integer getAddressPk() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>stevedb.user.first_name</code>.
     */
    public UserRecord setFirstName(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stevedb.user.last_name</code>.
     */
    public UserRecord setLastName(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.last_name</code>.
     */
    public String getLastName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>stevedb.user.birth_day</code>.
     */
    public UserRecord setBirthDay(LocalDate value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.birth_day</code>.
     */
    public LocalDate getBirthDay() {
        return (LocalDate) get(5);
    }

    /**
     * Setter for <code>stevedb.user.sex</code>.
     */
    public UserRecord setSex(String value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.sex</code>.
     */
    public String getSex() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stevedb.user.phone</code>.
     */
    public UserRecord setPhone(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.phone</code>.
     */
    public String getPhone() {
        return (String) get(7);
    }

    /**
     * Setter for <code>stevedb.user.e_mail</code>.
     */
    public UserRecord setEMail(String value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.e_mail</code>.
     */
    public String getEMail() {
        return (String) get(8);
    }

    /**
     * Setter for <code>stevedb.user.note</code>.
     */
    public UserRecord setNote(String value) {
        set(9, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.user.note</code>.
     */
    public String getNote() {
        return (String) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return User.USER.USER_PK;
    }

    @Override
    public Field<Integer> field2() {
        return User.USER.OCPP_TAG_PK;
    }

    @Override
    public Field<Integer> field3() {
        return User.USER.ADDRESS_PK;
    }

    @Override
    public Field<String> field4() {
        return User.USER.FIRST_NAME;
    }

    @Override
    public Field<String> field5() {
        return User.USER.LAST_NAME;
    }

    @Override
    public Field<LocalDate> field6() {
        return User.USER.BIRTH_DAY;
    }

    @Override
    public Field<String> field7() {
        return User.USER.SEX;
    }

    @Override
    public Field<String> field8() {
        return User.USER.PHONE;
    }

    @Override
    public Field<String> field9() {
        return User.USER.E_MAIL;
    }

    @Override
    public Field<String> field10() {
        return User.USER.NOTE;
    }

    @Override
    public Integer component1() {
        return getUserPk();
    }

    @Override
    public Integer component2() {
        return getOcppTagPk();
    }

    @Override
    public Integer component3() {
        return getAddressPk();
    }

    @Override
    public String component4() {
        return getFirstName();
    }

    @Override
    public String component5() {
        return getLastName();
    }

    @Override
    public LocalDate component6() {
        return getBirthDay();
    }

    @Override
    public String component7() {
        return getSex();
    }

    @Override
    public String component8() {
        return getPhone();
    }

    @Override
    public String component9() {
        return getEMail();
    }

    @Override
    public String component10() {
        return getNote();
    }

    @Override
    public Integer value1() {
        return getUserPk();
    }

    @Override
    public Integer value2() {
        return getOcppTagPk();
    }

    @Override
    public Integer value3() {
        return getAddressPk();
    }

    @Override
    public String value4() {
        return getFirstName();
    }

    @Override
    public String value5() {
        return getLastName();
    }

    @Override
    public LocalDate value6() {
        return getBirthDay();
    }

    @Override
    public String value7() {
        return getSex();
    }

    @Override
    public String value8() {
        return getPhone();
    }

    @Override
    public String value9() {
        return getEMail();
    }

    @Override
    public String value10() {
        return getNote();
    }

    @Override
    public UserRecord value1(Integer value) {
        setUserPk(value);
        return this;
    }

    @Override
    public UserRecord value2(Integer value) {
        setOcppTagPk(value);
        return this;
    }

    @Override
    public UserRecord value3(Integer value) {
        setAddressPk(value);
        return this;
    }

    @Override
    public UserRecord value4(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public UserRecord value5(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public UserRecord value6(LocalDate value) {
        setBirthDay(value);
        return this;
    }

    @Override
    public UserRecord value7(String value) {
        setSex(value);
        return this;
    }

    @Override
    public UserRecord value8(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserRecord value9(String value) {
        setEMail(value);
        return this;
    }

    @Override
    public UserRecord value10(String value) {
        setNote(value);
        return this;
    }

    @Override
    public UserRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, LocalDate value6, String value7, String value8, String value9, String value10) {
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
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Integer userPk, Integer ocppTagPk, Integer addressPk, String firstName, String lastName, LocalDate birthDay, String sex, String phone, String eMail, String note) {
        super(User.USER);

        setUserPk(userPk);
        setOcppTagPk(ocppTagPk);
        setAddressPk(addressPk);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDay(birthDay);
        setSex(sex);
        setPhone(phone);
        setEMail(eMail);
        setNote(note);
    }
}
