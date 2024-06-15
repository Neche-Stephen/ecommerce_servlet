package org.ecommerce.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ecommerceDB";
    private static final String JDBC_USERNAME = "springstudent";
    private static final String JDBC_PASSWORD = "springstudent";
    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            System.out.println("Connected");
        }
        return connection;
    }


}
