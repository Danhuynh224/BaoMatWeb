package org.sale.project.controller.client;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.entity.Order;
import org.sale.project.entity.User;
import org.sale.project.service.AccountService;
import org.sale.project.service.UploadService;
import org.sale.project.service.UserService;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AccountClientController {

    UserService userService;
    UploadService uploadService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;

    @GetMapping
    public String getPageInformation(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        Optional<User> userOptional = userService.findByEmail(email);
        model.addAttribute("email", email);

        model.addAttribute("user", userOptional.orElseGet(User::new));
        model.addAttribute("orders", userOptional.isEmpty()
                ? new ArrayList<Order>()
                : userOptional.get().getOrders().stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .collect(Collectors.toList()));

        return "/client/home/information";
    }

    @PostMapping("/update-information")
    public String updateInformation(Model model, HttpServletRequest request,
                                    @ModelAttribute("user") @Valid User userUpdate, BindingResult bindingResult,
                                    @RequestParam("imageAvatar") MultipartFile imageAvatar,@RequestParam("idform") Optional<String> idform){

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>> user: " + fieldError.getField() + fieldError.getDefaultMessage());

        }
        if (bindingResult.hasErrors()) {
            return "/client/home/information";
        }


        if(!imageAvatar.isEmpty()){
            String img = uploadService.uploadImage(imageAvatar, "/avatar");
            userUpdate.setImage(img);
        }


        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        userService.updateUser(email, userUpdate);
        session.setAttribute("checkid", idform.get());
        return "redirect:/account";
    }
    @PostMapping("/update-adress")
    public String updateAddress(Model model, HttpServletRequest request,
                                @ModelAttribute("user") @Valid User userUpdate, BindingResult bindingResult,
                                @RequestParam("idform") Optional<String> idform) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        session.setAttribute("checkid", idform.get());
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        for (FieldError fieldError : fieldErrors) {
//            System.out.println(">>> user: " + fieldError.getField() + fieldError.getDefaultMessage());
//
//        }
//        if (bindingResult.hasErrors()) {
//            return "/client/home/information";
//        }
        if(userUpdate.getAddress().chars().filter(ch -> ch == '|').count()!=3)
        {
            model.addAttribute("errorAddressUpdate", "Vui lòng nhập đầy đủ thông tin địa chỉ");
            return "/client/home/information";
        }
        userService.updateUserAddress(email, userUpdate.getAddress());
        return "redirect:/account";
    }
    @PostMapping("/pass-update")
    public String updatePass(HttpServletRequest request,
                             @RequestParam("pass") String pass,
                             @RequestParam("newpass") String newpass,
                             @RequestParam ("confirmpass") String confirmpass,
                             @RequestParam("idform") Optional<String> idform,
                             Model model) {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        Optional<User> userOptional = userService.findByEmail(email);
        model.addAttribute("email", email);
//        User user = userOptional.get();

        model.addAttribute("user", userOptional.orElseGet(User::new));
        model.addAttribute("orders", userOptional.isEmpty()
                ? new ArrayList<Order>()
                : userOptional.get().getOrders().stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .collect(Collectors.toList()));

        session.setAttribute("checkid", idform.get());
        model.addAttribute("pass", pass);
        model.addAttribute("newpass", newpass);
        model.addAttribute("confirmpass", confirmpass);
        Account account = accountService.findByEmail(email);
        boolean check= passwordEncoder.matches(pass, account.getPassword());
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        if(check){
            if(newpass.length()<8){
                model.addAttribute("errorPassUpdate", "Mật khẩu mới phải có độ  dài tên 8 ký tự.");
                return "/client/home/information";
            }
            else if (!newpass.matches(passwordRegex)) {
                model.addAttribute("errorPassUpdate", "Mật khẩu phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt.");
                return "/client/home/information";
            }
            else if(!newpass.equals(confirmpass)){
                model.addAttribute("errorPassUpdate", "Mật khẩu mới và mật khẩu xác nhận không trùng nhau.");
                return "/client/home/information";
            }
            else
            {
                account.setPassword(passwordEncoder.encode(newpass));
                accountService.updateAccount(account);
                logoutAllSessionsOfUser(email);
            }
        }
        else {
            model.addAttribute("errorPassUpdate", "Mật khẩu tài khoản không đúng");
            return "/client/home/information";
        }

        return "redirect:/account";
    }
    public void logoutAllSessionsOfUser(String username) {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object principal : allPrincipals) {
            if (principal instanceof UserDetails userDetails) {
                if (userDetails.getUsername().equals(username)) {
                    sessionRegistry.getAllSessions(userDetails, false)
                            .forEach(sessionInfo -> {
                                sessionInfo.expireNow(); // Đăng xuất session
                            });
                }
            }
        }
    }
}

