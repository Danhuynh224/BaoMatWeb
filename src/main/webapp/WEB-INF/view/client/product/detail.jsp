<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: dungnguyen
  Date: 15/10/24
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.sale.project.entity.FeedBackReview" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

    <title>Chi tiết sản phẩm</title>

    <style>
        /* CSS ẩn input ban đầu */
        .input-container-feedback {
            display: none;
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<jsp:include page="../layout/header.jsp" />
<link rel="stylesheet" href="/css/product/detail.css" type="text/css">

<div class="container" >
    <div class="row">
        <div class="col-md-6">
            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <c:forEach var="image" items="${images}">
                        <div class="carousel-item ${image eq item.image ? 'active' : ''}">
                            <img class="d-block w-100" src="/images/product/${item.product.name}/${image}" alt="${item.product.name}">
                        </div>
                    </c:forEach>

                    <%--          <div class="carousel-item">--%>
                    <%--            <img class="d-block w-100" src="Image/hoodie.png" alt="Hoodie Image 2">--%>
                    <%--          </div>--%>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                    <%--          <span class="carousel-control-prev-icon" aria-hidden="true"></span>--%>
                    <i class="bi bi-caret-left"></i>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                    <i class="bi bi-caret-right"></i>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="col-md-6 d-flex flex-column justify-content-center">
            <div class="infor">
                <h2 class="fw-bold">${item.product.name}</h2>
                <div class="price mb-2"> <!-- New price section -->
                    <strong style="font-size: 1.5rem; color: red;">Giá:
                        <fmt:formatNumber value="${item.price}" type="number"/>
                        VND</strong>
                </div>
                <p><strong>Mô tả:</strong> ${item.product.description}.</p>
                <div class="rating text-warning">
                    <c:if test="${starReview.countReview >= 1}">
                        <c:forEach begin="${1}" end="${starReview.scoreProduct}" step="1">
                            <span class="fa fa-star checked"></span>


                        </c:forEach>

                        <c:forEach begin="${starReview.scoreProduct+1}" end="${5}" step="1">

                            <span class="fa fa-star" style="color: black;"></span>


                        </c:forEach>
                        (${starReview.countReview})
                    </c:if>


                    <c:if test="${starReview.countReview < 1}">
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        (0)

                    </c:if>

                </div>
                <div class="my-3">
                    <strong>Kích thước:</strong>
                    <div>
                        <c:forEach items="${sizes}" var="size">
                            <a href="javascript:void(0);"
                               class="btn btn-outline-danger btn-sm me-1 ${item.size.name eq size.name ? 'disabled' : ''}"
                               onclick="updateSelection('size', '${size.name}')">
                                    ${size.name}
                            </a>
                        </c:forEach>
                    </div>
                </div>

                <div class="my-3">
                    <strong>Màu sắc:</strong>
                    <div>
                        <c:forEach items="${colors}" var="color">
                            <a href="javascript:void(0);"
                               class="  btn-sm me-1 }"
                               onclick="updateSelection('color',
                                       '${color.name}')" style=" display: inline-block; padding: 10px; width: 30px; height: 30px;
                                    background-color: ${color.description}; ${item.color.name eq color.name ? 'border: white solid 2px;box-shadow: 0 0 0 1px #000; ' : ''}
                                    border-radius: 50%">
                                    <%--                                ${color.name}--%>
                                <div ></div>
                            </a>
                        </c:forEach>
                    </div>
                </div>

                <script>
                    // Biến để lưu giá trị đã chọn của size và color
                    let selectedSize = '${param.size != null ? param.size : ''}';
                    let selectedColor = '${param.color != null ? param.color : ''}';

                    function updateSelection(type, value) {
                        if (type === 'size') {
                            selectedSize = value;
                            selectedColor = null;
                        } else if (type === 'color') {
                            selectedColor = value;
                        }

                        sendRequest();
                    }

                    function sendRequest() {
                        var productId = '${item.product.id}';

                        var url = '/product/detail/' + productId;
                        var params = [];

                        if (selectedSize) {
                            params.push('size=' + selectedSize);
                        }

                        if (selectedColor) {
                            params.push('color=' + selectedColor);
                        }

                        if (params.length > 0) {
                            url += '?' + params.join('&');
                        }

                        window.location.href = url;
                    }
                </script>

                <div class="d-flex align-items-center  my-3">
                    <label class="mb-0 me-2"><strong>Số lượng:</strong></label>
                    <input id="quantityInput" type="number" class="form-control me-2 text-center" value="1" min="1" max="${item.quantity}" style="width: 80px;" name="quantity">
                    <span>Còn lại ${item.quantity} sản phẩm</span>
                </div>

                <div class="mt-1">
                    <form:form action="/cart/add-product-item-in-cart/${item.id}" method="post">
<%--                        <input type="hidden" id="quantityInput" name="quantity" value="1" />--%>

                        <button type="submit" class="btn btn-danger btn-sm w-100 ${item.quantity eq 0 ? 'disabled' : ''}"
                                style="background-color: #ED4417; height: 50px; border: 1px solid black; border-radius: 10px">
                            <strong>Thêm vào giỏ hàng</strong>
                        </button>
                    </form:form>
                </div>

            </div>
        </div>
        <div class="col-lg-12">
            <nav>
                <div class="nav nav-tabs mb-3">
                    <button class="nav-link active border-white border-bottom-0" type="button" role="tab"
                            id="nav-about-tab" data-bs-toggle="tab" data-bs-target="#nav-about"
                            aria-controls="nav-about" aria-selected="true">Description</button>
                    <button class="nav-link border-white border-bottom-0" type="button" role="tab"
                            id="nav-mission-tab" data-bs-toggle="tab" data-bs-target="#nav-mission"
                            aria-controls="nav-mission" aria-selected="false">Reviews</button>
                </div>
            </nav>
            <div class="tab-content mb-5">
                <div class="tab-pane active" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">
                    <p>${item.product.description}</p>
                    <%--                  <p>Sabertooth peacock flounder; chain pickerel hatchetfish, pencilfish snailfish filefish Antarctic--%>
                    <%--                      icefish goldeye aholehole trumpetfish pilot fish airbreathing catfish, electric ray sweeper.</p>--%>
                    <div class="px-2">
                        <div class="row g-4">
                            <div class="col-6">
                                <div class="row bg-light align-items-center text-center justify-content-center py-2">
                                    <div class="col-6">
                                        <p class="mb-0">Name</p>
                                    </div>

                                    <div class="col-6">
                                        <p class="mb-0">
                                            <strong>${item.product.name}</strong>
                                        </p>
                                    </div>
                                </div>
                                <div class="row bg-light align-items-center text-center justify-content-center py-2">
                                    <div class="col-6">
                                        <p class="mb-0">Size</p>
                                    </div>
                                    <div class="col-6">
                                        <p class="mb-0">
                                            <strong>${item.size.name} - ${item.size.description}</strong>
                                        </p>
                                    </div>
                                </div>
                                <div class="row text-center align-items-center justify-content-center py-2">
                                    <div class="col-6">
                                        <p class="mb-0">Color</p>
                                    </div>
                                    <div class="col-6">
                                        <p class="mb-0">
                                            <strong>
                                                ${item.color.name} - ${item.color.description}
                                            </strong>
                                        </p>
                                    </div>
                                </div>
                                <div class="row bg-light text-center align-items-center justify-content-center py-2">
                                    <div class="col-6">
                                        <p class="mb-0">Quality</p>
                                    </div>
                                    <div class="col-6">
                                        <p class="mb-0">
                                            <strong>
                                                ${item.quantity}
                                            </strong>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="nav-mission" role="tabpanel" aria-labelledby="nav-mission-tab">
                    <c:forEach var="detail" items="${item.orderDetails}">
                        <c:if test="${not empty detail.review}">
                            <div class="d-flex row" >
                                <div class="col-1">
                                    <img src="/images/avatar/${detail.order.user.image != null ? detail.order.user.image : 'default.png'}" class="img-fluid rounded-circle p-3" style="width: 100px; height: 100px;" alt="">
                                </div>
                                <div class="col">
                                    <h5 style="font-weight: bold">${detail.order.user.name}</h5>
                                    <p class="mb-2" style="font-size: 12px; font-weight: lighter; color: teal">${detail.review.dateReview.toString()}</p>
                                    <div class="d-flex mb-3">
                                        <c:forEach begin="1" end="${detail.review.star}">
                                            <%--                                              <i class="fa fa-star text-secondary"></i>--%>
                                            <span class="fa fa-star checked" style="color: #bdbd00"></span>

                                        </c:forEach>
                                        <c:forEach begin="${detail.review.star + 1}" end="5">
                                            <i class="fa fa-star"></i>
                                        </c:forEach>
                                    </div>

                                    <p><i><span style="color: teal">Chi tiết đánh giá: </span>${detail.review.description}</i></p>
                                </div>
                                <hr>
                                <div class="row" style="border-bottom: 2px solid teal; margin-bottom: 20px">
                                    <c:if test="${sessionScope.isAdmin && empty detail.review.feedBackReview}">

                                    <div style="margin-bottom: 20px">
                                        <!-- Nút "Trả lời" kèm theo hàm toggleInput với id duy nhất -->
                                        <button class="btn btn-outline-primary btn-sm" onclick="toggleInput('inputContainer-${detail.id}')">Trả lời</button>
                                    </div>


                                    <!-- Input cho câu trả lời, ẩn ban đầu và có id duy nhất -->
                                    <div class="mt-2" id="inputContainer-${detail.id}" style="display: none; margin-bottom: 20px">
                                        <div class="input-group">
                                            <%
                                                FeedBackReview feedBackReview = new FeedBackReview();
                                            %>

                                            <form:form action="/feedBack/create" modelAttribute="newFeedBackReview" method="post">

                                                <div style="display: none">
                                                    <label>
                                                        <input type="text" value="${detail.review.id}" name = "idReview">
                                                    </label>
                                                    <label>
                                                        <input type="text" value="${item.product.id}" name="idProduct">
                                                    </label>
                                                </div>
                                                <label>
                                                    <input:input type="text" class="form-control" placeholder="Nhập phản hồi" name="feedBackReview" path="description"/>
                                                </label>
                                                <button class="btn btn-primary">Gửi</button>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                                </c:if>
                                <script>
                                    // Hàm hiển thị/ẩn input dựa vào id duy nhất
                                    function toggleInput(id) {
                                        const inputContainer = document.getElementById(id);
                                        if (inputContainer.style.display === "none" || inputContainer.style.display === "") {
                                            inputContainer.style.display = "block"; // Hiển thị phần input
                                        } else {
                                            inputContainer.style.display = "none"; // Ẩn phần input
                                        }
                                    }

                                    // Hàm xử lý khi bấm nút "Gửi" (có thể thay đổi theo nhu cầu)

                                </script>
                                <div>

                                    <c:if test="${not empty detail.review.feedBackReview}">
                                        <div class="row">

                                            <div class="d-flex justify-content-between">
                                                <h5 style="color: teal; font-weight: bold">Chủ Cửa hàng</h5>

                                            </div>
                                            <p class="mb-2" style="font-size: 12px; color: teal; font-weight: lighter">${detail.review.feedBackReview.date.toString()}</p>
                                            <p><i><span style="color: teal">Chi tiết đánh giá: </span>${detail.review.feedBackReview.description}</i></p>
                                        </div>

                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty recommenderProducts}">
        <h2>Những sản phẩm tương tự</h2>
        <div class="row row-cols-4 g-4 mt-2">
            <c:forEach items="${recommenderProducts}" var="product">
                <c:if test="${not empty product.productVariant}">
                    <div class="col" style = "margin-right: 0; margin-left: 0;">
                        <a href="/product/detail/${product.id}" class="card-link text-decoration-none">
                            <div class="card border-0 rounded-0 shadow">

                                <img src="/images/product/${product.name}/${product.productVariant.get(0).image}"
                                     class="card-img-top rounded-0" alt="...">
                                <div class="card-body mt-1 mb-1">
                                    <div class="row">
                                        <div class="col-10 gap-0">
                                            <h5 class="card-title text-primary text-primary">${product.name}</h5>
                                            <c:if test="${product.name.length() < 24}">
                                                <br/>
                                            </c:if>
                                            <p class="card-text">
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>
                                                    <%--                                            ${product.productVariant.get(0).quantity}--%>
                                                (123)
                                            </p>
                                        </div>
                                        <div class="col-2">
                                            <i class="bi bi-bookmark-plus fs-3"></i>
                                        </div>
                                    </div>
                                    <div class="row align-items-center text-center g-0">
                                        <div class="text-primary mt-2 mb-2">
                                            <h5><fmt:formatNumber value="${product.productVariant.get(0).price}" type="number"/> VNĐ</h5>
                                        </div>
                                        <div>

                                            <c:if test="${product.productVariant.get(0).quantity >= 1}">
                                                <form:form action="/cart/add-product-item-in-cart/${product.productVariant.get(0).id}" method="post" modelAttribute="item">
                                                    <%--                                                    <form:hidden path="quantity" value="1"/>--%>
                                                    <button type="submit" class="btn w-100 p-3 rounded-0">Thêm vào giỏ hàng</button>
                                                </form:form>
                                            </c:if>
                                            <c:if test="${product.productVariant.get(0).quantity < 1}">
                                                <a href="#" class="btn w-100 p-3 rounded-0" >Liên hệ</a>

                                            </c:if>

                                                <%--                                        <a href="/cart/add-product-item-in-cart/${product.productVariant.get(0).id}" class="btn w-100 p-3 rounded-0">ADD TO CART</a>--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:if>

            </c:forEach>



        </div>
    </c:if>
</div>
<jsp:include page="../layout/footer.jsp" />
</body>

</html>
