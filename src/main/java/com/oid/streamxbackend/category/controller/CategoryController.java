package com.oid.streamxbackend.category.controller;

import com.oid.streamxbackend.category.dto.CategoryResponse;
import com.oid.streamxbackend.category.dto.CreateCategoryRequest;
import com.oid.streamxbackend.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private  final CategoryService categoryService ;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory
            ( @Valid @RequestBody CreateCategoryRequest request){

      return categoryService.createCategory(request);
    }


    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }


}
