<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cảm ơn đã đặt hàng</title>
  <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="vh-100 d-flex justify-content-center align-items-center">
  <div class="col-md-4">
    <div class="border border-3 border-success"></div>
    <div class="card  bg-white shadow p-5">
      <div class="mb-4 text-center">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-success" width="75" height="75"
             fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
          <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z" />
          <path
                  d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z" />
        </svg>
      </div>
      <div class="text-center">
        <h1>Thank You !</h1>
        <p>Cảm ơn bạn đã mua hàng!!!
          <strong style="color: red">${contentError}</strong>

        </p>
        <a href="/" class="btn btn-outline-success">Trang chủ</a>
      </div>
    </div>
  </div>
</div>
</body>

</html>