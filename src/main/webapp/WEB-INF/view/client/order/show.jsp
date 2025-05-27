<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dungnguyen
  Date: 9/11/24
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Order Detail</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.min.css">
  <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<jsp:include page="../layout/header.jsp"/>
<section class="gradient-custom">
  <div class="container py-5">
    <div class="row d-flex justify-content-center align-items-center">
      <div class="col-lg-10 col-xl-8">
        <div class="card" style="border-radius: 10px;">
          <div class="card-header px-4 py-5 d-flex justify-content-between align-items-center">
            <div class="col-md-1">
              <a href="/account" class="text-decoration-none">
                <i class="btn bi bi-arrow-left fs-4"></i>
              </a>
            </div>
            <h5 class="text-muted mb-0">Thanks for your Order, <span style="color: #ED4417;">${order.user.name}</span>!</h5>
          </div>
          <div class="card-body p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
              <p class="lead fw-semibold mb-0" style="color: #ED4417;">Receipt</p>
              <p class="small text-muted mb-0">Id : #${order.id.substring(0,5)}</p>
            </div>

            <c:forEach var="orderDetail" items="${order.details}">
              <div class="card shadow-0 border mb-4">
                <div class="card-body">
                  <div class="row">
                    <div class="col-md-2">
                      <img src="/images/product/${orderDetail.productVariant.product.name}/${orderDetail.productVariant.image}"
                           class="img-fluid" alt="${orderDetail.productVariant.product.name}">
                    </div>
                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                      <p class="text-muted mb-0">${orderDetail.productVariant.product.name}</p>
                    </div>
                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                      <p class="text-muted mb-0 small">${orderDetail.productVariant.color.name}</p>
                    </div>
                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                      <p class="text-muted mb-0 small">${orderDetail.productVariant.size.name}</p>
                    </div>
                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                      <p class="text-muted mb-0 small">${orderDetail.quantity}</p>
                    </div>
                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                      <p class="text-muted mb-0 small">${orderDetail.price}</p>
                    </div>
                  </div>
                  <hr class="mb-4" style="background-color: #e0e0e0; opacity: 1;">
                  <div class="row d-flex align-items-center justify-content-between">
                    <p class="text-muted mb-0 small col">Track Order</p>

                    <!-- Button container using flexbox -->
                    <div class="d-flex col justify-content-end">
                      <c:if test="${empty orderDetail.review && order.status.toString() == 'COMPLETED'}">
                        <a href="/order/detail/review/${orderDetail.id}" class="btn btn-danger">Review</a>
                      </c:if>
                    </div>
                  </div>
                </div>
              </div>

            </c:forEach>




            <div class="d-flex justify-content-between pt-2">
              <div class="d-flex flex-column">
                <p class="fw-bold mb-0">Order Details</p>
                <p class="text-muted mb-0 mt-2">Note : ${order.note}</p>
                <p class="text-muted mb-0">Invoice Date : ${order.date}</p>
                <c:if test="${not empty order.voucher}">
                  <p class="text-muted mb-0">Recepits Voucher : ${order.voucher.code}</p>
                </c:if>
              </div>
              <div class="d-flex flex-column text-end">
                <p class="text-muted mb-0"><span class="fw-bold me-4">Total</span> ${order.total-30000} VNĐ</p>
                <p class="text-muted mb-0 mt-2"><span class="fw-bold me-4">Delivery Charges</span> 30.000 VNĐ</p>
                <c:if test="${not empty order.voucher}">
                  <p class="text-muted mb-0"><span class="fw-bold me-4">Discount</span> ${order.total * (1/(1-order.voucher.discountValue/100.0) -1)}</p>
                </c:if>
              </div>
            </div>

          </div>
          <div class="card-footer border-0 px-4 py-5"
               style="background-color: rgba(237,68,23,0.86); border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">

            <h5 class="d-flex align-items-center justify-content-end text-white text-uppercase mb-0">Total
              paid: <span class="h2 mb-0 ms-2">${order.total} VNĐ</span></h5>


          </div>
          <c:if test="${order.status.toString() == 'PROCESSING' || order.status.toString() == 'SPACED'}">
            <a href="/order/cancel/${order.id}" class = "btn btn-danger">Huỷ Đơn hàng </a>
          </c:if>


        </div>
        <c:if test="${order.status.toString() == 'PAYMENT_FAILED'}">
          <form action="/order/returnPayment/${order.id}" method="post">
            <button class="btn btn-danger" type="submit">Thanh Toán lại</button>
            <div>
              <input
                      type="hidden"
                      name="${_csrf.parameterName}"
                      value="${_csrf.token}"
              />
            </div>
          </form>
        </c:if>



      </div>
    </div>
  </div>
</section>
<jsp:include page="../layout/footer.jsp"/>
</body>
</html>
