package ru.job4j.repositories;

import ru.job4j.model.CarItem;

import java.io.Serializable;
import java.util.List;

public interface CarItemDao<T, Id extends Serializable>  extends Repository<T, Id> {
    List<CarItem> findOpenItems();
    List<CarItem> findCloseItems();
    List<CarItem> findAllUserItem(int id);
}
