package com.project.products.service;

import com.project.products.model.Product;
import com.project.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().sorted(Comparator.comparing(Product::getId)).toList();
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product update(Product product) {
        validateProductExistsById(product.getId());
        return repository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        validateProductExistsById(id);
        repository.deleteById(id);
    }

    private void validateProductExistsById(Long id){
        Optional<Product> productExisting = findById(id);
        if (productExisting.isEmpty()){
            throw new RuntimeException(String.format("Product does not exist with id %s.", id));
        }
    }
}
