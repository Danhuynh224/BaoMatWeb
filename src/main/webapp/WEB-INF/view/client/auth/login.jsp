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
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
    />
    <link rel="stylesheet" href="/css/register.css" />
    <title>Login</title>
    <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
  </head>
  <body>
    <!-- Section: Design Block -->
    <section class="background-radial-gradient overflow-hidden">
      <style>
        .background-radial-gradient {
          background-color: hsl(6, 56%, 36%);
          background-image: radial-gradient(
              650px circle at 0% 0%,
              hsl(0, 68%, 63%) 15%,
              hsl(0, 61%, 53%) 35%,
              hsl(0, 59%, 46%) 75%,
              hsl(0, 67%, 34%) 80%,
              transparent 100%
            ),
            radial-gradient(
              1250px circle at 100% 100%,
              hsl(0, 68%, 63%) 15%,
              hsl(0, 61%, 53%) 35%,
              hsl(0, 59%, 46%) 75%,
              hsl(0, 67%, 34%) 80%,
              transparent 100%
            );
        }

        #radius-shape-1 {
          height: 220px;
          width: 220px;
          top: -60px;
          left: -130px;
          background: radial-gradient(#ffffff, #d80202);
          overflow: hidden;
        }

        #radius-shape-2 {
          border-radius: 38% 62% 63% 37% / 70% 33% 67% 30%;
          bottom: -60px;
          right: -110px;
          width: 300px;
          height: 300px;
          background: radial-gradient(#ffffff, #d80202);
          overflow: hidden;
        }

        .bg-glass {
          background-color: hsla(0, 0%, 100%, 0.9) !important;
          backdrop-filter: saturate(200%) blur(25px);
        }
      </style>

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
                <form action="/login" method="POST">
                  <h2 class="display-7 fw-bold" style="color: #740c0c">
                    ĐĂNG NHẬP
                  </h2>
                  <p>
                    <i>Đăng nhập để tích điểm và hưởng ưu đãi thành viên khi mua hàng</i>
                  </p>
                  <c:if test="${param.error == 'blocked'}">
                    <div class="my-2" style="color: red">
                      Quá nhiều lần đăng nhập thất bại. Vui lòng thử lại sau 1 giờ.
                    </div>
                  </c:if>
                  <c:if test="${param.error == 'true'}">
                    <div class="my-2" style="color: red">
                      Email hoặc mật khẩu không chính xác.
                    </div>
                  </c:if>
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
                      name="username"
                      required
                    />
                    <div id="emailHelp" class="form-text">
                      Chúng tôi sẽ không chia sẽ thông tin với bất kỳ bên
                    </div>
                  </div>

                  <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label"
                      >Mật khẩu</label
                    >
                    <input
                      placeholder="Nhập mật khẩu"
                      type="password"
                      class="form-control"
                      id="exampleInputPassword1"
                      name="password"
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
                    Đăng nhập
                  </button>
                </form>
                <div class="description">
                  Hoặc đăng nhập với tài khoản Google/Facebook của bạn  <a href="/forgot">Quên mật khẩu</a>
                </div>
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=${host}google&response_type=code&client_id=264565889451-li40qm8elg7569blr27vkuvb23qbmqe1.apps.googleusercontent.com&approval_prompt=force" class="btn btn-google">
                  <i class="bi bi-google"></i>
                </a>
                <a href="https://www.facebook.com/v19.0/dialog/oauth?fields=id,name,email,picture&client_id=1059853482122901&redirect_uri=${sessionScope.host}facebook" class="btn btn-google">
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
