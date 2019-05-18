package ru.job4j.repositories;

import ru.job4j.model.Person;

import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao<Person, Integer> {

    private static final PersonDaoImpl REPOSITORY = new PersonDaoImpl();

    private PersonDaoImpl() {
    }

    public static PersonDaoImpl getInstance() {
        return REPOSITORY;
    }

    @Override
    public void persist(Person entity) {
        HibernateUtil.tx(session -> session.save(entity));
    }

    @Override
    public void update(Person entity) {
        HibernateUtil.tx(session -> {
            session.update(entity);
            return null;
        });
    }

    @Override
    public Person findById(Integer id) {
        return HibernateUtil.tx(session -> session.get(Person.class, id));
    }

    @Override
    public void delete(Person entity) {
        HibernateUtil.tx(session -> {
            session.delete(entity);
            return null;
        });
    }

    @Override
    public List<Person> findAll() {
        return HibernateUtil.tx(session -> session.createQuery("from Person", Person.class).list());
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return HibernateUtil.tx(session -> session.createQuery("from Person where login = :login", Person.class)
                .setParameter("login", login).uniqueResultOptional());
    }
}
