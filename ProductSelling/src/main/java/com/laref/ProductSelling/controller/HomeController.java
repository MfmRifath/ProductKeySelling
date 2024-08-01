package com.laref.ProductSelling.controller;

import com.laref.ProductSelling.entity.Product;
import com.laref.ProductSelling.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Product> featuredProducts = productService.getAllProducts(); // For simplicity, using all products as featured
        model.addAttribute("featuredProducts", featuredProducts);
        return "home";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
