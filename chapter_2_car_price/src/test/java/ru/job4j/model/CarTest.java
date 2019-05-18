package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.repositories.HibernateUtil;

import java.time.LocalDate;
import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static ru.job4j.model.SessionRollback.create;

public class CarTest {

    private CarColor testColor;

    @Before
    public void setUp() {
        testColor = new CarColor("blackberry");

    }

    private void startTest(BiConsumer<Session, Car> consumer) {
        try (final SessionFactory sessionFactory = create(HibernateUtil.getSessionFactory());
             final Session session = create(sessionFactory.openSession())) {

            Car car = new Car(2011, "Toyota", "Camry", "universal", "AWD",
                    "Automatic", "2.5", 2500000, 3, testColor,
                    "08236567IUN655");
            session.beginTransaction();
            session.save(testColor);
            session.save(car);
            consumer.accept(session, car);
        }
    }


    @Test
    public void whenSavePerson() {
        startTest((session, car) -> {
            Car ex = new Car(2011, "Toyota", "Camry", "universal", "AWD",
                    "Automatic", "2.5", 2500000, 3, testColor,
                    "08236567IUN655");
            assertThat(session.get(Car.class, car.getId()), is(ex));
        });
    }

    @Test
    public void whenUpdatePerson() {
        startTest((session, car) -> {
            car.setDoors(5);
            car.setMileage(350000);
            session.update(car);
            Car ex = new Car(2011, "Toyota", "Camry", "universal", "AWD",
                    "Automatic", "2.5", 350000, 5, testColor,
                    "08236567IUN655");
            assertThat(session.get(Car.class, car.getId()), is(ex));
        });
    }

    @Test
    public void whenDeletePerson() {
        startTest((session, car) -> {
            session.delete(car);
            assertNull(session.get(Car.class, car.getId()));
        });
    }
}