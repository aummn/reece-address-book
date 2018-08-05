package com.reece.model;

import java.util.Objects;

/**
 * This class used for mapping the contact.
 *
 * @author zhimeng
 * @version 1.0 4/8/2018
 * @since 1.0
 */
public class Contact {

    private long id;
    private String name;
    private String phone;

    public Contact() {}

    public Contact(String name) {
        this.name = name;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact(long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContents() {
        return this.name + this.phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                Objects.equals(name, contact.name) &&
                Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phone);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
