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
import java.util.Optional;

@WebServlet(name = "signIn", urlPatterns = "/signIn")
public class SignInController extends HttpServlet {
    private final PersonService personService = PersonServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/SignIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<Person> person = personService.getPersonByLogin(login);
        if (person.isPresent() && person.get().getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("person_id", person.get().getId());
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("error", "Credentional is invalid");
            doGet(req, resp);
        }
    }
}
