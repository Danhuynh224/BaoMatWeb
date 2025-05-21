package org.sale.project.controller.admin;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductVariant;
import org.sale.project.service.CategoryService;
import org.sale.project.service.ProductVariantService;
import org.sale.project.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    CategoryService categoryService;
    ProductService productService;
    @GetMapping
    public String getPageProduct(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
                if (page < 1) {
                    throw new IllegalArgumentException("Số trang phải lớn hơn hoặc bằng 1");
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi phân tích số trang: " + e.getMessage());
            // Có thể set flash attribute để hiện thông báo trên giao diện
            return "redirect:/admin/product";
        }

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> pProduct = productService.findAll(pageable, true);

        List<Product> products = pProduct.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pProduct.getTotalPages());
        model.addAttribute("nameSearch", "");

        return "/admin/product/show";
    }


    @GetMapping("/create")
    public String getPageCreate(Model model) {
        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "/admin/product/create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("newProduct") @Valid Product product,
            BindingResult bindingResult, Model model) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            System.out.println(">>> " + fieldError.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());

            return "/admin/product/create";
        }

        product.setCategory(categoryService.findCategoryByName(product.getCategory().getName()));
        product.setStatus(false);
        productService.saveProduct(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/update/{id}")
    public String getPageUpdate(@PathVariable("id") String id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "/admin/product/update";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product productUpdate) {

        productService.updateProduct(productUpdate);
        return "redirect:/admin/product";
    }

    @GetMapping("search")
    public String findAllProductWithName(@RequestParam("query") String name, Model model) {

        if (name.isEmpty()) {
            return "redirect:/admin/product";
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> pProduct = productService.findAll(name, pageable);

        List<Product> products = pProduct.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", pProduct.getTotalPages());
        model.addAttribute("nameSearch", name);
        return "/admin/product/show";
    }

    @GetMapping("/{id}")
    public String getProductVariant(@PathVariable("id") String id, Model model) {

        Product product = productService.findById(id);

        if(product != null){
            model.addAttribute("productVariants", product.getProductVariant().isEmpty() ?
                    new ArrayList<ProductVariant>() : product.getProductVariant());

            model.addAttribute("product", product);
        } else{
            return "redirect:/admin/product";
        }


        return "/admin/product/detail";




    }

}
