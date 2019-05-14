package ru.job4j.service;

import ru.job4j.model.Item;

import java.util.List;

public interface ItemService {
    void add(Item item);
    List<Item> findAll();
    List<Item> findOpenItem();
    List<Item> findCloseItem();
}
