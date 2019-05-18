package ru.job4j.service;

import ru.job4j.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    boolean addPerson(Person person);
    List<Person> getAllPerson();
    Optional<Person> getPersonByLogin(String login);
}
