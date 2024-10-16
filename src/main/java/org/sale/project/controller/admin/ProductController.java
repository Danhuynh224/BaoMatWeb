package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.service.CategoryService;
import org.sale.project.service.ProductItemService;
import org.sale.project.service.ProductService;
import org.sale.project.service.UploadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    CategoryService categoryService;
    ProductService productService;
    UploadService uploadService;
    ProductItemService productItemService;


    @GetMapping
    public String getPageProduct(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Product> pProduct = productService.findAll(pageable);


        List<Product> products =pProduct.getContent();



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
    public String createProduct(@ModelAttribute("newProduct") Product product,
                                @RequestParam("imageProduct") MultipartFile imageProduct) {

        String img = uploadService.uploadImage(imageProduct, "/product");

        product.setImage(img);
        product.setCategory(categoryService.findCategoryByName(product.getCategory().getName()));
        productService.saveProduct(product);

        return "redirect:/admin/product";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        try{
            productItemService.deleteAllByProductId(id);
        } catch (Exception e){

        }
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
    public String updateProduct(@ModelAttribute("product") Product product, @RequestParam("imageProduct") MultipartFile imageProduct) {
        String img = uploadService.uploadImage(imageProduct, "/product");
        product.setImage(img);
        product.setCategory(categoryService.findCategoryByName(product.getCategory().getName()));
        productService.saveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("search")
    public String findAllProductWithName(@RequestParam("query") String name, Model model) {

        if(name.isEmpty()){
            return "redirect:/admin/product";
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> pProduct = productService.findAll(name,pageable);

        List<Product> products =pProduct.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", pProduct.getTotalPages());
        model.addAttribute("nameSearch", name);
        return "/admin/product/show";
    }
}
