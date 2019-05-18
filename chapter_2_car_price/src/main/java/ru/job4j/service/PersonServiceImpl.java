package ru.job4j.service;

import ru.job4j.model.Person;
import ru.job4j.repositories.PersonDaoImpl;

import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private static final PersonServiceImpl INSTANCE = new PersonServiceImpl();
    private final PersonDaoImpl personDao = PersonDaoImpl.getInstance();

    private PersonServiceImpl() {
    }

    public static PersonService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean addPerson(Person person) {
        if (this.heavePersonEmptyFields(person) || personDao.findByLogin(person.getLogin()).isPresent()) {
            return false;
        }
        personDao.persist(person);
        return true;
    }

    @Override
    public List<Person> getAllPerson() {
        return personDao.findAll();
    }

    @Override
    public Optional<Person> getPersonByLogin(String login) {
        return personDao.findByLogin(login);
    }

    private boolean heavePersonEmptyFields(Person p) {
        return p.getEmail().isEmpty()
                || p.getFirstName().isEmpty()
                || p.getLogin().isEmpty()
                || p.getPassword().isEmpty()
                || p.getPhoneNumber().isEmpty();
    }
}
