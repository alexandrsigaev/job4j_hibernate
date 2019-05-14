package ru.job4j.service;

import ru.job4j.model.Item;
import ru.job4j.repositories.ItemDaoImpl;

import java.util.List;
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
        ItemDAO.openCurrentSessionWithTransaction();
        ItemDAO.persist(item);
        ItemDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public List<Item> findAll() {
        ItemDAO.openCurrentSessionWithTransaction();
        List<Item> items = ItemDAO.findAll();
        ItemDAO.closeCurrentSessionWithTransaction();
        return items;
    }

    @Override
    public List<Item> findOpenItem() {
        ItemDAO.openCurrentSessionWithTransaction();
        List<Item> items = ItemDAO.findAll();
        ItemDAO.closeCurrentSessionWithTransaction();
        return items.stream().filter(item -> !item.isDone()).collect(Collectors.toList());
    }

    @Override
    public List<Item> findCloseItem() {
        ItemDAO.openCurrentSessionWithTransaction();
        List<Item> items = ItemDAO.findAll();
        ItemDAO.closeCurrentSessionWithTransaction();
        return items.stream().filter(Item::isDone).collect(Collectors.toList());
    }
}
