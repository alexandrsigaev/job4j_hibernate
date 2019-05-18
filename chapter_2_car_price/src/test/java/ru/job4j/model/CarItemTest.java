package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.repositories.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static ru.job4j.model.SessionRollback.create;

public class CarItemTest {

    private LocalDateTime date = LocalDateTime.now();
    private Person person;
    private CarColor testColor;
    private Car testCar;

    @Before
    public void setUp() {
        person = new Person("login", "pwrd", "Name", "email@123.com",
                "1234567890", LocalDate.now());
        testColor = new CarColor("blackberry");
        testCar = new Car(2011, "Toyota", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
    }


    private void startTest(BiConsumer<Session, CarItem> consumer) {
        try (final SessionFactory sessionFactory = create(HibernateUtil.getSessionFactory());
             final Session session = create(sessionFactory.openSession())) {

            CarItem carItem = new CarItem(person, 2000, "MSK",
                    "Good", date, testCar, false);

            session.beginTransaction();
            session.save(person);
            session.save(testColor);
            session.save(testCar);
            session.save(carItem);
            consumer.accept(session, carItem);
        }
    }

    @Test
    public void whenSavePerson() {
        startTest((session, carItem) -> {
            CarItem ex = new CarItem(person, 2000, "MSK",
                    "Good", date, testCar, false);
            ex.setId(carItem.getId());
            assertThat(session.get(CarItem.class, carItem.getId()), is(ex));
        });
    }

    @Test
    public void whenUpdatePerson() {
        startTest((session, carItem) -> {
            carItem.setAddressLocality("SPb");
            carItem.setPrice(12344);
            CarItem ex = new CarItem(person, 12344, "SPb",
                    "Good", date, testCar, false);
            ex.setId(carItem.getId());
            assertThat(session.get(CarItem.class, carItem.getId()), is(ex));
        });
    }

    @Test
    public void whenDeletePerson() {
        startTest((session, carItem) -> {
            session.delete(carItem);
            assertNull(session.get(CarItem.class, carItem.getId()));
        });
    }

}