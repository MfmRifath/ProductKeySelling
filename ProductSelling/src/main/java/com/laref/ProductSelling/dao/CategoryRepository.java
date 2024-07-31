package com.laref.ProductSelling.dao;

import com.laref.ProductSelling.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
