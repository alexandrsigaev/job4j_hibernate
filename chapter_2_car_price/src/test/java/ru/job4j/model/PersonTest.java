package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.job4j.repositories.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static ru.job4j.model.SessionRollback.create;

public class PersonTest {

    private LocalDate date = LocalDate.now();

    private void startTest(BiConsumer<Session, Person> consumer) {
        try (final SessionFactory sessionFactory = create(HibernateUtil.getSessionFactory());
             final Session session = create(sessionFactory.openSession())) {
            Person person = new Person("login", "pwrd", "Name", "email@123.com",
                    "1234567890", date);

            session.beginTransaction();
            session.save(person);
            consumer.accept(session, person);
        }
    }

    @Test
    public void whenSavePerson() {
        startTest((session, person) -> {
            Person ex = new Person("login", "pwrd", "Name", "email@123.com",
                    "1234567890", date);
            assertThat(session.get(Person.class, person.getId()), is(ex));
        });
    }

    @Test
    public void whenUpdatePerson() {
        startTest((session, person) -> {
            person.setEmail("update_email@123.com");
            person.setPassword("qwerty");
            person.setPhoneNumber("0987654321");
            Person ex = new Person("login", "qwerty", "Name", "update_email@123.com",
                    "0987654321", date);
            assertThat(session.get(Person.class, person.getId()), is(ex));
        });
    }

    @Test
    public void whenDeletePerson() {
        startTest((session, person) -> {
            session.delete(person);
            assertNull(session.get(Person.class, person.getId()));
        });
    }

    @Test
    public void whenGetListCarItemsFromUser() {
        startTest((session, person) -> {

            CarColor testColor = new CarColor("blackberry");
            session.save(testColor);

            Car testCar = new Car(2011, "Toyota", "Camry", "universal", "AWD",
                    "Automatic", "2.5", 2500000, 3, testColor,
                    "08236567IUN655");
            session.save(testCar);

            CarItem testItem = new CarItem(person, 300000, "SPB, Mira 123",
                    "Good price", LocalDateTime.now(), testCar, false);
            session.save(testItem);

            CarImage testImage = new CarImage(testItem, "/123.jpg");
            session.save(testImage);

            person.setCarItems(List.of(testItem));
            session.update(person);

            Person ex = new Person("login", "pwrd", "Name", "email@123.com",
                    "1234567890", date);
            ex.setCarItems(List.of(testItem));

            assertThat(session.get(Person.class, person.getId()), is(ex));
        });
    }
}