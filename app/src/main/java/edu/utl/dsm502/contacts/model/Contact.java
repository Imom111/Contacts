package edu.utl.dsm502.contacts.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private String name;
    private String number;
    private String eMail;
    private String address;
    private String photography;

    public Contact() {
    }

    public Contact(int id, String name, String number, String eMail, String address, String photography) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.eMail = eMail;
        this.address = address;
        this.photography = photography;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotography() {
        return photography;
    }

    public void setPhotography(String photography) {
        this.photography = photography;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", eMail='" + eMail + '\'' +
                ", address='" + address + '\'' +
                ", photography='" + photography + '\'' +
                '}';
    }
}
