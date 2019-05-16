package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.job4j.model.Item;
import ru.job4j.service.ItemService;
import ru.job4j.service.ItemServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ItemController", urlPatterns = "/item", loadOnStartup = 1)
public class ItemsController extends HttpServlet {

    private final ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("status");
        List<Item> items = null;
        if (action.equals("all")) {
            items = itemService.findAll();
        } else if (action.equals("open")) {
            items = itemService.findOpenItem();
        } else if (action.equals("close")) {
            items = itemService.findCloseItem();
        }
        resp.setContentType("text/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        objectMapper.writeValue(pw, items);
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String jsonItem = reader.readLine();
        System.out.println(jsonItem);
        ObjectMapper objectMapper = new ObjectMapper();
        Item item = objectMapper.readValue(jsonItem, Item.class);
        itemService.add(item);
    }


}
