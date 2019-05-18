package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.CarItem;
import ru.job4j.service.CarItemService;
import ru.job4j.service.CarItemServiceImpl;
import ru.job4j.service.PersonService;
import ru.job4j.service.PersonServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@WebServlet(name = "carItem", urlPatterns = "/find_car_item", loadOnStartup = 1)
public class CarItemController extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(CarItemController.class);
    private final CarItemService carItemService = CarItemServiceImpl.getInstance();
    private final Map<String, Function<HttpServletRequest, List<CarItem>>> actions = new HashMap<>();

    public CarItemController() {
        actions.put("all", this::getAllItems);
        actions.put("open", this::getOpenItems);
        actions.put("close", this::getCloseItems);
        actions.put("my_items", this::getMyItems);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameterMap().containsKey("find")) {
            req.getRequestDispatcher("/WEB-INF/items.html").forward(req, resp);
        } else {
            String action = req.getParameter("find");
            List<CarItem> carItems = actions.get(action).apply(req);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(resp.getOutputStream(), carItems);
        }
    }

    private List<CarItem> getAllItems(HttpServletRequest request) {
        return carItemService.getAllCarItems();
    }

    private List<CarItem> getOpenItems(HttpServletRequest request) {
        return carItemService.getAllOpenItems();
    }

    private List<CarItem> getCloseItems(HttpServletRequest request) {
        return carItemService.getAllCloseItems();
    }

    private List<CarItem> getMyItems(HttpServletRequest request) {
        List<CarItem> items = null;
        Integer personId = (Integer) request.getSession().getAttribute("person_id");
        if (personId != null) {
            items = carItemService.getAllUserItems(personId);
        }
        return items;
    }

}


