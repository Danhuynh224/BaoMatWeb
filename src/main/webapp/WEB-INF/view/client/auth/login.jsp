<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <!-- Bootstrap CSS -->
  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
  />

  <!-- Bootstrap Icons -->
  <link
          rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
  />

  <!-- Custom CSS -->
  <link rel="stylesheet" href="/css/register.css" />
  <link rel="stylesheet" href="<c:url value='/css/login.css'/>" />


  <title>Login</title>
  <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico" />
</head>
<body>
<section class="background-radial-gradient overflow-hidden">
  <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
    <div class="row gx-lg-5 align-items-center mb-5">
      <!-- Left -->
      <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
        <h1 class="my-5 display-5 fw-bold ls-tight text-light">
          Lựa chọn tuyệt vời <br />
          <span style="color: hsl(0, 57%, 55%)">cho thời trang của bạn</span>
        </h1>
      </div>

      <!-- Right -->
      <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
        <div id="radius-shape-1" class="position-absolute rounded-circle shadow-5-strong"></div>
        <div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>

        <div class="card bg-glass">
          <div class="card-body px-4 py-5 px-md-5">
            <form action="/login" method="POST">
              <h2 class="display-7 fw-bold text-danger">ĐĂNG NHẬP</h2>
              <p><i>Đăng nhập để tích điểm và hưởng ưu đãi thành viên khi mua hàng</i></p>

              <c:if test="${param.error == 'blocked'}">
                <div class="my-2 text-danger">
                  Quá nhiều lần đăng nhập thất bại. Vui lòng thử lại sau 1 giờ.
                </div>
              </c:if>
              <c:if test="${param.error == 'true'}">
                <div class="my-2 text-danger">
                  Email hoặc mật khẩu không chính xác.
                </div>
              </c:if>

              <div class="mb-3">
                <label for="email" class="form-label">Địa chỉ email</label>
                <input
                        type="email"
                        class="form-control"
                        id="email"
                        name="username"
                        required
                        placeholder="Nhập email"
                />
                <div id="emailHelp" class="form-text">
                  Chúng tôi sẽ không chia sẻ thông tin với bất kỳ bên nào.
                </div>
              </div>

              <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu</label>
                <input
                        type="password"
                        class="form-control"
                        id="password"
                        name="password"
                        required
                        placeholder="Nhập mật khẩu"
                />
              </div>

              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

              <button type="submit" class="btn btn-primary">Đăng nhập</button>
            </form>

            <div class="description mt-3">
              Hoặc đăng nhập với tài khoản Google/Facebook của bạn
              <a href="/forgot">Quên mật khẩu</a>
            </div>


            <div class="link mt-3">
              Chưa có tài khoản? <a href="/register">Đăng ký ngay!</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- JavaScript -->
<script src="/js/login.js"></script>
</body>
</html>
