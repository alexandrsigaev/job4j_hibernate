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

public class CarImageTest {

    private LocalDateTime date = LocalDateTime.now();
    private Person person;
    private CarColor testColor;
    private Car testCar;
    private CarItem testCarItem;

    @Before
    public void setUp() {
        person = new Person("login", "pwrd", "Name", "email@123.com",
                "1234567890", LocalDate.now());
        testColor = new CarColor("blackberry");
        testCar = new Car(2011, "Toyota", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        testCarItem = new CarItem(person, 2000, "MSK",
                "Good", date, testCar, false);
    }


    private void startTest(BiConsumer<Session, CarImage> consumer) {
        try (final SessionFactory sessionFactory = create(HibernateUtil.getSessionFactory());
             final Session session = create(sessionFactory.openSession())) {

            CarImage carImage = new CarImage(testCarItem, "/121.jpg");

            session.beginTransaction();
            session.save(person);
            session.save(testColor);
            session.save(testCar);
            session.save(testCarItem);
            session.save(carImage);

            consumer.accept(session, carImage);
        }
    }

    @Test
    public void whenSavePerson() {
        startTest((session, carImage) -> {
            CarImage ex = new CarImage(testCarItem, "/121.jpg");
            ex.setId(carImage.getId());
            assertThat(session.get(CarImage.class, carImage.getId()), is(ex));
        });
    }

    @Test
    public void whenUpdatePerson() {
        startTest((session, carImage) -> {
            carImage.setPath("/321.png");
            CarImage ex = new CarImage(testCarItem, "/321.png");
            ex.setId(carImage.getId());
            assertThat(session.get(CarImage.class, carImage.getId()), is(ex));
        });
    }

    @Test
    public void whenDeletePerson() {
        startTest((session, carImage) -> {
            session.delete(carImage);
            assertNull(session.get(CarImage.class, carImage.getId()));
        });
    }
}