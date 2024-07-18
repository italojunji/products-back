package com.project.products.controller;

import com.project.products.model.Category;
import com.project.products.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts(){
        List<Category> categoriesFound = service.findAll();
        if (!categoriesFound.isEmpty())
            return ResponseEntity.ok(categoriesFound);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There is no category registered.");
    }

}
