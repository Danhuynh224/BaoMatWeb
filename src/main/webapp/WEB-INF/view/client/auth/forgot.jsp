<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form" %> <%@page
contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/css/bootstrap-icons.min.css"
    />
    <link rel="stylesheet" href="/css/register.css" />
    <title>Forgot Password</title>
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
  </head>
  <body>
    <!-- Section: Design Block -->
    <section class="background-radial-gradient overflow-hidden">

      <link rel="stylesheet" href="<c:url value='/css/login.css'/>" />
      <link rel="stylesheet" href="/css/register.css" />
      <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
        <div class="row gx-lg-5 align-items-center mb-5">
          <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
            <h1
              class="my-5 display-5 fw-bold ls-tight"
              style="color: hsl(218, 81%, 95%)"
            >
              Lựa chọn tuyệt vời <br />
              <span style="color: hsl(0, 57%, 55%)"
                >cho thời trang của bạn</span
              >
            </h1>
          </div>

          <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
            <div
              id="radius-shape-1"
              class="position-absolute rounded-circle shadow-5-strong"
            ></div>
            <div
              id="radius-shape-2"
              class="position-absolute shadow-5-strong"
            ></div>

            <div class="card bg-glass">
              <div class="card-body px-4 py-5 px-md-5">
                <form action="/forgot" method="POST">
                  <h2 class="display-7 fw-bold" style="color: #740c0c">
                    Đặt lại mật khẩu
                  </h2>


                    <div class="my-2" style="color: red">
                      ${errorForget}
                    </div>
                  <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label"
                      >Địa chỉ email</label
                    >
                    <input
                      placeholder="Nhập email"
                      type="email"
                      class="form-control"
                      id="exampleInputEmail1"
                      aria-describedby="emailHelp"
                      name="email"
                      value="${email}"
                      required
                    />

                  </div>

                  <div>
                    <input
                      type="hidden"
                      name="${_csrf.parameterName}"
                      value="${_csrf.token}"
                    />
                  </div>

                  <button type="submit" class="btn btn-primary">
                    Send password to email
                  </button>
                </form>
                <div class="description">
                  Hoặc đăng nhập với tài khoản Google/Facebook của bạn
                </div>
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=${host}google&response_type=code&client_id=264565889451-li40qm8elg7569blr27vkuvb23qbmqe1.apps.googleusercontent.com&approval_prompt=force" class="btn btn-google">
                  <i class="bi bi-google"></i>
                </a>
                <a href="https://www.facebook.com/v19.0/dialog/oauth?client_id=1059853482122901&redirect_uri=http://localhost:8080/facebook" class="btn btn-google">
                  <i class="bi bi-facebook"></i>
                </a>
                <div class="link">
                  Chưa có tài khoản <a href="/register">Đăng ký ngay!</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Section: Design Block -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
