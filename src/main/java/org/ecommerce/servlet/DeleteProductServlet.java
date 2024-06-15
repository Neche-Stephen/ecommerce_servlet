package org.ecommerce.servlet;

import org.ecommerce.connection.DatabaseConnection;
import org.ecommerce.dao.ProductDao;
import org.ecommerce.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "/Users/macbookair/Desktop/Decagon_Assignments/week-8-final/src/main/webapp/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("delete servlet is called" + id);
            ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());

            // Retrieve the product to get the image path
            Product product = productDao.getSingleProduct(id);

            if (product != null) {
                String imagePath = product.getImage();

                // Delete the product record from the database
                boolean result = productDao.deleteProduct(id);
                if (result) {
                    // Delete the image file from the server
                    if (imagePath != null && !imagePath.isEmpty()) {
                        String fullPath = UPLOAD_DIR + imagePath;
                        File imageFile = new File(fullPath);
                        if (imageFile.exists()) {
                            imageFile.delete();
                        }
                    }
                    response.sendRedirect("viewProducts.jsp");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
