package com.oid.streamxbackend.category.service;

import com.oid.streamxbackend.category.dto.CategoryResponse;
import com.oid.streamxbackend.category.dto.CreateCategoryRequest;
import com.oid.streamxbackend.category.entity.Category;
import com.oid.streamxbackend.category.repository.CategoryRepository;
import com.oid.streamxbackend.common.exception.CategoryAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private  final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

   public CategoryResponse createCategory(CreateCategoryRequest categoryRequest ){
        if(categoryRepository.existsByNameIgnoreCase(categoryRequest.name().trim())){
            throw  new CategoryAlreadyExistsException(categoryRequest.name());
        }

       Category category = Category.builder()
               .name(categoryRequest.name().trim())
               .description(categoryRequest.description())
               .build();

         return toResponse(categoryRepository.save(category));
   }


   public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
   }


   private  CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt()
        );
   }


}
