package org.ecommerce.servlet;

import org.ecommerce.connection.DatabaseConnection;
import org.ecommerce.dao.SavedProductDao;
import org.ecommerce.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ecommerce.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



@WebServlet("/saved-products")
public class SavedProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try{
          // Check if user is authenticated
          User auth = (User) request.getSession().getAttribute("auth");
          if (auth == null) {
              // If not authenticated, redirect to login page
              response.sendRedirect("login.jsp");
              return;
          }

          System.out.println(" Saved product servlet is called");
//          int userId = auth.getId();
          int userId = Integer.parseInt(request.getParameter("user_id"));
          int productId = Integer.parseInt(request.getParameter("product_id"));
          String sourcePage = request.getParameter("source_page");

          SavedProductDao savedProductDao = new SavedProductDao(DatabaseConnection.getConnection());

          boolean isSaved = savedProductDao.isProductSaved(userId, productId);
          if (isSaved) {
              savedProductDao.unsaveProduct(userId, productId);
          } else {
              savedProductDao.saveProduct(userId, productId);
          }

//          response.sendRedirect("index.jsp");
          response.sendRedirect(sourcePage != null ? sourcePage : "index.jsp");
      } catch (SQLException e) {
          throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
      }
    }
}



//@WebServlet("/saved-products")
//public class SavedProductServlet extends HttpServlet {
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String method = request.getParameter("_method");
//        int userId = Integer.parseInt(request.getParameter("user_id"));
//        int productId = Integer.parseInt(request.getParameter("product_id"));
//
//        Connection con = (Connection) getServletContext().getAttribute("con");
//        SavedProductDao savedProductDao = new SavedProductDao(con);
//
//        boolean result;
//        if (method != null && method.equals("delete")) {
//            result = savedProductDao.removeSavedProduct(userId, productId);
//            response.getWriter().write(result ? "Product removed successfully" : "Failed to remove product");
//        } else {
//            result = savedProductDao.saveProduct(userId, productId);
//            response.getWriter().write(result ? "Product saved successfully" : "Failed to save product");
//        }
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int userId = Integer.parseInt(request.getParameter("user_id"));
//
//        Connection con = (Connection) getServletContext().getAttribute("con");
//        SavedProductDao savedProductDao = new SavedProductDao(con);
//
//        List<Product> savedProducts = savedProductDao.getSavedProducts(userId);
//        request.setAttribute("savedProducts", savedProducts);
//        request.getRequestDispatcher("savedProducts.jsp").forward(request, response);
//    }
//}
