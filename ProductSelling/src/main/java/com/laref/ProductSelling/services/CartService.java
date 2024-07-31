package com.laref.ProductSelling.services;

import com.laref.ProductSelling.dao.CartRepository;
import com.laref.ProductSelling.dao.ProductRepository;
import com.laref.ProductSelling.entity.Cart;
import com.laref.ProductSelling.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository; // Assuming this service exists to fetch product details

    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    @Transactional
    public void addCartItem(Long productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Optional<Cart> existingCartItemOpt = cartRepository.findByProductId(productId);

            if (existingCartItemOpt.isPresent()) {
                // If the product is already in the cart, update the quantity
                Cart existingCartItem = existingCartItemOpt.get();
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                cartRepository.save(existingCartItem);
            } else {
                // If the product is not in the cart, add a new item
                Cart newCartItem = new Cart(product, quantity);
                cartRepository.save(newCartItem);
            }
        } else {
            // Handle the case where the product doesn't exist (optional)
            throw new IllegalArgumentException("Product not found for ID: " + productId);
        }
    }

    public void removeCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void clearCart() {
        cartRepository.deleteAll();
    }


    public void saveCart(Cart cartItem) {
        cartRepository.save(cartItem);
    }
}
