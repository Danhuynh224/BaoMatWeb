<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">

<link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
<%--- Navbar start -->--%>
<body>
<%--<jsp:include page="../layout/header.jsp" />--%>
<%--- Navbar start -->--%>

<jsp:include page="../layout/header.jsp" />
<link rel="stylesheet" href="/css/client/home.css" type="text/css">

<div class="container">


    <!-- Banner slide -->
    <div id="carouselExampleIndicators" class="carousel slide m-2" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3" aria-label="Slide 4"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="/images/assets/img/Black Bold Men's Fashion Landscape Banner.png"
                     class="d-block w-100 rounded-1" alt="...">

            </div>
            <div class="carousel-item">
                <img src="/images/assets/img/Cream Feminine Aesthetic Best Collection Banner.png"
                     class="d-block w-100 rounded-1" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/images/assets/img/Gray Black Minimalist Jeans Collection Banner Landscape.png"
                     class="d-block w-100 rounded-1" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/images/assets/img/Simple Modern Photo Collage Autumn Fashion Sale Banner.png"
                     class="d-block w-100 rounded-1" alt="...">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <!-- End Banner slide -->

    <!-- Text Header -->
    <div class="d-flex justify-content-center align-items-center">
        <div class="text-center w-50 m-5">
            <h3 class="text-primary">Enjoy Your Youth!</h3>
            <p class="text-body fs-5"> Chúng tôi hiểu rằng mỗi bộ trang phục không chỉ là những mảnh vải
                mà còn là sự thể hiện cá tính và phong cách riêng của bạn.
                Từ các thiết kế thời trang hiện đại, trẻ trung đến những mẫu mã thanh lịch,
                sang trọng, chúng tôi tự hào cung cấp những sản phẩm không chỉ đẹp mắt
                mà còn thoải mái và bền bỉ. Khám phá ngay bộ sưu tập của chúng tôi
                để tìm kiếm những trang phục hoàn hảo cho mọi dịp!</p>
        </div>
    </div>

    <div class="d-flex justify-content-between">
        <h4 class="text-primary"> Có thể bạn quan tâm </h4>
        <a href="/product" class="btn btn-light">Show All</a>
    </div>

    <!-- Product -->
    <div class="row row-cols-4 g-4 mt-2">
        <c:forEach items="${products}" var="product">
            <c:if test="${not empty product.key.productVariant}">
                <div class="col" style = "margin-right: 0; margin-left: 0;">
                    <a href="/product/detail/${product.key.id}" class="card-link text-decoration-none">
                        <div class="card border-0 rounded-0 shadow">

                            <img src="/images/product/${product.key.name}/${product.key.productVariant.get(0).image}"
                                 class="card-img-top rounded-0" alt="...">
                            <div class="card-body mt-1 mb-1">
                                <div class="row">
                                    <div class="col-10 gap-0">
                                        <h5 class="card-title text-primary text-primary">${product.key.name}</h5>
                                        <c:if test="${product.key.name.length() < 25}">
                                            <br/>
                                        </c:if>
                                        <p class="card-text">
                                            <c:if test="${product.value.countReview >= 1}">
                                                <c:forEach begin="${1}" end="${product.value.scoreProduct}" step="1">
                                                    <i class="bi bi-star-fill text-warning"></i>

                                                </c:forEach>
                                                <c:forEach begin="${product.value.scoreProduct+1}" end="${5}" step="1">
                                                    <i class="bi bi-star-fill"></i>

                                                </c:forEach>



                                                (${product.value.countReview})
                                            </c:if>
                                            <c:if test="${product.value.countReview == 0}">
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>
                                                <i class="bi bi-star-fill text-warning"></i>

                                                (0)
                                            </c:if>

<%--                                            ${product.productVariant.get(0).quantity}--%>

                                        </p>
                                    </div>
                                    <div class="col-2">
                                        <i class="bi bi-bookmark-plus fs-3"></i>
                                    </div>
                                </div>
                                <div class="row align-items-center text-center g-0">
                                    <div class="text-primary mt-2 mb-2">
                                        <h5><fmt:formatNumber value="${product.key.productVariant.get(0).price}" type="number"/> VNĐ</h5>
                                    </div>
                                    <div>

                                        <c:if test="${product.key.productVariant.get(0).quantity >= 1}">
                                            <form:form action="/cart/add-product-item-in-cart/${product.key.productVariant[0].id}" method="post" modelAttribute="products">
                                                <%--                                                    <form:hidden path="quantity" value="1"/>--%>
                                                <button type="submit" class="btn w-100 p-3 rounded-0">Thêm vào giỏ hàng</button>
                                            </form:form>
                                        </c:if>
                                        <c:if test="${product.key.productVariant.get(0).quantity < 1}">
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

    <!-- Pagination -->


</div>
<jsp:include page="../layout/footer.jsp" />



</body>
</html>