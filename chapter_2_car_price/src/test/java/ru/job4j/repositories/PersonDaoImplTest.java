package ru.job4j.repositories;

import org.junit.Test;
import ru.job4j.model.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PersonDaoImplTest {

    private final PersonDao<Person, Integer> personDao = PersonDaoImpl.getInstance();

    @Test
    public void persist() {
        Person person = new Person("persist", "pwrd", "Name", "email@123.com",
                "1234567890", LocalDate.now());
        personDao.persist(person);
        Optional<Person> candidate = this.personDao.findByLogin("persist");
        assertTrue(candidate.isPresent());
        assertThat(person, is(candidate.get()));
        assertThat(person, is(this.personDao.findById(person.getId())));
    }

    @Test
    public void update() {
        LocalDate date = LocalDate.now();
        Person person = new Person("update", "pwrdFirst", "NameFirst", "email@123.com",
                "1234567890", date);
        this.personDao.persist(person);
        Person personUpdate = new Person("update", "pwrdSecond", "NameSecond", "email@321.com",
                "0987654321", date);
        personUpdate.setId(person.getId());
        this.personDao.update(personUpdate);
        Optional<Person> candidate = this.personDao.findByLogin("update");
        assertTrue(candidate.isPresent());
        assertThat(personUpdate, is(candidate.get()));
        assertThat(personUpdate, is(this.personDao.findById(person.getId())));
    }

    @Test
    public void delete() {
        LocalDate date = LocalDate.now();
        Person person = new Person("delete", "pwrdFirst", "NameFirst", "email@123.com",
                "1234567890", date);
        this.personDao.persist(person);
        this.personDao.delete(person);
        Optional<Person> candidate = this.personDao.findByLogin("delete");
        assertTrue(candidate.isEmpty());
        assertNull(this.personDao.findById(person.getId()));
    }

    @Test
    public void findAll() {
        Person personOne = new Person("findFirst", "pwrdFirst", "NameFirst", "email@123.com",
                "1234567890", LocalDate.now());
        this.personDao.persist(personOne);
        Person personTwo = new Person("findSecond", "pwrdSecond", "NameSecond", "email@321.com",
                "0987654321", LocalDate.now());
        this.personDao.persist(personTwo);

        List<Person> persons = this.personDao.findAll();

        assertTrue(persons.contains(personOne) && persons.contains(personTwo));

    }

}