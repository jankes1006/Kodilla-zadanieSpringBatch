package com.kodilla.csvconvertertask;

public class SpecificInformationPerson {

    private String name;
    private String surname;
    private String birthdayDate;

    public SpecificInformationPerson(String name, String surname, String birthdayDate) {
        this.name = name;
        this.surname = surname;
        this.birthdayDate = birthdayDate;
    }

    public SpecificInformationPerson() {
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

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
}
