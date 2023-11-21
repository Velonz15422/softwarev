package com.pymextore.softwarev.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.repository.CategoryRepository;

import org.junit.Before;
import org.junit.Test;

public class CategoryServiceTest {

    private CategoryService categoryService;

    private CategoryRepository categoryRepositoryMock;

    @Before
    public void setUp() {
        categoryRepositoryMock = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepositoryMock);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        categoryService.createCategory(category);

        verify(categoryRepositoryMock, times(1)).save(category);
    }

    @Test
    public void testListCategory() {
        List<Category> categories = new ArrayList<>();
        when(categoryRepositoryMock.findAll()).thenReturn(categories);

        List<Category> result = categoryService.listCategory();

        assertSame(categories, result);
    }

    @Test
    public void testEditCategory() {
        int categoryId = 1;
        Category existingCategory = new Category();
        existingCategory.setCategoryName("Old Name");
        existingCategory.setDescription("Old Description");
        existingCategory.setImageUrl("Old Image URL");

        Category updateCategory = new Category();
        updateCategory.setCategoryName("New Name");
        updateCategory.setDescription("New Description");
        updateCategory.setImageUrl("New Image URL");

        when(categoryRepositoryMock.getById(categoryId)).thenReturn(existingCategory);

        categoryService.editCategory(categoryId, updateCategory);

        assertEquals("New Name", existingCategory.getCategoryName());
        assertEquals("New Description", existingCategory.getDescription());
        assertEquals("New Image URL", existingCategory.getImageUrl());
        verify(categoryRepositoryMock, times(1)).save(existingCategory);
    }

    @Test
    public void testFindById() {
        int categoryId = 1;
        when(categoryRepositoryMock.findById(categoryId)).thenReturn(Optional.of(new Category()));

        assertTrue(categoryService.findById(categoryId));
    }

    @Test
    public void testFindByIdNotFound() {
        int categoryId = 1;
        when(categoryRepositoryMock.findById(categoryId)).thenReturn(Optional.empty());

        assertFalse(categoryService.findById(categoryId));
    }
}
