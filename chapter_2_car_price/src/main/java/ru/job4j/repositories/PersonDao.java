package ru.job4j.repositories;

import ru.job4j.model.Person;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface PersonDao<T, Id extends Serializable> extends Repository<T, Id> {
    Optional<T> findByLogin(String login);
}
