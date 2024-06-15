package org.ecommerce.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ecommerce.connection.DatabaseConnection;
import org.ecommerce.dao.UserDao;
import org.ecommerce.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create User object with input values
        User user = new User();
        user.setFullname(name);
        user.setEmail(email);
        user.setPassword(password);

        try {
            Connection con = DatabaseConnection.getConnection(); // Establish DB connection
            UserDao userDao = new UserDao(con);

            // Check if the user already exists
            if (userDao.getUserByEmail(email) != null) {
                response.getWriter().write("User with this email already exists.");
                return;
            }

            // Attempt to insert the new user
            boolean result = userDao.addUser(user);
            if (result) {
                response.sendRedirect("login.jsp"); // Redirect to login page after successful signup
            } else {
                response.getWriter().write("Failed to sign up. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database connection problem.", e);
        }
    }
}
