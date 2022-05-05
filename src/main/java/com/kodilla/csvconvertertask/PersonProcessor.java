package com.kodilla.csvconvertertask;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class PersonProcessor implements ItemProcessor<SpecificInformationPerson, GeneralInformationPerson> {
    @Override
    public GeneralInformationPerson process(SpecificInformationPerson person) throws Exception {
        LocalDate birthday = LocalDate.parse(person.getBirthdayDate());
        int agePerson = LocalDate.now().getYear() - birthday.getYear();
        return new GeneralInformationPerson(person.getName(), person.getSurname(), agePerson);
    }
}
