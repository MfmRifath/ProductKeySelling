package com.laref.ProductSelling.services;

import com.laref.ProductSelling.dao.CartRepository;
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
    private CartRepository cartItemRepository;

    @Autowired
    private ProductService productService; // Assuming this service exists to fetch product details

    public List<Cart> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Transactional
    public void addCartItem(Long productId, int quantity) {
        Optional<Product> productOpt = Optional.ofNullable(productService.getProductById(productId));
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            // You can add more logic here, such as checking if the item already exists in the cart
            Cart cartItem = new Cart(product, quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void removeCart(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }



}
