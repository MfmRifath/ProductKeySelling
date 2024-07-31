package com.laref.ProductSelling.dao;

import com.laref.ProductSelling.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
