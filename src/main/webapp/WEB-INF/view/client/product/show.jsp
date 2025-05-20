<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Product Page</title>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />

    <!-- Custom CSS -->
    <%--    <link rel="stylesheet" href="/css/product/products.css" />--%>
    <%--    <link href="/client/css/style.css" rel="stylesheet">--%>

    <style>

        .carousel-inner {
            margin-top: 100px;
        }

        .card:hover {
            transform: scale(1.05);
        }

        .card {
            transition: transform 0.5s;
        }

        .btn {
            background-color: #ED4417!important;
            color: #fff !important;
            font-weight: bold !important;
            border: navajowhite !important;
        }

        .btn:hover {
            background-color: rgba(255, 105, 0, 0.6) !important;
            color: #fff !important;
        }

        .text-primary {
            color: #ff6900 !important;
        }

        /* Tạo header cố định và trong suốt */


        /* Tạo khoảng cách giữa nội dung và header */
        body {
            padding-top: 80px; /* Khoảng cách để tránh nội dung bị đè bởi header */
        }

        .slide {
            margin-top: 100px;
        }

    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<jsp:include page="../layout/header.jsp" />

<div class="container-fluid">
    <div id="carouselExampleIndicators" class="carousel slide">
        <div class="carousel-indicators">
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="0"
                    class="active"
                    aria-current="true"
                    aria-label="Slide 1"
            ></button>
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="1"
                    aria-label="Slide 2"
            ></button>
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="2"
                    aria-label="Slide 3"
            ></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img
                        src="/images/Banner/banner1.png"
                        class="d-block w-100 h-100 img-fluid"
                        alt="..."
                />
            </div>
            <div class="carousel-item">
                <img
                        src="/images/Banner/banner2.png"
                        class="d-block w-100 h-100 img-fluid"
                        alt="..."
                />
            </div>
            <div class="carousel-item">
                <img
                        src="/images/Banner/Banner3.png"
                        class="d-block w-100 h-100 img-fluid"
                        alt="..."
                />
            </div>
        </div>
        <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide="prev"
        >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide="next"
        >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <div class="row" >
        <!-- Filter Section -->
        <div class="col-md-2 filter-section" style="margin-left: 30px; margin-top: 30px;">
            <h5>Bộ lọc tìm kiếm</h5>
            <form id="filterForm">
                <!-- Category filter -->
                <div class="mb-3" style=";">
                    <label class="form-label">Loại:</label><br />
                    <c:forEach items="${categories}" var="cate">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="categories" value="${cate.name}" id="${cate.name}" ${not empty categoryList && categoryList.contains(cate.name) ? 'checked' : ''}/>
                            <label class="form-check-label" for="${cate.name}">${cate.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <hr />

                <!-- Color filter -->
                <div class="mb-3" style=";">
                    <label class="form-label">Màu sắc:</label><br />
                    <c:forEach items="${colors}" var="color">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="colors" value="${color.name}" id="${color.name}" ${not empty colorList && colorList.contains(color.name) ? 'checked' : ''}/>
                            <label class="form-check-label" for="${color.name}">${color.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <hr />

                <!-- Size filter -->
                <div class="mb-3" style=";">
                    <label class="form-label">Kích thước:</label><br />
                    <c:forEach items="${sizes}" var="size">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="sizes" value="${size.name}" id="${size.name}" ${not empty sizeList && sizeList.contains(size.name) ? 'checked' : ''} />
                            <label class="form-check-label" for="${size.name}">${size.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <hr />


                <div class="mb-3">
                    <label class="form-label">Giá tiền:</label><br />
                    <div class="price-input">
                        <select name="minPrice">
                            <option value="0" ${min == 0 ? 'selected' : ''}>0đ</option>
                            <option value="100000" ${min == 100000 ? 'selected' : ''}>100.000đ</option>
                            <option value="200000" ${min == 200000 ? 'selected' : ''}>200.000đ</option>
                            <option value="500000" ${min == 500000 ? 'selected' : ''}>500.000đ</option>
                            <%--                            <option value="1000001">Lớn hơn 1.000.000đ</option>--%>
                        </select>
                        <%--                        <span class="separator" style="{margin-top: 4px}"></span>--%>
                        <select name="maxPrice" style="margin-top: 3px">
                            <option value="100000" ${max == 100000 ? 'selected' : ''}>100.000đ</option>
                            <option value="200000" ${min == 200000 ? 'selected' : ''}>200.000đ</option>
                            <option value="500000" ${min == 500000 ? 'selected' : ''}>500.000đ</option>
                            <option value="1000000" ${min == 1000000 ? 'selected' : ''}>1.000.000đ</option>
                            <%--                            <option value="1000001" >Lớn hơn 1.000.000đ</option>--%>
                        </select>
                    </div>
                </div>

                <!-- Apply button -->
                <div class="mb-3">
                    <button type="button" class="btn btn-outline bg-orange" onclick="applyFilters()">Tìm</button>
                </div>
            </form>
        </div>

        <script>
            function applyFilters() {
                const form = document.getElementById('filterForm');

                let categories = [];
                let colors = [];
                let sizes = [];

                form.querySelectorAll('input[name="categories"]:checked').forEach((checkbox) => {
                    categories.push(checkbox.value);
                });

                form.querySelectorAll('input[name="colors"]:checked').forEach((checkbox) => {
                    colors.push(checkbox.value);
                });

                form.querySelectorAll('input[name="sizes"]:checked').forEach((checkbox) => {
                    sizes.push(checkbox.value);
                });

                const minPrice = form.querySelector('select[name="minPrice"]').value;
                const maxPrice = form.querySelector('select[name="maxPrice"]').value;

                let queryParams = new URLSearchParams();

                if (categories.length > 0) {
                    queryParams.append('categories', categories.join(','));
                }

                if (colors.length > 0) {
                    queryParams.append('colors', colors.join(','));
                }

                if (sizes.length > 0) {
                    queryParams.append('sizes', sizes.join(','));
                }

                if (minPrice && minPrice !== '0') {
                    queryParams.append('minPrice', minPrice);
                }
                if (maxPrice && maxPrice !== '100000') {
                    queryParams.append('maxPrice', maxPrice);
                }


                window.location.href = '/product/filter?' + queryParams.toString();
            }
        </script>

        <!-- Product Listing -->
        <div class="col-md" style="margin-top: 30px">
            <div class="row" style="margin-left: 30px">
                <!-- Product 1 -->
                <c:forEach items="${products}" var="product">
                    <!-- Mỗi cột chiếm 3 phần (4 sản phẩm trên 1 hàng) -->
                    <div class="col-md-3 mb-4">
                        <a href="/product/detail/${product.key.id}" class="card-link text-decoration-none">
                            <div class="card border-0 rounded-0 shadow">

                                <img src="/images/product/${product.key.name}/${product.key.productVariant.get(0).image}"
                                     class="card-img-top rounded-0" alt="...">
                                <div class="card-body mt-1 mb-1">
                                    <div class="row">
                                        <div class="col-12 gap-0">
                                            <h5 class="card-title text-black text-center">${product.key.name}</h5>
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
                                    </div>
                                    <div class="row align-items-center text-center g-0">
                                        <div class="text-black mt-2 mb-2">
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


                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
    <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation example" class="mt-4">

            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link text-primary ${currentPage eq 1 ? 'disabled' : ''}" href="/product?page=${currentPage-1}">Previous</a>
                </li>
                <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                    <li class="page-item">
                        <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link text-primary"
                           href="/product?page=${loop.index}">${loop.index}</a>
                    </li>
                </c:forEach>

                    <%--            <li class="page-item"><a class="page-link " href="#">1</a></li>--%>
                    <%--            <li class="page-item"><a class="page-link text-primary" href="#">2</a></li>--%>
                    <%--            <li class="page-item"><a class="page-link text-primary" href="#">3</a></li>--%>
                    <%--            <li class="page-item"><a class="page-link text-primary" href="#">...</a></li>--%>

                <li class="page-item">
                    <a class="page-link text-primary ${currentPage eq totalPages ? 'disabled' : ''}" href="/product?page=${currentPage+1}">Next</a>
                </li>
            </ul>
        </nav>
    </c:if>

</div>
<jsp:include page="../layout/footer.jsp" />


<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
