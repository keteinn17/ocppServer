/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables.records;


import eu.chargetime.ocpp.jsonserverimplementation.db.tables.Address;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AddressRecord extends UpdatableRecordImpl<AddressRecord> implements Record6<Integer, String, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>stevedb.address.address_pk</code>.
     */
    public AddressRecord setAddressPk(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.address.address_pk</code>.
     */
    public Integer getAddressPk() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stevedb.address.street</code>.
     */
    public AddressRecord setStreet(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.address.street</code>.
     */
    public String getStreet() {
        return (String) get(1);
    }

    /**
     * Setter for <code>stevedb.address.house_number</code>.
     */
    public AddressRecord setHouseNumber(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.address.house_number</code>.
     */
    public String getHouseNumber() {
        return (String) get(2);
    }

    /**
     * Setter for <code>stevedb.address.zip_code</code>.
     */
    public AddressRecord setZipCode(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.address.zip_code</code>.
     */
    public String getZipCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stevedb.address.city</code>.
     */
    public AddressRecord setCity(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.address.city</code>.
     */
    public String getCity() {
        return (String) get(4);
    }

    /**
     * Setter for <code>stevedb.address.country</code>.
     */
    public AddressRecord setCountry(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.address.country</code>.
     */
    public String getCountry() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Address.ADDRESS.ADDRESS_PK;
    }

    @Override
    public Field<String> field2() {
        return Address.ADDRESS.STREET;
    }

    @Override
    public Field<String> field3() {
        return Address.ADDRESS.HOUSE_NUMBER;
    }

    @Override
    public Field<String> field4() {
        return Address.ADDRESS.ZIP_CODE;
    }

    @Override
    public Field<String> field5() {
        return Address.ADDRESS.CITY;
    }

    @Override
    public Field<String> field6() {
        return Address.ADDRESS.COUNTRY;
    }

    @Override
    public Integer component1() {
        return getAddressPk();
    }

    @Override
    public String component2() {
        return getStreet();
    }

    @Override
    public String component3() {
        return getHouseNumber();
    }

    @Override
    public String component4() {
        return getZipCode();
    }

    @Override
    public String component5() {
        return getCity();
    }

    @Override
    public String component6() {
        return getCountry();
    }

    @Override
    public Integer value1() {
        return getAddressPk();
    }

    @Override
    public String value2() {
        return getStreet();
    }

    @Override
    public String value3() {
        return getHouseNumber();
    }

    @Override
    public String value4() {
        return getZipCode();
    }

    @Override
    public String value5() {
        return getCity();
    }

    @Override
    public String value6() {
        return getCountry();
    }

    @Override
    public AddressRecord value1(Integer value) {
        setAddressPk(value);
        return this;
    }

    @Override
    public AddressRecord value2(String value) {
        setStreet(value);
        return this;
    }

    @Override
    public AddressRecord value3(String value) {
        setHouseNumber(value);
        return this;
    }

    @Override
    public AddressRecord value4(String value) {
        setZipCode(value);
        return this;
    }

    @Override
    public AddressRecord value5(String value) {
        setCity(value);
        return this;
    }

    @Override
    public AddressRecord value6(String value) {
        setCountry(value);
        return this;
    }

    @Override
    public AddressRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AddressRecord
     */
    public AddressRecord() {
        super(Address.ADDRESS);
    }

    /**
     * Create a detached, initialised AddressRecord
     */
    public AddressRecord(Integer addressPk, String street, String houseNumber, String zipCode, String city, String country) {
        super(Address.ADDRESS);

        setAddressPk(addressPk);
        setStreet(street);
        setHouseNumber(houseNumber);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
    }
}
