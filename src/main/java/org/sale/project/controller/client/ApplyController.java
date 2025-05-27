package org.sale.project.controller.client;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.entity.User;
import org.sale.project.entity.Voucher;
import org.sale.project.service.AccountService;
import org.sale.project.service.UserService;
import org.sale.project.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/apply")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true )
public class ApplyController {


    VoucherService voucherService;
    AccountService accountService;

    @GetMapping("/voucher")
    public Map<String, Object> validateVoucher(@RequestParam("voucher") Optional<String> codeOptional, @RequestParam("priceTotal") Optional<Double> totalOptional, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        String code = codeOptional.orElse("");
        if(code.charAt(0) != 'P'){
            code = 'E' + code;
        }
        Voucher voucher = voucherService.findByCode(code);
        double total = totalOptional.orElse(0.0D);

        if(voucher != null){

            if(voucher.getCode().charAt(0) == 'P'){

                HttpSession session = request.getSession();
                String email = (String) session.getAttribute("email");

                Account account = accountService.findByEmail(email);

                if(account != null && account.getId().contains(voucher.getCode().substring(1, 5))){
                    response.put("discount", voucher.getDiscountValue());
                    response.put("finalTotal", Math.max(0, total*(1 - voucher.getDiscountValue() / 100.0)));
                    response.put("message", "Mã giảm giá áp dụng thành công!");

                } else{
                    response.put("error", "Mã giảm giá không hợp lệ!");
                }

            }
            else if (!voucher.getActive()) {
                response.put("error", "Mã giảm giá đã hết hạn!");
            } else if (isVoucherValid(voucher.getEndDate().toString())) {
                response.put("discount", voucher.getDiscountValue());
                response.put("finalTotal", Math.max(0, total*(1 - voucher.getDiscountValue() / 100.0)));
                response.put("message", "Mã giảm giá áp dụng thành công!");
            } else  {
                response.put("error", "Mã giảm giá không hợp lệ!");
            }
        } else{
            response.put("error", "Mã giảm giá không tồn tại");
        }

        return response;
    }

    // Phương thức kiểm tra ngày hết hạn mã giảm giá
    private boolean isVoucherValid(String expiryDate) {
        LocalDate expiry = LocalDate.parse(expiryDate);
        return LocalDate.now().isBefore(expiry) || LocalDate.now().isEqual(expiry);
    }
}
