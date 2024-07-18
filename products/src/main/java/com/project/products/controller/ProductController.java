package com.project.products.controller;

import com.project.products.service.ProductService;
import com.project.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id){
        Optional<Product> productsFound = service.findById(id);
        return productsFound.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format("There is no product with id '%s'", id)));
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts(){
        List<Product> productsFound = service.findAll();
        if (!productsFound.isEmpty())
            return ResponseEntity.ok(productsFound);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There is no product yet.");
    }

    @PostMapping
    private ResponseEntity<Object> saveProduct(@RequestBody Product product){
        try {
            Product productSaved = service.save(product);
            return ResponseEntity.created(new URI("/products/"+productSaved.getId())).body(productSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An internal server error occurred during saving product.");
        }
    }

    @PutMapping
    private ResponseEntity<Object> updateProduct(@RequestBody Product product){
        try {
            Product productUpdated = service.update(product);
            return ResponseEntity.ok(productUpdated);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(String.format("Error: Updating was not possible.\nCause: %s",e.getMessage()));
        }

    }

    @DeleteMapping (value = "/{id}")
    private ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
        try {
            service.deleteById(id);
            return ResponseEntity.ok(service.findById(id).isPresent());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(String.format("Error: Deleting was not possible.\nCause: %s",e.getMessage()));
        }

    }

}
