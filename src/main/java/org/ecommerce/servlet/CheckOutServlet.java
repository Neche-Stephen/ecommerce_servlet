package org.ecommerce.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ecommerce.connection.DatabaseConnection;
import org.ecommerce.dao.OrderDao;
import org.ecommerce.model.Cart;
import org.ecommerce.model.Order;
import org.ecommerce.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("called");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
            User auth = (User) request.getSession().getAttribute("auth");

            if (cart_list != null && auth != null) {
                for (Cart c : cart_list) {
                    Order order = new Order();
                    order.setId(c.getId());
                    order.setUserId(auth.getId());
                    order.setQuantity(c.getQuantity());

                    OrderDao oDao = new OrderDao(DatabaseConnection.getConnection());
                    boolean result = oDao.insertOrder(order);
                    if (!result) {
                        // Handle the case where the order insertion failed
                        response.sendRedirect("error.jsp");
                        return; // Exit the method after redirecting
                    }
                }
                cart_list.clear();
                response.sendRedirect("orders.jsp");
            } else {
                if (auth == null) {
                    response.sendRedirect("login.jsp");
                } else {
                    response.sendRedirect("cart.jsp");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
