package com.reece.model;

import java.util.Objects;

/**
 * This class holding the address book information.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookInfoRecord {

    private long id;
    private String name;

    public AddressBookInfoRecord() {}

    public AddressBookInfoRecord(String name) {
        this.name = name;
    }

    public AddressBookInfoRecord(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookInfoRecord that = (AddressBookInfoRecord) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AddressBookInfoRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
