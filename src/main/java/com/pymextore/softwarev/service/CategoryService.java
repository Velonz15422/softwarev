package com.pymextore.softwarev.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository2) {
	}

	public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    public void editCategory(int categoryId, Category updateCategory) {
        Category category = categoryRepository.getById(categoryId);
        category.setCategoryName(updateCategory.getCategoryName());
        category.setDescription(updateCategory.getDescription());
        category.setImageUrl(updateCategory.getImageUrl());
        categoryRepository.save(category);
    }

    public boolean findById(int categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }
}

