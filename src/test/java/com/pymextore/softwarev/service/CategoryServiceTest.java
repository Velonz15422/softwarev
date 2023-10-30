/*package com.pymextore.softwarev.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.repository.CategoryRepository;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        categoryService.createCategory(category);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testListCategory() {
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> result = categoryService.listCategory();
        assertEquals(categories, result);
    }

    @Test
    public void testEditCategory() {
        int categoryId = 1;
        Category existingCategory = new Category();
        Category updatedCategory = new Category();
        when(categoryRepository.getById(categoryId)).thenReturn(existingCategory);

        categoryService.editCategory(categoryId, updatedCategory);
        verify(categoryRepository, times(1)).save(existingCategory);
        assertEquals(updatedCategory.getCategoryName(), existingCategory.getCategoryName());
        assertEquals(updatedCategory.getDescription(), existingCategory.getDescription());
        assertEquals(updatedCategory.getImageUrl(), existingCategory.getImageUrl());
    }

    @Test
    public void testFindByIdWhenCategoryExists() {
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));
        boolean result = categoryService.findById(categoryId);
        assertEquals(true, result);
    }

    @Test
    public void testFindByIdWhenCategoryDoesNotExists() {
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        boolean result = categoryService.findById(categoryId);
        assertEquals(false, result);
    }
}*/
