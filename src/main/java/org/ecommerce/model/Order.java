package org.ecommerce.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order extends Product{

    private int orderId;
    private int userId;
    private int quantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
