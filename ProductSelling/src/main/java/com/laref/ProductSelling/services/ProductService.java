package com.laref.ProductSelling.services;

import com.laref.ProductSelling.dao.ProductRepository;
import com.laref.ProductSelling.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void save(Product product) {
        productRepository.save( product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void saveProduct(MultipartFile imageFile, Product product) {
    }
}
