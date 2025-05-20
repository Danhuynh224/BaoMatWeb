package org.sale.project.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.*;
import org.sale.project.enums.ActionType;
import org.sale.project.recommender.RecommenderSystem;
import org.sale.project.repository.CartItemRepository;
import org.sale.project.repository.UserActionRepository;
import org.sale.project.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;
    UserService userService;
    private final CartItemRepository cartItemRepository;
    ProductService productService;
    UserActionService userActionService;
    ProductVariantService productVariantService;



    @GetMapping
    public String getPageCart(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);
        Cart cart = cartService.findCartByUser(user);
        List<CartItem> items = cart == null ? new ArrayList<>() : cart.getCartItems();

        model.addAttribute("items", items);

        Set<Product> recommenderProducts = new HashSet<>();
        List<Product> products = productService.findAll();

       if(products.size() >= 2){
           for (CartItem item : items) {
               Product productRecommender = RecommenderSystem.recommendProducts(item.getProductVariant().getProduct(), products, 1).getFirst();

               recommenderProducts.add(productRecommender);
           }
       }

        for(CartItem item : items){
            recommenderProducts.remove(item.getProductVariant().getProduct());
        }


        model.addAttribute("recommenderProducts", recommenderProducts.stream().toList());

        model.addAttribute("totalPrice", cartService.totalPriceInCart(cart));

        return "/client/cart/show";
    }

    @PostMapping("/add-product-item-in-cart/{id}")
    public String addProductItemInCart(@PathVariable("id") String id,
                                       Model model,
                                       @AuthenticationPrincipal UserDetails userDetails,
                                       HttpServletRequest request){
        int quantity = 1;

        HttpSession session = request.getSession();
        String email = userDetails.getUsername();
        cartService.addProductToCart(email, id, quantity, session);

        User user = userService.findUserByEmail(email);

         userActionService.save(UserAction.builder().
                user(user)
                .product(productVariantService.findById(id).getProduct())
                         .actionType(ActionType.ADD_TO_CART)
                .build());

        return "redirect:/cart";
    }

    @GetMapping("/delete-item-in-cart/{id}")
    public String removeProductItemInCart(@PathVariable("id") String id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        String idProduct = cartItem.getProductVariant().getProduct().getId();
        cartService.removeCartItem(id);

        User user = userService.findUserByEmail(email);
        session.setAttribute("sum", user.getCart().getCartItems().size() - 1);

        for(UserAction userAction : user.getUserActions()){
            if(userAction.getProduct().getId().equals(idProduct) && userAction.getActionType().equals(ActionType.ADD_TO_CART)){
                System.out.println(userAction.getActionType());

                userActionService.deleteUserAction(userAction.getId());
                break;
            }
        }

        return "redirect:/cart";
    }


    @GetMapping("/updateUp/{id}")
    public String updateUp(@PathVariable("id") String id){
        Optional<CartItem> item = cartItemRepository.findById(id);
        if(item.isPresent()){
            item.get().setQuantity(item.get().getQuantity() + 1);
            cartItemRepository.save(item.get());
        }
        return "redirect:/cart";
    }

    @GetMapping("updateDown/{id}")
    public String updateDown(@PathVariable("id") String id, HttpServletRequest request){
        Optional<CartItem> item = cartItemRepository.findById(id);
        if(item.isPresent()){
            if(item.get().getQuantity() == 1){
                cartItemRepository.delete(item.get());
                HttpSession session = request.getSession();
                session.setAttribute("sum", (Integer) session.getAttribute("sum") - 1);
            }else{
                item.get().setQuantity(item.get().getQuantity() - 1);
                cartItemRepository.save(item.get());
            }
        }
        return "redirect:/cart";
    }


}
