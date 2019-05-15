package ru.job4j.service;

import ru.job4j.model.Item;
import ru.job4j.repositories.ItemDaoImpl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService{

    private static final ItemService INSTANCE = new ItemServiceImpl();
    private final ItemDaoImpl ItemDAO = ItemDaoImpl.getInstance();

    private ItemServiceImpl() {
    }

    public static ItemService getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(Item item) {
        ItemDAO.persist(item);
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = ItemDAO.findAll();
        return items;
    }

    @Override
    public List<Item> findOpenItem() {
        List<Item> items = ItemDAO.findAll();
        return items.stream().filter(item -> !item.isDone()).collect(Collectors.toList());
    }

    @Override
    public List<Item> findCloseItem() {
        List<Item> items = ItemDAO.findAll();
        return items.stream().filter(Item::isDone).collect(Collectors.toList());
    }
}
