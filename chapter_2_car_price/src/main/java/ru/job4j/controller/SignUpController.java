package ru.job4j.controller;

import ru.job4j.model.Person;
import ru.job4j.service.PersonService;
import ru.job4j.service.PersonServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/registration")
public class SignUpController extends HttpServlet {
    private final PersonService personService = PersonServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Person person = new Person(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("phone"),
                LocalDate.now());

        boolean isRegister = personService.addPerson(person);
        if (isRegister) {
            HttpSession session = req.getSession();
            session.setAttribute("person_id", person.getId());
            resp.sendRedirect(String.format("%s/signIn", req.getContextPath()));
        } else {
            req.setAttribute("error", "User with this login is present");
            doGet(req, resp);
        }
    }
}
