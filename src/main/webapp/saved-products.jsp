<%@ page import="org.ecommerce.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.ecommerce.connection.DatabaseConnection" %>
<%@ page import="org.ecommerce.model.User" %>
<%@ page import="org.ecommerce.dao.ProductDao" %>
<%@ page import="org.ecommerce.dao.SavedProductDao" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        SavedProductDao spd = new SavedProductDao(DatabaseConnection.getConnection());
        List<Product> savedProducts = spd.getSavedProducts(auth.getId());
        request.setAttribute("savedProducts", savedProducts);
    } else {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Saved Products</title>
    <%@ include file="includes/head.jsp"%>
</head>
<body>
<%@ include file="includes/navbar.jsp"%>
<div class="container">
    <div class="card-header my-3">Saved Products</div>
    <div class="row">
        <%
            List<Product> savedProducts = (List<Product>) request.getAttribute("savedProducts");
            if (savedProducts != null && !savedProducts.isEmpty()) {
                for (Product p : savedProducts) {
        %>
        <div class="col-md-3 my-3">
            <div class="card w-100">
                <img class="card-img-top" src="<%=p.getImage() %>" alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title"><%=p.getName() %></h5>
                    <h6 class="price">Price: $<%=p.getPrice() %></h6>
                    <h6 class="category">Category: <%=p.getCategory() %></h6>
                    <div class="mt-3 d-flex justify-content-between">
                        <a class="btn btn-dark" href="add-to-cart?id=<%=p.getId()%>">Add to Cart</a>
                        <a class="btn btn-primary" href="order-now?quantity=1&id=<%=p.getId()%>">Buy Now</a>
                        <form action="saved-products" method="post" style="display:inline;">
                            <input type="hidden" name="user_id" value="<%= auth.getId() %>">
                            <input type="hidden" name="product_id" value="<%= p.getId() %>">
                            <input type="hidden" name="source_page" value="<%= request.getRequestURI() %>">
                            <button type="submit" class="btn btn-secondary">Unsave</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            } else {
                out.println("You have no saved products.");
            }
        %>
    </div>
</div>
<%@ include file="includes/footer.jsp"%>
</body>
</html>
