package ru.job4j.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.model.Item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemDaoImpl implements ItemDao<Item, Integer> {

    private static final ItemDaoImpl ITEM_DAO = new ItemDaoImpl();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return ITEM_DAO;
    }

    @Override
    public void persist(Item entity) {
        this.tx(session -> session.save(entity));
    }

    @Override
    public void update(Item entity) {
        this.vtx(session -> session.update(entity));
    }

    @Override
    public Item findById(Integer id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    @Override
    public void delete(Item entity) {
        this.vtx(session -> session.delete(entity));
    }

    @Override
    public List<Item> findAll() {
        return this.tx(session -> session.createQuery("from Item", Item.class).list());
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void vtx(final Consumer<Session> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
