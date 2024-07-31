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
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getCartPage(Model model) {
        model.addAttribute("cartItems", cartService.getAllCartItems());
        return "cart";
    }

    @PostMapping("/add")
    public String addCartItem(@RequestParam Long productId, @RequestParam int quantity, @ModelAttribute("cartItems") List<Cart> cartItems) {
        // Check if the product is already in the cart
        Optional<Product> productOpt = Optional.ofNullable(productService.getProductById(productId));
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            boolean itemFound = false;

            // Iterate over cart items to find the product
            for (Cart cartItem : cartItems) {
                if (cartItem.getProduct().getId().equals(productId)) {
                    // Product already in the cart, increase quantity
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    itemFound = true;
                    break;
                }
            }

            // If the product is not in the cart, add a new CartItem
            if (!itemFound) {
                cartService.addCartItem(productId, quantity);
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeCartItem(@RequestParam Long cartItemId) {
        cartService.removeCart(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
