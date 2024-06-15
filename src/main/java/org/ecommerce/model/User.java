package org.ecommerce.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private int id;
    private String fullname;
    private String password;
    private String email;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
