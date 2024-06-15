<%@ page import="org.ecommerce.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.ecommerce.connection.DatabaseConnection" %>
<%@ page import="org.ecommerce.model.User" %>
<%@ page import="org.ecommerce.dao.ProductDao" %>
<%@ page import="org.ecommerce.model.Cart" %>
<%@ page import="org.ecommerce.dao.SavedProductDao" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("person", auth);
    }
    SavedProductDao spd = new SavedProductDao(DatabaseConnection.getConnection());
    ProductDao pd = new ProductDao(DatabaseConnection.getConnection());
    List<Product> products = pd.getAllProducts();
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>My title</title>
    <%@ include file="includes/head.jsp"%>
</head>
<body>
<%@include file="includes/navbar.jsp"%>
<div class="container">
    <div class="card-header my-3">All Products</div>
    <div class="row">
        <%
            if (!products.isEmpty()) {
                for (Product p : products) {
                    boolean isSaved = auth != null && spd.isProductSaved(auth.getId(), p.getId());
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
                    </div>
                    <div class="mt-3 d-flex justify-content-between">
                        <form action="saved-products" method="post" style="display:inline;">
                            <input type="hidden" name="user_id" value="<%= auth != null ? auth.getId() : 0 %>">
                            <input type="hidden" name="product_id" value="<%= p.getId() %>">
                            <button type="submit" class="btn btn-secondary">
                                <%= isSaved ? "Unsave" : "Save" %>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            } else {
                out.println("There are no products");
            }
        %>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>



<%--<%@ page import="org.ecommerce.model.Product" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="java.util.ArrayList" %>--%>
<%--<%@ page import="org.ecommerce.connection.DatabaseConnection" %>--%>
<%--<%@ page import="org.ecommerce.model.User" %>--%>
<%--<%@ page import="org.ecommerce.dao.ProductDao" %>--%>
<%--<%@ page import="org.ecommerce.model.Cart" %>--%>
<%--<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>--%>

<%--<%--%>
<%--    User auth = (User) request.getSession().getAttribute("auth");--%>
<%--    if (auth != null) {--%>
<%--        request.setAttribute("person", auth);--%>
<%--    }--%>
<%--    ProductDao pd = new ProductDao(DatabaseConnection.getConnection());--%>
<%--    List<Product> products = pd.getAllProducts();--%>
<%--    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");--%>
<%--    if (cart_list != null) {--%>
<%--        request.setAttribute("cart_list", cart_list);--%>
<%--    }--%>
<%--%>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--    <head>--%>
<%--        <title>My title</title>--%>
<%--        <%@ include file="includes/head.jsp"%>--%>
<%--    </head>--%>
<%--    <body>--%>
<%--    <%@include file="includes/navbar.jsp"%>--%>
<%--&lt;%&ndash;    <% out.println(DatabaseConnection.getConnection()); %>&ndash;%&gt;--%>
<%--    <div class="container">--%>
<%--&lt;%&ndash;        <img src="female-shoes.jpg" >&ndash;%&gt;--%>
<%--        <div class="card-header my-3">All Products</div>--%>
<%--        <div class="row">--%>
<%--            <%--%>
<%--                if (!products.isEmpty()) {--%>
<%--                    for (Product p : products) {--%>
<%--            %>--%>
<%--            <div class="col-md-3 my-3">--%>
<%--                <div class="card w-100">--%>
<%--                    <img class="card-img-top" src="<%=p.getImage() %>"--%>
<%--                         alt="Card image cap">--%>
<%--&lt;%&ndash;                    <img class="card-img-top" src="female-shoes.jpg>"&ndash;%&gt;--%>
<%--&lt;%&ndash;                         alt="Card image cap">&ndash;%&gt;--%>
<%--                    <div class="card-body">--%>
<%--                        <h5 class="card-title"><%=p.getName() %></h5>--%>
<%--                        <h6 class="price">Price: $<%=p.getPrice() %></h6>--%>
<%--                        <h6 class="category">Category: <%=p.getCategory() %></h6>--%>
<%--                        <div class="mt-3 d-flex justify-content-between">--%>
<%--                            <a class="btn btn-dark" href="add-to-cart?id=<%=p.getId()%>">Add to Cart</a> <a--%>
<%--                                class="btn btn-primary" href="order-now?quantity=1&id=<%=p.getId()%>">Buy Now</a>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <%--%>
<%--                    }--%>
<%--                } else {--%>
<%--                    out.println("There is no proucts");--%>
<%--                }--%>
<%--            %>--%>

<%--        </div>--%>
<%--    </div>--%>

<%--    <%@include file="includes/footer.jsp"%>--%>
<%--    </body>--%>
<%--</html>--%>