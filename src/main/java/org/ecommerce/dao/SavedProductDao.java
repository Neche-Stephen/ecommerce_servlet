package org.ecommerce.dao;

import org.ecommerce.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedProductDao {
    private Connection con;

    public SavedProductDao(Connection con) {
        this.con = con;
    }

    public boolean isProductSaved(int userId, int productId) {
        boolean isSaved = false;
        try {
            String query = "SELECT * FROM user_saved_products WHERE user_id = ? AND product_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            ResultSet rs = pst.executeQuery();
            isSaved = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    public boolean saveProduct(int userId, int productId) {
        boolean result = false;
        try {
            String query = "INSERT INTO user_saved_products (user_id, product_id) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            result = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean unsaveProduct(int userId, int productId) {
        boolean result = false;
        try {
            String query = "DELETE FROM user_saved_products WHERE user_id = ? AND product_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            result = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Product> getSavedProducts(int userId) {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT products.* \n" +
                    "FROM products \n" +
                    "JOIN user_saved_products \n" +
                    "ON products.id = user_saved_products.product_id \n" +
                    "WHERE user_saved_products.user_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

}
