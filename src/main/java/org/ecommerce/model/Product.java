package org.ecommerce.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String image;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
