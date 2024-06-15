package org.ecommerce.servlet;

import org.ecommerce.connection.DatabaseConnection;
import org.ecommerce.dao.ProductDao;
import org.ecommerce.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/UpdateProductServlet")
@MultipartConfig
public class UpdateProductServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "/Users/macbookair/Desktop/Decagon_Assignments/week-8-final/src/main/webapp/database-images";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try{
           int id = Integer.parseInt(request.getParameter("id"));

           ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());
           Product product = productDao.getSingleProduct(id);

           request.setAttribute("product", product);
           System.out.println("from updatep servlet" + product);
           request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));

            String imagePath = null;
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();

                // Using the same manual path as in AddProductServlet
                String myImagePath = UPLOAD_DIR + "/" + fileName;
                System.out.println(myImagePath);
                filePart.write(myImagePath);

                // Save the full manual path to the database
                imagePath = "database-images/" + fileName ;
            }

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setPrice(price);
            if (imagePath != null) {
                product.setImage(imagePath);
            } else {
                ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());
                Product existingProduct = productDao.getSingleProduct(id);
                product.setImage(existingProduct.getImage());
            }
            ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());

            boolean result = productDao.updateProduct(product);
            if (result) {
                response.sendRedirect("viewProducts.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
