package com.pathz.UserManager.servlets.auth;

import com.pathz.UserManager.dao.UserDAO;
import com.pathz.UserManager.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("log_reg/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(username, password);

        try {
            if (UserDAO.verifyUser(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("name", username);
                response.sendRedirect("/users");

            } else {
                request.setAttribute("incorrect_data", "Incorrect login or password!");
                request.getRequestDispatcher("log_reg/login.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
