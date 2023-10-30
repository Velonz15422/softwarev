/*package com.pymextore.softwarev.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pymextore.softwarev.dto.ProductDto;
import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.repository.ProductRepository;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        Category category = new Category();
        productService.createProduct(productDto, category);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetProductDto() {
        Product product = new Product();
        product.setDescription("Test Product");
        product.setImageURL("test.jpg");
        product.setName("Test Name");
        product.setPrice(100.0);
        Category category = new Category();
        category.setId(1);
        product.setCategory(category);

        ProductDto productDto = productService.getProductDto(product);

        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getImageURL(), productDto.getImageURL());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(category.getId(), productDto.getCategoryId());
        assertEquals(product.getPrice(), productDto.getPrice(), 0.001);
        assertEquals(product.getId(), productDto.getId());
    }


    @Test
    public void testUpdateProductWhenProductExists() throws Exception {
        int productId = 1;
        ProductDto productDto = new ProductDto();
        Optional<Product> optionalProduct = Optional.of(new Product());
        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        productService.updateProduct(productDto, productId);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test(expected = Exception.class)
    public void testUpdateProductWhenProductDoesNotExist() throws Exception {
        int productId = 1;
        ProductDto productDto = new ProductDto();
        Optional<Product> optionalProduct = Optional.empty();
        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        productService.updateProduct(productDto, productId);
    }
}*/
