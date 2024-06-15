<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="org.ecommerce.dao.ProductDao" %>
<%@ page import="org.ecommerce.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.ecommerce.connection.DatabaseConnection" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.ecommerce.model.User" %>
<%@ page import="java.util.HashMap" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth == null || !auth.getEmail().endsWith("@admin.com")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Products</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>All Products</h2>
    <div class="mb-3">
        <button id="toggleView" class="btn btn-secondary">Toggle View</button>
        <a href="addProduct.jsp" class="btn btn-success">Add New Product</a>
    </div>
    <div id="productList">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Category</th>
                <th>Price</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());
                List<Product> productList = productDao.getAllProducts();
//                System.out.println("from view product jsp" + productList);
                for (Product product : productList) {
            %>
            <tr>
                <td><%= product.getId() %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getDescription() %></td>
                <td><%= product.getCategory() %></td>
                <td><%= product.getPrice() %></td>
                <td><img src="<%= product.getImage() %>" width="50"></td>
<%--                <td><img src="database-images/ladis-bag.jpg" width="50"></td>--%>

                <td>
                    <a href="UpdateProductServlet?id=<%= product.getId() %>" class="btn btn-warning btn-sm">Update</a>
                    <a href="DeleteProductServlet?id=<%= product.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <div id="categoryList" style="display: none;">
        <%
            Map<String, List<Product>> categorizedProducts = new HashMap<>();
            for (Product product : productList) {
                categorizedProducts.computeIfAbsent(product.getCategory(), k -> new java.util.ArrayList<>()).add(product);
            }
        %>
        <%
            for (Map.Entry<String, List<Product>> entry : categorizedProducts.entrySet()) {
        %>
        <h3><%= entry.getKey() %></h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Product product : entry.getValue()) {
            %>
            <tr>
                <td><%= product.getId() %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getDescription() %></td>
                <td><%= product.getPrice() %></td>
                <td><img src="<%= product.getImage() %>" width="50"></td>
                <td>
                    <a href="UpdateProductServlet?id=<%= product.getId() %>" class="btn btn-warning btn-sm">Update</a>
                    <a href="DeleteProductServlet?id=<%= product.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <%
            }
        %>
    </div>
</div>

<script>
    document.getElementById('toggleView').addEventListener('click', function () {
        var productList = document.getElementById('productList');
        var categoryList = document.getElementById('categoryList');
        if (productList.style.display === 'none') {
            productList.style.display = '';
            categoryList.style.display = 'none';
        } else {
            productList.style.display = 'none';
            categoryList.style.display = '';
        }
    });
</script>
</body>
</html>
