package com.kodilla.csvconvertertask;

public class Person {
    private String name;
    private String surname;
    private String date;

    public Person(String name, String surname, String date) {
        this.name = name;
        this.surname = surname;
        this.date = date;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
