package org.sale.project.controller.client;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.RandomStringUtils;
import org.sale.project.controller.auth.GoogleLogin;
import org.sale.project.dto.request.StarReview;
import org.sale.project.entity.*;
import org.sale.project.service.*;
import org.sale.project.service.email.EmailService;
import org.sale.project.service.review.ScoreStarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Controller
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HomeController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    RoleService roleService;
    ProductService productService;
    EmailService emailService;
    AccountService accountService;
    RecommendationService recommendationService;
    ScoreStarService scoreStarService;


    @NonFinal
    @Value("${name.host}")
    String host;




    private final VoucherService voucherService;

    @GetMapping("/register")
    public String getPageRegister(Model model) {
        model.addAttribute("newAccount", new Account());
        model.addAttribute("host", host);
        return "/client/auth/register";
    }

    @GetMapping("/forgot")
    public String getPageForgot(Model model) {
        model.addAttribute("email", "");
        model.addAttribute("host", host);
        return "/client/auth/forgot";
    }

    @PostMapping("/forgot")
    public String forget(@RequestParam("email") String email, Model model) throws MessagingException {

        String password = accountService.forgotPassword(email);

        if(password == null) {
            model.addAttribute("host", host);

            model.addAttribute("errorForget", "Email không tồn tại trong hệ thống");
            model.addAttribute("email", HtmlUtils.htmlEscape(email));
            return "/client/auth/forgot";
        }

        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newAccount") @Valid Account account,@RequestParam("confirmpass") String confirmpass, BindingResult bindingResult, Model model, HttpServletRequest request) throws MessagingException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("host", host);


            return "/client/auth/register";
        }
        if(accountService.findByEmail(account.getEmail()) != null) {
            model.addAttribute("host", host);

            model.addAttribute("errorRegister", "Email đã tồn tại!!!!");
            model.addAttribute("newAccount", account);
            return "/client/auth/register";
        }
        String email = account.getEmail();
        String temppass = account.getPassword();
        if(!temppass.equals(confirmpass))
        {
            model.addAttribute("host", host);

            model.addAttribute("errorRegister", "Mật khẩu xác thực không trùng khớp");
            model.addAttribute("newAccount", account);
            return "/client/auth/register";
        }

        if(temppass.length()<=8)
        {
            model.addAttribute("host", host);

            model.addAttribute("errorRegister", "Mật khẩu phải dài hơn 8 chữ số");
            model.addAttribute("newAccount", account);
            return "/client/auth/register";
        }


        // Biểu thức chính quy kiểm tra mật khẩu mạnh
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        if (!temppass.matches(passwordRegex)) {
            model.addAttribute("host", host);

            model.addAttribute("errorRegister", "Mật khẩu phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt.");
            model.addAttribute("newAccount", account);
            return "/client/auth/register";

        }


        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(roleService.findByName("USER"));


        account =  accountService.saveAccount(account);

        Voucher voucher = voucherService.regisCreate(account);


        String content = emailService.MailLogin(account, voucher);
        emailService.sendHtmlEmail(account.getEmail(),"REGISTER CORRECT",content);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);


        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, temppass, userDetails.getAuthorities());


        //Xác thực
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);


        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        session.setAttribute("email", email);




        return "redirect:/";

    }

    //    SecurityContextHolderFilter securityContextHolderFilter;
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("/google")
    public String accessLogin(Model model, HttpServletRequest request) throws Exception {

        String code = request.getParameter("code");

        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code, host);

        System.out.println(">>>>accessToken: " + accessToken);

        GoogleAccount ggAc = gg.getUserInfo(accessToken);
        System.out.println(ggAc.toString());


        String email = ggAc.getEmail();

        String password = ggAc.getEmail().substring(0, ggAc.getEmail().indexOf("@"));

        System.out.println(">>>>email: " + email + ", password: " + password);


        if(accountService.findByEmail(email) == null) {

            accountService.saveAccount(Account.builder().email(email).password(passwordEncoder.encode(password)).role(roleService.findByName("USER")).build());
            Account newAccount = accountService.findByEmail(email);
            Voucher voucher = voucherService.regisCreate(newAccount);
            String content = emailService.MailLogin(newAccount, voucher);
            emailService.sendHtmlEmail(email,"REGISTER CORRECT",content);

        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Tạo token xác thực
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());



        SecurityContextHolder.getContext().setAuthentication(authToken);
        User user = userService.findUserByEmail(email);

        HttpSession session = request.getSession(true);


        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        session.setAttribute("email", email);
        if(user != null) {
            session.setAttribute("id", user.getId());
            session.setAttribute("sum", user.getCart() == null || user.getCart().getCartItems() == null ? 0
                    : user.getCart().getCartItems().size());

        }


        return "redirect:/";
    }


    @GetMapping
    public String getPageHome(Model model, HttpServletRequest request) throws IOException {


        HttpSession session = request.getSession();


        String email = (String) session.getAttribute("email");


        session.setAttribute("nameSearch" , "");


        List<Product> products = email != null && !email.equals("admin@gmail.com") ? productService.findAll(recommendationService.getTopNRecommendations(userService.findUserByEmail(email), 12))
                : productService.findAll() ;


        if(email != null) {
            products = products.size() == 12 ? products : fullProduct(products);
        }
        Map<Product, StarReview> mapProductAndScore = new HashMap<>();
        for(Product p : products) {
            mapProductAndScore.put(p, scoreStarService.score(p));
        }


        model.addAttribute("products", mapProductAndScore);


        return "/client/home/show";
    }

    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        session.setAttribute("nameSearch" , "");

        return "/client/about/show";
    }

    @GetMapping("/blog")
    public String blog(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();




        session.setAttribute("nameSearch" , "");

        return "/client/blog/show";
    }



    private List<Product> fullProduct(List<Product> products) {
        List<Product> editableProducts = new ArrayList<>(products);

        List<Product> fullProducts = productService.findAll();
        for (Product product : fullProducts) {
            if (editableProducts.size() == 12) {
                break;
            }
            if (!editableProducts.contains(product)) {
                editableProducts.add(product);
            }
        }
        return editableProducts;
    }



    @GetMapping("/login")
    public String login(Model model) {
        System.out.println(">>>host: " + host);
        model.addAttribute("host", host);
        return "/client/auth/login";
    }

    @GetMapping("/404")
    public String notFound() {
        return "/client/error/404";
    }




}
