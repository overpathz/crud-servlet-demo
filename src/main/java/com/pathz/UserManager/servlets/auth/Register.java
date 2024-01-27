package com.pathz.UserManager.servlets.auth;

import com.pathz.UserManager.dao.UserDAO;
import com.pathz.UserManager.models.User;
import com.pathz.UserManager.util.EncryptVerify;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("log_reg/reg.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_pswd");

        String encryptedPassword = EncryptVerify.encryptPassword(password);
        User user = new User(username, encryptedPassword);

        try {
            if (UserDAO.isExistWithName(user)) {
                request.setAttribute("req_message", "User is already exist!");
                request.getRequestDispatcher("log_reg/reg.jsp").forward(request, response);
            }
            else if (!password.equals(confirm_password)) {
                request.setAttribute("req_message", "Passwords are not the same!");
                request.getRequestDispatcher("log_reg/reg.jsp").forward(request, response);
            }
            else {
                UserDAO.insertUser(user);
                response.sendRedirect("log_reg/login.jsp");
            }

            formEmailAndSend(user);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void formEmailAndSend(User user) {
        // ...
    }
}
