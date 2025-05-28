package org.sale.project.controller.admin;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.sale.project.entity.Order;
import org.sale.project.entity.User;
import org.sale.project.enums.StatusOrder;
import org.sale.project.service.OrderDetailService;
import org.sale.project.service.OrderService;
import org.sale.project.service.email.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;
    OrderDetailService orderDetailService;
    EmailService emailService;


    @GetMapping
    public String getPageOrder(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Order> pOrder = orderService.findAll(pageable);
        List<Order> orders = pOrder.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pOrder.getTotalPages());
        return "/admin/order/show";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdate(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        Order order = orderService.findById(id);
        if(order != null){
            HttpSession session = request.getSession();
            session.setAttribute("totalAnnounce", Math.max((int)session.getAttribute("totalAnnounce") - 1, 0));
            order.setAnnounceOrder(false);

            orderService.saveOrder(order);
        }
        model.addAttribute("order", order != null ? order : new Order());


        return "/admin/order/update";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") Order order, HttpServletRequest request) throws MessagingException {

        Order oldOrder = orderService.findById(order.getId());
        User user = oldOrder.getUser();


        oldOrder.setStatus((StatusOrder) order.getStatus());
        String content = "<p> Status:  <strong>" + oldOrder.getStatus() + "</strong></p>" ;
        if(order.getStatus().name().equals("SHIPPING")){
            content += "</br>  <p> Đơn hàng của bạn đang trong quá trình vận chuyển </p>";
        } else{
            content += "</br>  <p> Đơn hàng của bạn đã thành công </p>";
        }
        orderService.saveOrder(oldOrder);
            String emailContent=emailService.MailOrderStatus(oldOrder, user);
            if(!emailContent.isEmpty()) {
                emailService.sendHtmlEmail(
                        user.getAccount().getEmail(),
                         order.getStatus().toString().equals("RETURN") ? "Xác nhận hoàn trả đơn hàng #" : "Đơn hàng đã hoàn thành: #"+ oldOrder.getId().substring(0, 5),
                        emailContent
                );
            }
        return "redirect:/admin/order";
    }

    @GetMapping("/detail/{id}")
    public String getPageDetail(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        Order order = orderService.findById(id);
        if(order != null) {

            if(order.isAnnounceOrder()){
                HttpSession session = request.getSession();
                session.setAttribute("totalAnnounce", Math.max((int)session.getAttribute("totalAnnounce") - 1, 0));

                order.setAnnounceOrder(false);
                orderService.saveOrder(order);
            }

            model.addAttribute("details", orderDetailService.findAllOrderDetailByOrderId(id));


        }
        model.addAttribute("order", order != null ? order : new Order());

        return "/admin/order/detail";
    }

    @GetMapping("/search")
    public String findAllOrderByStartId(@RequestParam("query") String id, Model model) {

        if(id.isEmpty()){
            return "redirect:/admin/order";
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> pOrder = orderService.findAllByStartId(id,pageable);
        List<Order> orders = pOrder.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", pOrder.getTotalPages());
        model.addAttribute("startID", HtmlUtils.htmlEscape(id));

        return "/admin/order/show";

    }

}
