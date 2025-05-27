<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link
          href="https://cdn.jsdelivr.net/npm/remixicon/fonts/remixicon.css"
          rel="stylesheet"
  />
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      max-width: 960px; /* Giới hạn chiều rộng tối đa của form */
    }
    .form-control {
      border: 1px solid #ced4da;
      outline: none;
    }
    .input-group-text {
      background-color: #f8f9fa;
    }
    .form-section {
      background-color: #f8f9fa;
      padding: 20px;
      border-radius: 5px;
    }
    .form-label {
      font-weight: bold;
    }
    .d-grid button {
      background-color: #f05f25;
      font-weight: bold;
    }
    .order-summary {
      border: 1px solid #ced4da;
      padding: 15px;
      border-radius: 5px;
    }
    .order-summary img {
      width: 100px;
      height: 100px;
      object-fit: cover;
    }
    .payment-method {
      cursor: pointer;
      border: 1px solid #ced4da;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 10px;
    }
    .payment-method input[type="radio"] {
      cursor: pointer;
    }
  </style>
  <title>Checkout Page</title>
  <link rel="icon" type="image/x-icon" href="/images/assets/img/favicon.ico">
</head>
<body>
<jsp:include page="../layout/headerImport.jsp" />
<div class="container py-4">
  <a href="/cart" class="btn btn-danger" >back</a>
  <div class="d-flex flex-column flex-sm-row align-items-center bg-white py-4 px-3">
    <p class="text-decoration-none fs-2 fw-bold text-dark">
      Thông tin vận chuyển
    </p>
  </div>


  <div class="row mt-4">
    <div class="col-lg-6 form-section" style="margin-right: 50px">
      <h5 class="fw-bold">Thông tin chi tiết</h5>
      <p class="text-muted">Hoàn thành thông tin liên hệ của bạn</p>
      <form:form action="/pay/complete" method="post" modelAttribute="user">

      <c:set var="errorName">
        <form:errors path="name" cssClass="invalid-feedback"/>
      </c:set>
      <c:set var="errorAddrress">
        <form:errors path="address" cssClass="invalid-feedback"/>
      </c:set>
      <c:set var="errorPhoneNumber">
        <form:errors path="phoneNumber" cssClass="invalid-feedback"/>
      </c:set>

      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <hr>
      <div class="mb-3">
        <label for="Username" class="form-label">Họ và tên</label>
        <div class="input-group">
          <span class="input-group-text"><i class="ri-id-card-line"></i></span>
          <form:input path="name" type="text" class="form-control py-3 ${not empty errorName ? 'is-invalid' : ''}" id="Username" placeholder="Nhập họ và tên"   name="name" />
            ${errorName}
        </div>
      </div>
      <hr>
      <div class="mb-3">
        <label for="UserPhone" class="form-label">Số điện thoại</label>
        <div class="input-group">
          <span class="input-group-text"><i class="ri-phone-line"></i></span>
          <form:input path="phoneNumber" type="text" class="form-control py-3 ${not empty errorPhoneNumber ? 'is-invalid' : ''}" id="UserPhone" placeholder="Nhập số điện thoại"   name="phoneNumber" />
            ${errorPhoneNumber}
        </div>
      </div>
      <hr>
      <div class="mb-3">
        <p style="color: red">${errorAddressUpdate}</p>
        <label for="address-detail" class="form-label mb-3">Địa chỉ giao hàng</label>
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="province" class="form-label ms-2">Tỉnh/Thành phố</label>
            <select id="province" class="form-control ms-2" >
              <option value="">Chọn tỉnh</option>
            </select>
          </div>
          <div class="col-md-4">
            <label for="district" class="form-label ms-2">Quận/Huyện</label>
            <select id="district" class="form-control ms-2">
              <option value="">Chọn quận</option>
            </select>
          </div>
          <div class="col-md-4">
            <label for="ward" class="form-label ms-2">Phường/Xã</label>
            <select id="ward" class="form-control ms-2">
              <option value="">Chọn phường</option>
            </select>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-md-12">
            <label for="detail" class="form-label ms-2">Địa chỉ cụ thể</label>
            <input id="detail" class="form-control ms-2"  placeholder="Số nhà, đường..."/>
            <form:input path="address" id="address-detail" class="form-control" placeholder="Số nhà, đường..." style="display: none"/>
          </div>
          <input name="idform" style="display: none" value="3"> </input>
        </div>
      </div>
      <hr>
      <div class="mb-3">
        <label for="billing-note" class="form-label">Ghi chú</label>
        <div class="input-group">
          <span class="input-group-text"><i class="ri-sticky-note-line"></i></span>
          <input type="text" class="form-control py-3" id="billing-note" placeholder="Ví dụ: Giao hàng giờ hành chính" name="note" />
        </div>
      </div>

      <div class="py-2 border-top border-bottom">
        <div class="d-flex justify-content-between">
          <span>Tạm tính</span>
          <span id="priceWrapper">
    <span class="fw-bold" id="originalPrice" data-total="${totalPrice}">
      <fmt:formatNumber value="${totalPrice}" type="number" /> VND
    </span>
    <span class="fw-bold text-danger" id="discountedPrice" style="display: none;">0 VND</span>
  </span>
        </div>

        <div class="mb-3 mt-3">
          <label for="voucherCode" class="form-label">Mã Voucher</label>
          <div class="input-group">
            <span class="input-group-text"><i class="ri-ticket-line"></i></span>
            <input
                    type="text"
                    class="form-control py-3"
                    id="voucherCode"
                    name="voucherCode"
                    placeholder="Nhập mã voucher"
                    value="${voucherCode != null ? voucherCode : ''}" />
            <button type="button" class="btn btn-secondary" id="applyVoucher">Áp dụng</button>
          </div>
          <small id="voucherError" class="text-danger" style="display: none;">Mã giảm giá không hợp lệ!</small>
        </div>
        <div class="d-flex justify-content-between">
          <span>Phí giao hàng</span>
          <span class="fw-bold">30,000 VND</span>
        </div>
      </div>





      <div class="d-flex justify-content-between mt-3">
        <span>Tổng</span>
        <span class="fs-4 fw-bold"  id="totalPriceFinal"><fmt:formatNumber value="${totalPrice + 30000}" type="number"/> VND</span>
        <input type="hidden" name="totalPriceFinal" id="hiddenTotalPriceFinal" value="${totalPrice + 30000}">

      </div>

      <script>
        document.getElementById("applyVoucher").addEventListener("click", () => {
          const voucherCodeInput = document.getElementById("voucherCode");
          const voucherCode = voucherCodeInput.value.trim();
          const originalPriceElement = document.getElementById("originalPrice");
          const discountedPriceElement = document.getElementById("discountedPrice");
          const totalPriceFinalElement = document.getElementById("totalPriceFinal");
          const voucherErrorElement = document.getElementById("voucherError");
          const shippingFee = 30000; // Phí giao hàng cố định

          const total = parseFloat(originalPriceElement.getAttribute("data-total")); // Giá trị tạm tính ban đầu

          // Reset trạng thái
          voucherErrorElement.style.display = "none";
          voucherErrorElement.textContent = "";

          if (!voucherCode) {
            voucherErrorElement.style.display = "block";
            voucherErrorElement.textContent = "Vui lòng nhập mã voucher!";
            return;
          }

          // Gửi yêu cầu GET với tham số trong URL
          fetch(`/apply/voucher?voucher=` + voucherCode + `&priceTotal=` + total)
                  .then((response) => response.json())
                  .then((data) => {
                    if (data.error) {
                      voucherErrorElement.style.display = "block";
                      voucherErrorElement.textContent = data.error;

                      voucherCodeInput.removeAttribute("readonly");
                      voucherCodeInput.classList.remove("text-success");
                    } else {
                      voucherErrorElement.style.display = "none";

                      // Cập nhật giá sau giảm
                      discountedPriceElement.textContent = new Intl.NumberFormat("vi-VN").format(data.finalTotal) + " VND";
                      discountedPriceElement.style.display = "inline";
                      originalPriceElement.style.textDecoration = "line-through";

                      // Cập nhật tổng tiền linh hoạt
                      const totalFinal = data.finalTotal + shippingFee;
                      totalPriceFinalElement.textContent = new Intl.NumberFormat("vi-VN").format(totalFinal) + " VND";

                      // Đặt trạng thái cho ô voucher
                      voucherCodeInput.value = voucherCode;
                      voucherCodeInput.classList.add("text-success");
                      voucherCodeInput.setAttribute("readonly", true);

                      document.getElementById("hiddenTotalPriceFinal").value = totalFinal;

                    }
                  })
                  .catch((error) => {
                    console.error("Lỗi khi áp dụng mã giảm giá:", error);
                    alert("Đã xảy ra lỗi, vui lòng thử lại!");
                  });
        });

      </script>


      <div class="d-grid mt-4">
        <button class="btn btn-primary py-3">Đặt hàng</button>
      </div>
    </div>

    <div class="col-lg-5 p-4">
      <h5 class="fw-bold">Tóm tắt đơn hàng</h5>
      <p class="text-muted">Hãy kiểm tra kỹ thông tin của đơn hàng</p>

      <!-- Cart Items -->
      <div class="border p-3 mb-4">
        <c:forEach items="${items}" var="item">
          <div class="d-flex mb-3">
            <img src="/images/product/${item.productVariant.product.name}/${item.productVariant.image}" class="img-fluid rounded me-3" alt="Product" style="width: 100px; height: auto" />
            <div>
              <h6 class="fw-bold mb-1" style="width: 80%">${item.productVariant.product.name}</h6>
              <div class="mt-2">
                <input style="display: none" name="totalPrice" value="${item.quantity * item.productVariant.price + 30000}">
                <input type="text" class="form-control form-control-sm d-inline" style="width: 40px" value="${item.quantity}" disabled />
                <span class="fw-bold ms-3 fs-5"><fmt:formatNumber type="number" value="${item.quantity * item.productVariant.price}"/>₫</span>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>

      <h5 class="fw-bold">Phương thức thanh toán</h5>
      <div class="container mt-5">
        <form>
          <!-- Phương thức thanh toán -->
          <div class="payment-method position-relative">
            <input class="form-check-input position-absolute top-50 end-0 translate-middle-y me-3"
                   type="radio" name="paymentMethod" id="codRadio" value="cod" checked />
            <label class="d-flex align-items-center" for="codRadio">
              <i class="ri-truck-line fs-2"></i>
              <div class="ms-3">
                <span class="d-block fw-bold">COD</span>
                <p class="text-muted small mb-0">Thanh toán khi nhận hàng</p>
              </div>
            </label>
          </div>

          <div class="payment-method position-relative">
            <input class="form-check-input position-absolute top-50 end-0 translate-middle-y me-3"
                   type="radio" name="paymentMethod" id="onlineRadio" value="online_1" />
            <label class="d-flex align-items-center" for="onlineRadio">
              <i class="ri-wallet-3-line fs-2"></i>
              <div class="ms-3">
                <span class="d-block fw-bold">Online Payment</span>
                <p class="text-muted small mb-0">Thanh toán VNPAY</p>
              </div>
            </label>
          </div>
          <div class="payment-method position-relative">
            <input class="form-check-input position-absolute top-50 end-0 translate-middle-y me-3"
                   type="radio" name="paymentMethod" id="onlineRadio2" value="online_2" />
            <label class="d-flex align-items-center" for="onlineRadio2">
              <i class="ri-wallet-3-line fs-2"></i>
              <div class="ms-3">
                <span class="d-block fw-bold">Online Payment</span>
                <p class="text-muted small mb-0">Thanh toán Banking</p>
              </div>
            </label>
          </div>

        </form>


      </div>
      </form:form>

    </div>
  </div>
</div>



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
</body>
</html>

