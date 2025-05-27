<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">

    <!-- Bootstrap & Icon CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon/fonts/remixicon.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

    <!-- Custom Styles -->
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>" />
<%--    <link rel="stylesheet" href="/css/client/custom.css">--%>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary bg-primary py-1 px-4">
        <a href="/" class="navbar-brand d-flex align-items-center">
            <img class="rounded img-fluid" style="width: 90px; height: 70px" src="/images/assets/img/logo.png" alt="Logo">
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
            <ul class="navbar-nav align-items-center">
                <li class="nav-item"><a href="/" class="nav-link active px-3 py-2" style="font-size: 14px">NEW ARRIVALS</a></li>
                <li class="nav-item"><a href="/product" class="nav-link px-3 py-2" style="font-size: 14px">CLOTHING</a></li>
                <li class="nav-item"><a href="/blog" class="nav-link px-3 py-2" style="font-size: 14px">BLOG</a></li>
                <li class="nav-item"><a href="/about" class="nav-link px-3 py-2" style="font-size: 14px">ABOUT</a></li>
            </ul>

            <form class="d-flex position-relative mb-0 me-3" role="search" action="/product" method="get">
                <input class="form-control me-2" type="search" id="itemInput" placeholder="Search" name="name" autocomplete="off" value="${sessionScope.nameSearch}">
                <div id="itemList" class="dropdown-menu w-80"></div>
                <button class="btn btn-outline bg-orange" type="submit"><i class="ri-search-line"></i></button>
            </form>

            <div class="d-flex align-items-center ms-auto mt-2">
                <c:if test="${not empty pageContext.request.userPrincipal && !sessionScope.isAdmin}">
                    <form action="/cart" method="get" class="mb-0 me-3">
                        <button class="position-relative me-4 my-auto btn-custom">
                            <i class="fa fa-shopping-bag" style="font-size: 1.5em;"></i>
                            <span class="position-absolute d-flex align-items-center justify-content-center count-badge">
                                    ${sessionScope.sum}
                            </span>
                        </button>
                    </form>
                    <div class="dropdown">
                        <a href="#" class="dropdown" id="dropdownMenuLink" data-bs-toggle="dropdown">
                            <i class="fas fa-user btn-custom" style="font-size: 1.5em!important;"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuLink">
                            <li class="dropdown-item text-center"><c:out value="${sessionScope.email}" /></li>
                            <li><a class="dropdown-item" href="/account">Quản lý tài khoản</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="/logout" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <button class="dropdown-item">Đăng xuất</button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </c:if>
                <c:if test="${empty pageContext.request.userPrincipal}">
                    <a href="/login" class="btn btn-light fw-semibold me-3">Đăng nhập</a>
                </c:if>
                <c:if test="${sessionScope.isAdmin}">
                    <a href="/admin" class="btn btn-light fw-semibold">Quản lý</a>
                </c:if>
            </div>
        </div>
    </nav>
</header>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="../js/header.js"></script>
</body>
</html>
