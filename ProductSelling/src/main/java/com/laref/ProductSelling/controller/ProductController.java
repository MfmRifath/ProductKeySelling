package com.laref.ProductSelling.controller;

import com.laref.ProductSelling.entity.Product;
import com.laref.ProductSelling.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;



    @Value("${upload.path}")
    private String uploadDir;

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product_list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }


    @PostMapping
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Model model) {
        try {
            if (!imageFile.isEmpty()) {
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = imageFile.getOriginalFilename();
                String filePath = Paths.get(uploadDir, fileName).toString();

                // Save the file locally
                imageFile.transferTo(new File(filePath));

                // Set the image filename in the product entity
                product.setImage("/images/products/"+fileName);
            } else {
                model.addAttribute("errorMessage", "Image file is required.");
                return "product_form";
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error saving uploaded file: " + e.getMessage());
            e.printStackTrace();
            return "product_form";
        }

        productService.save(product);
        return "redirect:/products/list";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product_form";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile imageFile, Model model) {
        handleImageUpload(product, imageFile, model);
        if (model.containsAttribute("errorMessage")) {
            return "product_form";
        }
        productService.save(product);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/list";
    }

    private void handleImageUpload(Product product, MultipartFile imageFile, Model model) {
        if (!imageFile.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = imageFile.getOriginalFilename();
                String filePath = Paths.get(uploadDir, fileName).toString();

                // Save the file locally
                imageFile.transferTo(new File(filePath));

                // Set the image filename or path in the product entity
                product.setImage("/images/products/" +fileName);
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Error saving uploaded file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            product.setImage(null); // Clear the image field if no file was uploaded

            model.addAttribute("errorMessage", "Image file is required.");
        }
    }
}
