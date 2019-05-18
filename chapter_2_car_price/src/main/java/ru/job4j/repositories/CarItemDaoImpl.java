package ru.job4j.repositories;

import org.hibernate.query.Query;
import ru.job4j.model.Car;
import ru.job4j.model.CarItem;

import java.util.List;

public class CarItemDaoImpl implements CarItemDao<CarItem, Integer> {

    private static final CarItemDaoImpl REPOSITORY = new CarItemDaoImpl();

    private CarItemDaoImpl() {
    }

    public static CarItemDaoImpl getInstance() {
        return REPOSITORY;
    }

    @Override
    public void persist(CarItem entity) {
        HibernateUtil.tx(session -> {
            Car car = entity.getCar();
            session.save(car);
            return session.save(entity);
        });
    }

    @Override
    public void update(CarItem entity) {
        HibernateUtil.tx(session -> {
            session.update(entity);
            return null;
        });
    }

    @Override
    public CarItem findById(Integer id) {
        return HibernateUtil.tx(session -> session.get(CarItem.class, id));
    }

    @Override
    public void delete(CarItem entity) {
        HibernateUtil.tx(session -> {
            session.delete(entity);
            return null;
        });
    }

    @Override
    public List<CarItem> findAll() {
        return HibernateUtil.tx(session -> session.createQuery("from CarItem", CarItem.class).list());
    }

    @Override
    public List<CarItem> findOpenItems() {
        return HibernateUtil.tx(session -> session.
                createQuery("from CarItem where sold = false", CarItem.class).list());
    }

    @Override
    public List<CarItem> findCloseItems() {
        return HibernateUtil.tx(session -> session.
                createQuery("from CarItem where sold = true", CarItem.class).list());
    }

    @Override
    public List<CarItem> findAllUserItem(int id) {
        return HibernateUtil.tx(session -> {
            return session.createQuery("from CarItem item where item.person.id =: id", CarItem.class)
                    .setParameter("id", id)
                    .list();
        });
    }
}
