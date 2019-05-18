package ru.job4j.service;

import ru.job4j.model.CarItem;
import ru.job4j.model.Person;
import ru.job4j.repositories.CarItemDao;
import ru.job4j.repositories.CarItemDaoImpl;
import ru.job4j.repositories.PersonDaoImpl;

import java.util.List;

public class CarItemServiceImpl implements CarItemService {

    private final static CarItemService INSTANCE = new CarItemServiceImpl();
    private final CarItemDao carItemDao = CarItemDaoImpl.getInstance();

    private CarItemServiceImpl() {
    }

    public static CarItemService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<CarItem> getAllCarItems() {
        return carItemDao.findAll();
    }

    @Override
    public List<CarItem> getAllOpenItems() {
        return carItemDao.findOpenItems();
    }

    @Override
    public List<CarItem> getAllCloseItems() {
        return carItemDao.findCloseItems();
    }

    @Override
    public List<CarItem> getAllUserItems(int userId) {
        return carItemDao.findAllUserItem(userId);
    }

    @Override
    public boolean addCarItem(CarItem carItem) {
        carItemDao.persist(carItem);
        return true;
    }

}
