package com.laref.ProductSelling.controller;

import com.laref.ProductSelling.entity.Cart;
import com.laref.ProductSelling.entity.Product;
import com.laref.ProductSelling.services.CartService;
import com.laref.ProductSelling.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carts")
@SessionAttributes("carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("carts")
    public List<Cart> initializeCarts() {
        return cartService.getAllCartItems();
    }

    @GetMapping
    public String getCartPage(Model model) {
        List<Cart> carts = cartService.getAllCartItems();
        double total = carts.stream()
                .mapToDouble(item -> {
                    Double price = item.getProduct().getPrice();
                    return item.getQuantity() * (price != null ? price : 0);
                })
                .sum();
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        return "cart";
    }


    @PostMapping("/add")
    public String addCartItem(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        // Find the product by ID
        Optional<Product> productOpt = Optional.ofNullable(productService.getProductById(productId));
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            List<Cart> carts = (List<Cart>) model.getAttribute("carts");
            boolean itemFound = false;

            // Check if the product is already in the cart
            for (Cart cartItem : carts) {
                if (cartItem.getProduct().getId().equals(productId)) {
                    // Product already in the cart, increase quantity
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    cartService.saveCart(cartItem);  // Update existing cart item
                    itemFound = true;
                    break;
                }
            }

            // If the product is not in the cart, add a new Cart item
            if (!itemFound) {
                Cart newCartItem = new Cart(product, quantity);
                cartService.saveCart(newCartItem);  // Save new cart item
                carts.add(newCartItem);  // Add new item to the cart
            }
        }

        return "redirect:/carts";
    }

    @PostMapping("/remove")
    public String removeCartItem(@RequestParam Long cartItemId) {
        cartService.removeCart(cartItemId);
        return "redirect:/carts";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/carts";
    }
}
