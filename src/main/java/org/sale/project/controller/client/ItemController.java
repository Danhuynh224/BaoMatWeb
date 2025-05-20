package org.sale.project.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.sale.project.dto.request.StarReview;
import org.sale.project.entity.*;
import org.sale.project.enums.ActionType;
import org.sale.project.recommender.RecommenderSystem;
import org.sale.project.service.*;
import org.sale.project.service.review.ScoreStarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ItemController {

    ProductService productService;
    ColorService colorService;
    SizeService sizeService;
    CategoryService categoryService;
    HistorySearchService historySearchService;
    UserService userService;

    UserActionService userActionService;
    ScoreStarService scoreStarService;


    @GetMapping
    public String getPageProducts(Model model, @RequestParam("name") Optional<String> nameOptional,
                                  @RequestParam("page") Optional<String> pageOptional, HttpServletRequest request, Map map) {


        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {

        }

        Pageable pageable = PageRequest.of(page - 1, 12);

        Page<Product> productPage;
        List<Product> products;
        if(nameOptional.isPresent()) {
            productPage = productService.findAll(nameOptional.get(), pageable);

            HttpSession session = request.getSession();
            session.setAttribute("nameSearch", HtmlUtils.htmlEscape(nameOptional.get()));
            products = productPage.getContent();
            String email = (String) session.getAttribute("email");

            if(email != null && userService.findUserByEmail(email) != null) {
                historySearchService.saveHistorySearch(HistorySearch.builder().
                        user(
                                userService.findUserByEmail(
                                        email
                                )
                        )
                        .title(nameOptional.get())
                        .localDate(LocalDate.now())

                        .build());

                for(Product product : products) {
                    userActionService.save(
                            UserAction.builder()
                                    .user(userService.findUserByEmail(session.getAttribute("email").toString()))
                                    .product(product)
                                    .actionType(ActionType.SEARCH)
                                    .build()
                    );
                }

            }





        } else{
            productPage = productService.findAll(pageable, false);
            products = productPage.getContent();
        }

        Map<Product, StarReview> mapProductStarReview = new HashMap<>();
        for(Product product : products) {
            mapProductStarReview.put(product, scoreStarService.score(product));
        }





        List<Category> categories = categoryService.findAll();
        List<Color> colors = colorService.findAll();
        List<Size> sizes = sizeService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);

        model.addAttribute("products", mapProductStarReview);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "/client/product/show";
    }

    @GetMapping("/detail/{id}")
    public String getPageDetailProduct(@PathVariable("id") String id, Model model,
            @RequestParam("size") Optional<String> sizeOptional,
            @RequestParam("color") Optional<String> colorOptional,
                                       HttpServletRequest request
                                       ) {

        String size = sizeOptional.orElse("");
        String color = colorOptional.orElse("");

        Product product = productService.findById(id);
        Set<Color> colors = new HashSet<>();
        Set<Size> sizes = new HashSet<>();
        Set<String> images = new HashSet<>();

        for (ProductVariant items : product.getProductVariant()) {
            sizes.add(items.getSize());
            images.add(items.getImage());
            if (size.isEmpty() || items.getSize().getName().equals(size)) {
                colors.add(items.getColor());
            }
        }

        ProductVariant selectedItem = null;
        for (ProductVariant item : product.getProductVariant()) {
            boolean sizeMatches = size.isEmpty() || item.getSize().getName().equals(size);
            boolean colorMatches = color.isEmpty() || item.getColor().getName().equals(color);

            if (sizeMatches && colorMatches) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            selectedItem = product.getProductVariant().getLast();
        }

        List<Color> sortedColors = new ArrayList<>(colors);
        sortedColors.sort(Comparator.comparing(Color::getName));

        List<Size> sortedSizes = new ArrayList<>(sizes);
        sortedSizes.sort(Comparator.comparing(Size::getName));

        StarReview starReview = scoreStarService.score(selectedItem.getProduct());


        model.addAttribute("starReview", starReview);
        model.addAttribute("images", images);
        model.addAttribute("item", selectedItem);
        model.addAttribute("colors", sortedColors);
        model.addAttribute("sizes", sortedSizes);
        model.addAttribute("newFeedBackReview", new FeedBackReview());


        // recommender
        List<Product> recommenderProducts = RecommenderSystem.recommendProducts(selectedItem.getProduct(),productService.findAll(), 4);



        recommenderProducts.forEach(x -> System.out.println(">>> name product - recommender" +x.getName()));
        System.out.println(recommenderProducts.size());
        model.addAttribute("recommenderProducts", recommenderProducts);


        // review


        return "/client/product/detail";
    }



    @GetMapping("/filter")
    public String filterProducts(
            @RequestParam(value = "categories", required = false) String categories,
            @RequestParam(value = "colors", required = false) String colors,
            @RequestParam(value = "sizes", required = false) String sizes,
            @RequestParam("minPrice") Optional<String> minPriceOptional,
            @RequestParam("maxPrice") Optional<String> maxPriceOptional,
            HttpServletRequest request,
            Model model) {


        double minPrice = Double.parseDouble(minPriceOptional.orElse("0"));
        double maxPrice = Double.parseDouble(maxPriceOptional.orElse("1000000000000.0"));

        if(minPrice > maxPrice) {
            double mid = minPrice;
            minPrice = maxPrice;
            maxPrice = mid;
        }

        model.addAttribute("min", minPriceOptional.orElse("0"));
        model.addAttribute("max", maxPriceOptional.orElse("100000"));



        List<String> categoryList = categories != null ? Arrays.asList(categories.split(",")) : Collections.emptyList();

        List<String> colorList = colors != null ? Arrays.asList(colors.split(",")) : Collections.emptyList();

        List<String> sizeList = sizes != null ? Arrays.asList(sizes.split(",")) : Collections.emptyList();

        Pageable pageable = PageRequest.of(0, 12);

        Page<Product> productPage = productService.filterProducts(categoryList, colorList, sizeList, minPrice, maxPrice,
                pageable);

        List<Product> products = productPage.getContent();

        // action
        HttpSession session = request.getSession();

        if(session.getAttribute("user") != null) {
            for(Product product : products) {
                userActionService.save(
                        UserAction.builder()
                                .user(userService.findUserByEmail(session.getAttribute("email").toString()))
                                .product(product)
                                .actionType(ActionType.SEARCH)
                                .build()
                );
            }
        }

        List<Category> category = categoryService.findAll();
        List<Color> color = colorService.findAll();
        List<Size> size = sizeService.findAll();

        Map<Product, StarReview> mapProductStarReview = new HashMap<>();
        for(Product product : products) {
            mapProductStarReview.put(product, scoreStarService.score(product));
        }

        model.addAttribute("categories", category);
        model.addAttribute("colors", color);
        model.addAttribute("sizes", size);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("colorList", colorList);

        model.addAttribute("products", mapProductStarReview);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", 1);

        return "client/product/show";
    }
}
