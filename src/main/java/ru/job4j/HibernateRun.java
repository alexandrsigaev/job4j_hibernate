package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.model.User;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class HibernateRun {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        User user = new User("Aleksandr", Calendar.getInstance());

        session.beginTransaction();
        Serializable user_id = session.save(user);
        session.getTransaction().commit();

        session.beginTransaction();
        User userFromDB = session.get(User.class, user_id);
        session.getTransaction().commit();

        userFromDB.setName("Oleg");

        session.beginTransaction();
        session.update(userFromDB);
        session.getTransaction().commit();

        session.beginTransaction();
        List<User> users = session.createQuery("from User", User.class).list();
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(userFromDB);
        session.getTransaction().commit();

        session.close();    }
}
