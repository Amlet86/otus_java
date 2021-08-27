package ru.amlet.servlet;

import java.io.IOException;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.amlet.dao.UserDao;
import ru.amlet.model.Client;


public class UsersApiServlet extends HttpServlet {

    private final UserDao userDao;
    private final Gson gson;

    public UsersApiServlet(UserDao userDao, Gson gson) {
        this.userDao = userDao;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = userDao.saveClient(extractClientsParametersFromRequest(request));

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    private Client extractClientsParametersFromRequest(HttpServletRequest request) {
        Client client = new Client();
        client.setName(request.getParameter("name"));
        client.setLogin(request.getParameter("login"));
        client.setPassword(request.getParameter("password"));
        client.setRole(request.getParameter("role"));

        return client;
    }

}
