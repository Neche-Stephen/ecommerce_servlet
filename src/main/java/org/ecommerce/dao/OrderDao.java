package org.ecommerce.dao;


import org.ecommerce.model.Order;
import org.ecommerce.model.Product;

import java.sql.*;
import java.util.*;

public class OrderDao {

    private Connection con;

    private String query;
    private PreparedStatement pst;
    private ResultSet rs;

    public OrderDao(Connection con) {
        super();
        this.con = con;
    }

    public boolean insertOrder(Order model) {
        boolean result = false;
        try {
            query = "insert into orders (productId, userId, quantity) values(?,?,?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getUserId());
            pst.setInt(3, model.getQuantity());
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public List<Order> userOrders(int id) {
        List<Order> list = new ArrayList<>();
        try {
            query = "select * from orders where userId=? order by orders.orderId desc";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                ProductDao productDao = new ProductDao(this.con);
                int pId = rs.getInt("productId");
                Product product = productDao.getSingleProduct(pId);
                order.setOrderId(rs.getInt("orderId"));
                order.setId(pId);
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setPrice(product.getPrice()*rs.getInt("quantity"));
                order.setQuantity(rs.getInt("quantity"));
                order.setCreatedAt(Timestamp.valueOf(rs.getString("createdAt")));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void cancelOrder(int id) {
        //boolean result = false;
        try {
            query = "delete from orders where orderId=?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            //result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        //return result;
    }
}
