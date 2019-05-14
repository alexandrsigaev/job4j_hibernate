package ru.job4j.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.job4j.model.Item;

import java.util.List;

public class ItemDaoImpl implements ItemDao<Item, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;
    private static final ItemDaoImpl ITEM_DAO = new ItemDaoImpl();

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return ITEM_DAO;
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void persist(Item entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Item entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Item findById(Integer id) {
        return (Item) getCurrentSession().get(Item.class, id);
    }

    @Override
    public void delete(Item entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<Item> findAll() {
        return getCurrentSession().createQuery("from Item", Item.class).list();
    }
}
