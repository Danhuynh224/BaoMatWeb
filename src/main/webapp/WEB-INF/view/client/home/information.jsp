<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thông tin tài khoản</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .form-container {
      background-color: white;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      display: none; /* Ẩn form mặc định */
    }
    .section-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin-bottom: 15px;
    }
    .btn-save {
      background-color: #20c997;
      color: white;
    }
    .btn-save:hover {
      background-color: #17a589;
    }
    .sidebar {
      margin-right: 20px;
    }
  </style>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<jsp:include page="../layout/header.jsp" />


<div class="container" style="margin-top: 100px ">
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-3">
      <ul class="list-group sidebar">
        <li class="list-group-item ${sessionScope.get("checkid") != '3' and sessionScope.get("checkid") != '4' ? 'active' : ''}" onclick="showSection('account-info')">Thông tin tài khoản</li>
        <li class="list-group-item " onclick="showSection('order-history')">Lịch sử đơn hàng</li>
        <li class="list-group-item ${sessionScope.get("checkid")  == '3' ? 'active' : ''}"  onclick="showSection('account-address')">Địa chỉ giao hàng</li>
        <li class="list-group-item ${sessionScope.get("checkid")  == '4' ? 'active' : ''}"  onclick="showSection('pass-update')">Thay đổi mật khẩu</li>
      </ul>
    </div>

    <!-- Form Thông tin tài khoản -->
    <div class="col-md-9 form-container" id="account-info" style="display: ${sessionScope.get("checkid") != '3' and sessionScope.get("checkid") != '4' ? 'block' : 'none' };">
      <div>
        <h2 class="mb-4">Thông tin tài khoản</h2>
        <form:form action="/account/update-information"  enctype="multipart/form-data" method="post" modelAttribute="user">
          <div class="row mb-3">
            <c:set var="errorName">
              <form:errors path="name" cssClass="invalid-feedback" />
            </c:set>
            <c:set var="erroPhone">
              <form:errors path="phoneNumber" cssClass="invalid-feedback" />
            </c:set>
            <c:set var="errorBirthDay">
              <form:errors path="birthDay" cssClass="invalid-feedback" />
            </c:set>

            <div class="col-md-6">
              <img src="/images/avatar/${not empty user.image ?  user.image : 'default.png'}" alt="${user.image}" style="width: 100px; height: auto;">
            </div>

            <div class="mb-3 col-12 col-md-6">
              <label for="imageAvatar" class="form-label">Ảnh đại điện</label>
              <input class="form-control" type="file" id="imageAvatar" name="imageAvatar" accept=".png, .jpg, .jpeg">
              <div class="form-text">Chỉ chấp nhận file PNG, JPEG, JPG. Kích thước tối đa: 5MB</div>
              <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-2">${errorMessage}</div>
              </c:if>
            </div>

            <div class="col-md-6">
              <label for="fullName" class="form-label">Họ và tên</label>

              <form:input path="name" type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}" id="fullName" placeholder="Tên khách hàng" />
                ${errorName}

            </div>
          </div>
          <div class="row mb-3">

            <div class="col-md-6">
              <label for="phone" class="form-label">Số điện thoại</label>

              <form:input path="phoneNumber" type="text" class="form-control ${not empty erroPhone ? 'is-invalid' : ''}" id="phone"  />
                ${erroPhone}

            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label>Giới tính</label><br>

                <!-- Radio Nam -->
                <form:radiobutton path="sex" id="male" value="1"
                />
                <label for="male">Nam</label>

                <!-- Radio Nữ -->
                <form:radiobutton path="sex" id="female" value="0"
                />
                <label for="female">Nữ</label>

                <!-- Radio Khác giới -->
                <form:radiobutton path="sex" id="other" value="-1"
                />
                <label for="other">Khác giới</label>
              </div>

            </div>
          </div>
          <div class="row mb-4">
            <div class="col-md-6">
              <label for="dob" class="form-label">Ngày sinh</label>
              <form:input path="birthDay" type="date" class="form-control ${not empty errorBirthDay ? 'is-invalid' : ''}" id="dob" />
                ${errorBirthDay}

            </div>
          </div>
          <h4 class="mb-3">Thông tin đăng nhập</h4>
          <div class="row mb-4">
            <div class="col-md-6">
              <label for="email" class="form-label">Email</label>
              <input path="email" disabled type="email" class="form-control" id="email"  value="${email}">
            </div>
          </div>
          <input name="idform" style="display: none" value="1"> </input>

          <button type="submit" class="btn btn-save btn-success">Lưu thông tin</button>
        </form:form>
      </div>
    </div>

    <!-- Form Lịch sử đơn hàng -->
    <div class="col-md-9 form-container " id="order-history" ${sessionScope.get("checkid") == '2' ? 'style="display: none"':''  }>
      <div>
        <h2 class="mb-4">Lịch sử đơn hàng</h2>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">Id</th>

            <th scope="col">Ngày đặt</th>
            <th scope="col">Tình trạng</th>
            <th scope="col">Tổng tiền</th>
            <th scope="col">Chi Tiết</th>


          </tr>
          </thead>
          <tbody>
          <c:forEach items="${orders}" var="order">
            <tr>

              <td>  <a href="/order/${order.id}" > #${order.id.substring(0, 5)} </a></td>

              <td>${order.date}</td>
              <td style="color: ${order.status.toString() == 'RETURN' || order.status.toString() == 'CANCEL' || order.status.toString() == 'PAYMENT_FAILED' ? 'red' : 'green'}" >
                  ${order.status.toString() == 'PROCESSING' ? 'Xử lý' :
                          order.status.toString() == 'PAYMENT_FAILED' ? 'Thanh toán thất bại' :
                                  order.status.toString() == 'SHIPPING' ? 'Giao Hàng' :
                                          order.status.toString() == 'COMPLETED' ? 'Hoàn Thành' :
                                                  order.status.toString() == 'RETURNED' ? 'Hoàn Trả Hàng' :
                                                          order.status.toString() == 'CANCEL' ? 'Hủy' : ""
                          }
              </td>
              <td><fmt:formatNumber type="number" value="${order.total}" /> VND</td>
              <td> <a href="/order/${order.id}" class = "btn btn-primary">Xem</a> </td>



            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="col-md-9 form-container" id="account-address" ${sessionScope.get("checkid") == '3' ? 'style="display: block"':''  }>
      <p style="color: red">${errorAddressUpdate}</p>
      <form:form action="/account/update-adress" method="post" modelAttribute="user">
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="province" class="form-label">Tỉnh/Thành phố</label>
            <select id="province" class="form-control" >
              <option value="">Chọn tỉnh</option>
            </select>
          </div>
          <div class="col-md-4">
            <label for="district" class="form-label">Quận/Huyện</label>
            <select id="district" class="form-control">
              <option value="">Chọn quận</option>
            </select>
          </div>
          <div class="col-md-4">
            <label for="ward" class="form-label">Phường/Xã</label>
            <select id="ward" class="form-control">
              <option value="">Chọn phường</option>
            </select>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-md-12">
            <label for="detail" class="form-label">Địa chỉ cụ thể</label>
            <input id="detail" class="form-control"  placeholder="Số nhà, đường..."/>
            <form:input path="address" id="address-detail" class="form-control" placeholder="Số nhà, đường..." style="display: none"/>
          </div>
          <input name="idform" style="display: none" value="3"> </input>
        </div>
        <button type="submit" class="btn btn-success btn-save">Lưu địa chỉ</button>
      </form:form>


      <h2 id="result"></h2>
    </div>
    <div class="col-md-9 form-container" id="pass-update" style="display: ${sessionScope.get("checkid") == '4' ? 'block' : 'none' };">
      <div>
        <h2 class="mb-4">Thay đổi mật khẩu</h2>
        <form action="/account/pass-update"  enctype="multipart/form-data" method="post">
          <p style="color: red">${errorPassUpdate}</p>
          <input
                  type="hidden"
                  name="${_csrf.parameterName}"
                  value="${_csrf.token}"
          />
          <div class="row mb-3">

            <div class="col-md-6">
              <label for="pass" class="form-label">Mật khẩu hiện tại</label>

              <input name="pass" type="password" class="form-control" id="pass" placeholder="Mật khẩu hiện tại" value="${pass}" />


            </div>
          </div>
          <div class="row mb-3">

            <div class="col-md-6">
              <label for="newpass" class="form-label">Mật khẩu mới</label>

              <input name="newpass" type="password" class="form-control" id="newpass" placeholder="Mật khẩu mới" value="${newpass}"/>

            </div>
          </div>
          <div class="row mb-3">

            <div class="col-md-6">
              <label for="confirmpass" class="form-label">Xác nhận mật khẩu mới</label>

              <input name="confirmpass" type="password" class="form-control" id="confirmpass" placeholder="Xác nhận lại mật khẩu" value="${confirmpass}"/>

            </div>
          </div>
          <input name="idform" style="display: none" value="4"/>

          <button type="submit" class="btn btn-save btn-success">Lưu thông tin</button>
        </form>
      </div>
    </div>
  </div>

</div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.26.1/axios.min.js" integrity="sha512-bPh3uwgU5qEMipS/VOmRqynnMXGGSRv+72H/N260MQeXZIK4PG48401Bsby9Nq5P5fz7hy5UGNmC/W1Z51h2GQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script>
  const host = "https://provinces.open-api.vn/api/";

  // Hàm gọi API lấy dữ liệu tỉnh/thành phố
  var callAPI = (api) => {
    return axios.get(api)
            .then((response) => {
              if (Array.isArray(response.data)) {
                renderData(response.data, "province");
              } else {
                console.error("Province data is not an array:", response.data);
              }
            })
            .catch((error) => {
              console.error("Error fetching provinces:", error);
            });
  };

  // Hàm gọi API lấy dữ liệu quận/huyện
  var callApiDistrict = (api) => {
    return axios.get(api)
            .then((response) => {
              if (Array.isArray(response.data.districts)) {
                renderData(response.data.districts, "district");
              } else {
                console.error("Districts data is not an array:", response.data);
              }
            })
            .catch((error) => {
              console.error("Error fetching districts:", error);
            });
  };

  // Hàm gọi API lấy dữ liệu phường/xã
  var callApiWard = (api) => {
    return axios.get(api)
            .then((response) => {
              if (Array.isArray(response.data.wards)) {
                renderData(response.data.wards, "ward");
              } else {
                console.error("Wards data is not an array:", response.data);
              }
            })
            .catch((error) => {
              console.error("Error fetching wards:", error);
            });
  };

  // Hàm hiển thị dữ liệu vào <select>
  var renderData = (array, select) => {
    if (!Array.isArray(array)) {
      console.error("Expected an array, got:", array);
      return;
    }

    let row = '<option disable value="">chọn</option>';
    array.forEach(function (element) {
      row += '<option value="' + element.code + '">' + element.name + '</option>';
    });
    document.querySelector("#" + select).innerHTML = row;
  };

  // Hàm in kết quả lựa chọn
  var printResult = () => {
    if ($("#district").val() != "" && $("#province").val() != "" &&
            $("#ward").val() != "") {
      let result = $("#province option:selected").text() +
              " | " + $("#district option:selected").text() + " | " +
              $("#ward option:selected").text()+ " | " + $("#detail").val();
      $("#address-detail").val(result);
    }
  };
  // Hàm load dữ liệu từ #address-detail
  var loadAddressDetail = () => {
    const addressParts = $("#address-detail").val().split(" | ");
    if (addressParts.length === 4) {
      const [provinceName, districtName, wardName, detail] = addressParts;

      // Gọi API để lấy danh sách province
      axios.get(host + "?depth=1")
              .then((response) => {
                const provinces = response.data;
                const selectedProvince = provinces.find(p => p.name === provinceName);
                if (selectedProvince) {
                  $("#province").val(selectedProvince.code);

                  // Gọi API để lấy danh sách district của tỉnh đã chọn
                  return axios.get(host + "p/" + selectedProvince.code + "?depth=2");
                }
              })
              .then((response) => {
                const districts = response.data.districts;
                renderData(districts, "district");
                const selectedDistrict = districts.find(d => d.name === districtName);
                if (selectedDistrict) {
                  $("#district").val(selectedDistrict.code);

                  // Gọi API để lấy danh sách ward của quận đã chọn
                  return axios.get(host + "d/" + selectedDistrict.code + "?depth=2");
                }
              })
              .then((response) => {
                const wards = response.data.wards;
                renderData(wards, "ward");
                const selectedWard = wards.find(w => w.name === wardName);
                if (selectedWard) {
                  $("#ward").val(selectedWard.code);
                }
              })
              .catch((error) => {
                console.error("Error loading address detail:", error);
              });
      $("#detail").val(detail);
    } else {
      console.warn("Address detail format is incorrect.");
    }
  };

  $(document).ready(() => {
    // Kiểm tra nếu #address-detail có dữ liệu thì thực hiện load ngược
    if ($("#address-detail").val().trim() !== "") {
      loadAddressDetail();
    }
  });


  // Gọi API để lấy danh sách tỉnh/thành phố ban đầu
  callAPI(host + "?depth=1");

  // Xử lý sự kiện khi thay đổi tỉnh/thành phố
  $("#province").change(function () {
    const provinceCode = $("#province").val();
    if (provinceCode) {
      callApiDistrict(host + "p/" + provinceCode + "?depth=2");
    } else {
      document.querySelector("#district").innerHTML = '<option disable value="">chọn</option>';
      document.querySelector("#ward").innerHTML = '<option disable value="">chọn</option>';
    }
    printResult();
  });

  // Xử lý sự kiện khi thay đổi quận/huyện
  $("#district").change(function () {
    const districtCode = $("#district").val();
    if (districtCode) {
      callApiWard(host + "d/" + districtCode + "?depth=2");
    } else {
      document.querySelector("#ward").innerHTML = '<option disable value="">chọn</option>';
    }
    printResult();
  });

  // Xử lý sự kiện khi thay đổi phường/xã
  $("#ward").change(function () {
    printResult();
  });
  // Gọi hàm printResult khi nội dung của #detail thay đổi
  $("#detail").change("input", () => {
    printResult();
  });

</script>


<script>



  function showSection(sectionId) {
    // Ẩn tất cả form
    document.querySelectorAll('.form-container').forEach(function (section) {
      section.style.display = 'none';
    });
    // Hiển thị form được chọn
    document.getElementById(sectionId).style.display = 'block';

    // Đặt class 'active' cho phần được chọn trong sidebar
    document.querySelectorAll('.list-group-item').forEach(function (item) {
      item.classList.remove('active');
    });
    event.target.classList.add('active');
  }
</script>

<jsp:include page="../layout/footer.jsp" />

</body>
</html>