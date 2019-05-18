package ru.job4j.service;

import ru.job4j.model.CarItem;
import ru.job4j.model.Person;

import java.util.List;

public interface CarItemService {

    List<CarItem> getAllCarItems();
    List<CarItem> getAllOpenItems();
    List<CarItem> getAllCloseItems();
    List<CarItem> getAllUserItems(int userId);
    boolean addCarItem(CarItem carItem);

}
