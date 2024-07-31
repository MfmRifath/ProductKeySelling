package com.laref.ProductSelling.controller;

import com.laref.ProductSelling.entity.Product;
import com.laref.ProductSelling.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product_list";
    }
    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
       Product product= productService.getProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
