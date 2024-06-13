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

@WebServlet("/addProduct") // URL pattern for this servlet
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "src/main/resources";

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Forward to the JSP page to display the form
//        request.getRequestDispatcher("addProduct.jsp").forward(request, response);
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));

            Part filePart = request.getPart("image");
            String fileName = filePart.getSubmittedFileName();

            // Using System.getProperty("user.dir") to get the current working directory
            String uploadPath = System.getProperty("user.dir") + File.separator + UPLOAD_DIR;

            // Create the uploads directory if it does not exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Save the file to the server
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            System.out.println(filePath);

            // Save the relative path to the database
            String imagePath = UPLOAD_DIR + "/" + fileName;

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setPrice(price);
            product.setImage(imagePath);

            ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());

            boolean result = productDao.addProduct(product);
            if (result) {
                request.setAttribute("success", "Product added successfully!");
            }

            request.getRequestDispatcher("addProduct.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
