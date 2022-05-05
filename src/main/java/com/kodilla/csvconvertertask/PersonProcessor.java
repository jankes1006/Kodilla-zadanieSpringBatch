package com.kodilla.csvconvertertask;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class PersonProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        LocalDate birthday = LocalDate.parse(person.getDate());
        int agePerson = LocalDate.now().getYear() - birthday.getYear();
        return new Person(person.getName(), person.getSurname(), String.valueOf(agePerson));
    }
}
