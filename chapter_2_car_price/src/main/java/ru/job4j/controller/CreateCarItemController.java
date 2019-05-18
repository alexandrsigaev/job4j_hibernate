package ru.job4j.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.*;
import ru.job4j.service.CarItemService;
import ru.job4j.service.CarItemServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(urlPatterns = "/car_item/create")
public class CreateCarItemController extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(CreateCarItemController.class);
    private final CarItemService service = CarItemServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/create_car_item.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("data");

        if (action.equals("item")) {
            CarItem carItem = this.createCarItemFromRequest(req);
            service.addCarItem(carItem);
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else if (action.equals("photo")) {
            photoUpload(req);
        }
    }


    private CarItem createCarItemFromRequest(HttpServletRequest req) {
        Person person = new Person();
        int personId = (Integer) req.getSession().getAttribute("person_id");
        person.setId(personId);

        CarColor color = new CarColor();
        color.setId(Integer.parseInt(req.getParameter("color")));

        Car car = new Car(
                Integer.parseInt(req.getParameter("year")),
                req.getParameter("make"),
                req.getParameter("model"),
                req.getParameter("modification"),
                req.getParameter("driveTrain"),
                req.getParameter("transmission"),
                req.getParameter("engine"),
                Integer.parseInt(req.getParameter("millage")),
                Integer.parseInt(req.getParameter("doors")),
                color,
                req.getParameter("vin"));

        return new CarItem(
                person,
                Integer.parseInt(req.getParameter("price")),
                req.getParameter("address"),
                req.getParameter("desc"),
                LocalDateTime.now(),
                car,
                false);
    }

    private void photoUpload(HttpServletRequest request) {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // constructs the directory path to store upload file
        // this path is relative to application's directory
        StringBuilder uploadPath = new StringBuilder();

        uploadPath
                .append(getServletContext().getRealPath(""))
                .append(File.separator)
                .append("image")
                .append(File.separator)
                .append(request.getSession().getAttribute("person_id"));


        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField()) {
                        String fileName = item.getName();

                        File path = new File(uploadPath.toString());
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        File uploadedFile = new File(path + File.separator + fileName);

                        LOG.info("File upload: " + uploadedFile.getCanonicalPath());
                        LOG.info("File upload: " + uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
