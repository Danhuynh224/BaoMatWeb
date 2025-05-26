package org.sale.project.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Product;
import org.sale.project.entity.ProductVariant;
import org.sale.project.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/item")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantController {

    ProductService productService;
    ColorService colorService;
    SizeService sizeService;
    ProductVariantService productVariantService;
    UploadService uploadService;

    @GetMapping
    public String getPageItem(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Pageable pageable = PageRequest.of(page-1, 4);
        Page<ProductVariant> pItem = productVariantService.findAll(pageable);
        List<ProductVariant> items = pItem.getContent();
        model.addAttribute("productVariants", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pItem.getTotalPages());
        return "/admin/item/show";
    }

    @GetMapping("/create")
    public String getPageCreate(Model model,
                                @RequestParam("idProduct") Optional<String> idProduct
                                ) {

        if(idProduct.isPresent()){
            Product product = productService.findById(idProduct.get());
            if(product != null){
                model.addAttribute("productSelected", product);
            }
        }



        model.addAttribute("newItem", new ProductVariant());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());
        model.addAttribute("products", productService.findAll());

        return "/admin/item/create";
    }

    @PostMapping("/create")
    public String createProductItem(@ModelAttribute("newItem") ProductVariant productVariant,
                                    @RequestParam("imageItem") MultipartFile imageItem,
                                    Model model)  {



        if(productVariantService.checkExistsByColorAndProductAndSize(productVariant)){
            model.addAttribute("errorProductVariantExists", "Product variant đã tồn tại");
            model.addAttribute("newItem", productVariant);
            model.addAttribute("colors", colorService.findAll());
            model.addAttribute("sizes", sizeService.findAll());
            model.addAttribute("products", productService.findAll());
            return "/admin/item/create";
        }


        String img;
        if(imageItem.isEmpty() || productVariantService.checkExistsByColorAndProduct(productVariant)){
            img = productVariantService.getImage(productVariant);
        } else {
            img = uploadService.uploadImage(imageItem, "/product/" + productVariant.getProduct().getName());
        }

        productVariant.setImage(img);

        productVariant.setColor(colorService.findByName(productVariant.getColor().getName()));
        productVariant.setSize(sizeService.findByName(productVariant.getSize().getName()));
        productVariant.setProduct(productService.findByProductName(productVariant.getProduct().getName()));

        productVariantService.saveProductItem(productVariant);

        return "redirect:/admin/product/" + productVariant.getProduct().getId();

    }

    @GetMapping("/delete/{id}")
    public String deleteProductItem(@PathVariable("id") String id) {
        productVariantService.deleteProductItem(id);
        return "redirect:/admin/item";
    }

    @GetMapping("/update/{id}")
    public String updateProductItem(@PathVariable("id") String id, Model model
                                    ) {

        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("item", productVariantService.findById(id));
        return "/admin/item/update";
    }

    @PostMapping("/update")
    public String updateProductItem(@ModelAttribute("item") ProductVariant productVariant,
                                    @RequestParam("imageItem") MultipartFile imageItem) {

        ProductVariant prd = productVariantService.findById(productVariant.getId());


        if(!imageItem.isEmpty() && (prd.getImage() == null || !prd.getImage().contains(imageItem.getOriginalFilename())) ){
                String img = uploadService.uploadImage(imageItem, "/product/" + productVariant.getProduct().getName());

                productVariantService.updateAllImage(productVariant, img);
        }

        prd.setColor(colorService.findByName(productVariant.getColor().getName()));
        prd.setSize(sizeService.findByName(productVariant.getSize().getName()));
        prd.setQuantity(productVariant.getQuantity());
        prd.setPrice(productVariant.getPrice());
        prd.setProduct(productService.findByProductName(productVariant.getProduct().getName()));
        productVariantService.saveProductItem(prd);
        return "redirect:/admin/item";
    }

    @GetMapping("/search")
    public String findAllByNameProduct(@RequestParam("query") String nameProduct, Model model){

        if(nameProduct.isEmpty()){
            return "redirect:/admin/item";
        }
        List<Product> products = productService.findAll(nameProduct);

        Pageable pageable = PageRequest.of(0, 4);

        Page<ProductVariant> itemPage = productVariantService.findByProduct(products, pageable);
        List<ProductVariant> items = itemPage.getContent();
        System.out.println("items: " + itemPage.getContent().size());


        model.addAttribute("nameSearch", HtmlUtils.htmlEscape(nameProduct));
        model.addAttribute("productVariants", items);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", itemPage.getTotalPages());
        return "/admin/item/show";
    }




}
