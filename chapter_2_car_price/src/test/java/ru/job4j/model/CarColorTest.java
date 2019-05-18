package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import ru.job4j.repositories.HibernateUtil;

import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static ru.job4j.model.SessionRollback.create;

public class CarColorTest {

    private void startTest(BiConsumer<Session, CarColor> consumer) {
        try (final SessionFactory sessionFactory = create(HibernateUtil.getSessionFactory());
             final Session session = create(sessionFactory.openSession())) {

            CarColor carColor = new CarColor("qwer");

            session.beginTransaction();
            session.save(carColor);
            consumer.accept(session, carColor);
        }
    }

    @Test
    public void whenSavePerson() {
        startTest((session, carColor) -> {
            CarColor ex = new CarColor("qwer");
            assertThat(session.get(CarColor.class, carColor.getId()), is(ex));
        });
    }

    @Test
    public void whenUpdatePerson() {
        startTest((session, carColor) -> {
            carColor.setColorName("rewq");
            CarColor ex = new CarColor("rewq");
            assertThat(session.get(CarColor.class, carColor.getId()), is(ex));
        });
    }

    @Test
    public void whenDeletePerson() {
        startTest((session, carColor) -> {
            session.delete(carColor);
            assertNull(session.get(CarColor.class, carColor.getId()));
        });
    }
}