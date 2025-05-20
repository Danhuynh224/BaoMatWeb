<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Giỏ hàng</title>
  <script src="https://kit.fontawesome.com/7a738de67b.js" crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="/css/client/cart/style.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<!--Body-->
<jsp:include page="../layout/header.jsp" />

<main>
<div class="container mt-5">
<h2 class="text-center" style="color: #000;">Giỏ hàng của bạn</h2>
<p class="text-center" style="color: #000;">Có ${items.size()} sản phẩm trong giỏ hàng</p>

<div class="card">
<div class="card-body">
<table class="table table-bordered align-middle text-center">
<thead>
<tr style="color: #000;">
  <th>Thông tin sản phẩm</th>
  <th>Số lượng</th>
  <th>Đơn giá</th>
  <th>Thành tiền</th>
  <th></th>
</tr>
</thead>
<tbody>
<c:set var="errorPay">${true}</c:set>
<c:forEach items="#{items}" var="item">
  <tr>
  <td>
  <a href="/product/detail/${item.productVariant.product.id}">
  <div class="d-flex align-items-center">
  <img src="/images/product/${item.productVariant.product.name}/${item.productVariant.image}" class="img-fluid" style="width: 80px; height: auto;" alt="Hình ảnh sản phẩm">
  <div class="ms-3 text-start" style="color: #000;">
  <p class="mb-1">${item.productVariant.product.name}</p>
  <small style="color: #000;">Màu: ${item.productVariant.color.name} / Kích cỡ: ${item.productVariant.size.name}</small>
  </div>
  </div>
  </a>
  </td>
  <td>
  <c:set var="checkQuantity">${item.quantity > item.productVariant.quantity ? true : false}</c:set>
  <div class="input-group mx-auto" style="width: 140px;">
  <c:if test="${item.productVariant.quantity > 0}">
    <a class="btn btn-outline-secondary" href="/cart/updateDown/${item.id}">-</a>
  </c:if>
  <input type="number" class="form-control text-center" value="${checkQuantity ? item.productVariant.quantity : item.quantity}" min="0" max="${item.productVariant.quantity}" readonly>
  <c:if test="${item.quantity < item.productVariant.quantity}">
    <a class="btn btn-outline-secondary" href="/cart/updateUp/${item.id}">+</a>
  </c:if>
  </div>
  </td>
    <td style="color: #000;">
      <c:if test="${item.productVariant.quantity != 0}">
        <fmt:formatNumber type="number" value="${item.productVariant.price}"/> đ
      </c:if>
      <c:if test="${item.productVariant.quantity == 0}">
        <c:set var="errorPay">${false}</c:set>
        Liên hệ
      </c:if>
    </td>
    <td style="color: #000;">
      <fmt:formatNumber type="number" value="${item.productVariant.price * (checkQuantity ? item.productVariant.quantity : item.quantity)}"/> đ
    </td>
    <td style="display: flex; justify-content: center; align-items: center; height: 100px;">
      <form method="get" action="/cart/delete-item-in-cart/${item.id}">
        <button class="button-delete-item" type="submit">
          <svg viewBox="0 0 448 512" class="svgIcon-delete"><path d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"></path></svg>
        </button>
      </form>
    </td>
  </tr>
</c:forEach>
</tbody>
</table>

  <div class="d-flex justify-content-between align-items-center mt-3">
    <c:if test="${sessionScope.sum > 0 && errorPay}">
      <h4 class="mb-0" style="color: #000;">Tổng tiền: <span style="color: #F15F25;"> <fmt:formatNumber type="number" value="${totalPrice}"/>  VND</span></h4>
    </c:if>
    <c:if test="${sessionScope.sum > 0 && !errorPay}">
      <h4 class="mb-0" style="color: #000;">Xoá sản phẩm không còn tồn tại</h4>
    </c:if>
    <a href="/pay" class="btn" style="background-color: #F15F25; color: white; ${sessionScope.sum > 0 && errorPay ? '' : 'pointer-events: none; opacity: 0.5; cursor: not-allowed;'}">Thanh toán</a>
  </div>
</div>
</div>

  <hr/>

  <c:if test="${not empty recommenderProducts}">
  <h2>Những sản phẩm tương tự</h2>
  <div class="row row-cols-2 row-cols-md-4 g-4 mt-2">
    <c:forEach items="${recommenderProducts}" var="product">
    <c:if test="${not empty product.productVariant}">
    <div class="col">
      <a href="/product/detail/${product.id}" class="card-link text-decoration-none">
        <div class="card border-0 rounded-0 shadow">
          <img src="/images/product/${product.name}/${product.productVariant.get(0).image}" class="card-img-top rounded-0" alt="...">
          <div class="card-body">
            <h5 class="card-title text-primary">${product.name}</h5>

            <div class="text-primary">
              <h5><fmt:formatNumber value="${product.productVariant.get(0).price}" type="number"/> VNĐ</h5>
            </div>
            <c:if test="${product.productVariant.get(0).quantity >= 1}">
              <form:form action="/cart/add-product-item-in-cart/${product.productVariant[0].id}" method="post" modelAttribute="recommenderProducts">
                <%--                                                    <form:hidden path="quantity" value="1"/>--%>
                <button type="submit" class="btn w-100 p-3 rounded-0">Thêm vào giỏ hàng</button>
              </form:form>
            </c:if>
            <c:if test="${product.productVariant.get(0).quantity < 1}">
              <a href="#" class="btn w-100 p-3 rounded-0">Liên hệ</a>
            </c:if>
          </div>
        </div>
      </a>
    </div>
    </c:if>
    </c:forEach>
  </div>
  </c:if>

</div>
</main>
<jsp:include page="../layout/footer.jsp" />
</body>
</html>