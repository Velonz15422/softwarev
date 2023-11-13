package com.pymextore.softwarev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pymextore.softwarev.common.ApiResponse;
import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
    try {
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Category created successfully"), HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse(false, "Failed to create category: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping("/list")
    public List<Category> listCategory() {
        return categoryService.listCategory();

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId,
            @RequestBody Category category) {
        if (!categoryService.findById(categoryId)) {
            return new ResponseEntity<>(new ApiResponse(false, "category doesnt exist"), HttpStatus.NOT_FOUND);

        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true, "Edited"), HttpStatus.OK);

    }
}
