<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="css/register.css">
    <title>Register</title>
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<!-- Section: Design Block -->
<section class="background-radial-gradient overflow-hidden">
    <link rel="stylesheet" href="/css/register.css" />
    <link rel="stylesheet" href="<c:url value='/css/login.css'/>" />
    <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
        <div class="row gx-lg-5 align-items-center mb-5">
            <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
                <h1 class="my-5 display-5 fw-bold ls-tight" style="color: hsl(218, 81%, 95%)">
                    Lựa chọn tuyệt vời <br />
                    <span style="color: hsl(0, 57%, 55%)">cho thời trang của bạn</span>
                </h1>
            </div>

            <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
                <div id="radius-shape-1" class="position-absolute rounded-circle shadow-5-strong"></div>
                <div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>

                <div class="card bg-glass">
                    <div class="card-body px-4 py-5 px-md-5">

                        <form:form action="/register" method="POST" class="form-group" modelAttribute="newAccount">
<%--                            <c:set var="errorEmail">--%>
<%--                                <form:errors path="email" cssClass="invalid-feedback" cssStyle="color: red; font-size: 10px;"/>--%>
<%--                            </c:set>--%>
<%--                            <c:set var="errorPassword">--%>
<%--                                <form:errors path="password" cssClass="invalid-feedback"/>--%>
<%--                            </c:set>--%>
                            <h2 class="display-7 fw-bold" style="color: #740c0c;">ĐĂNG KÝ</h2>
                            <p style="color: red">${errorRegister}</p>
                            <div class="mb-3">
                                <label class="form-label" for="email">Địa chỉ email</label>
                                <form:input
                                        class="form-control"
                                        style="${not empty errorEmail ? 'border: red solid 1px ':'' } "
                                        path="email" type="email" id="email"
                                        placeholder="Nhập email"/>
                                    ${errorEmail}

                            </div>
                            <div class="mb-3">

                                <label class="form-label" for="password">Mật khẩu</label>
                                <form:input path="password"
                                            class="form-control"
                                            style="${not empty errorPassword ? 'border: red solid 1px ':'' } "
                                            type="password" id="password"
                                            placeholder="Nhập mật khẩu" aria-label="Password"/>

                                    <c:if test="${not empty errorPassword}">
                                        <p style="color: red">Mật khẩu của bạn phải dài hơn 5 kí tự</p>
                                    </c:if>

                            </div>
                            <div class="mb-3">

                                <label class="form-label" for="confirmpass">Xác nhận lại mật khẩu</label>
                                <input id="confirmpass"
                                       class="form-control"
                                    <%--                                            style="${not empty errorPassword ? 'border: red solid 1px ':'' } "--%>
                                       type="password"
                                       name="confirmpass"
                                       placeholder="Nhập mật khẩu" aria-label="Password"/>
                            </div>
                            <button type="submit" class="btn">Đăng ký</button>
                        </form:form>
                        <div class="description">
                            Hoặc đăng nhập với tài khoản Google của bạn hoặc
                            <c:if test="${not empty errorRegister}">
                            <a href="/forgot">Quên mật khẩu</a>

                            </c:if>
                        <div class="link">
                            Nếu đã có tài khoản <a href="/login">Trở về đăng nhập!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Section: Design Block -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>