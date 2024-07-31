package com.laref.ProductSelling.dao;

import com.laref.ProductSelling.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
