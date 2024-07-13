package com.project.products.service;

import com.project.products.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product save(Product product);

    Product update(Product product);

    void deleteById(Long id);
}
