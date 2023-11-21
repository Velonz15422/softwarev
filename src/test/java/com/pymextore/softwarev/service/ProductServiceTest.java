package com.pymextore.softwarev.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pymextore.softwarev.dto.ProductDto;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.exceptions.ProductNotExistsException;
import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepositoryMock;

    @Before
    public void setUp() {
        productRepositoryMock = mock(ProductRepository.class);
        productService = new ProductService();
        productService.productRepository = productRepositoryMock;
    }

    @Test
    public void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("Test Description");
        productDto.setImageURL("test-image.jpg");
        productDto.setName("Test Product");
        productDto.setCategoryId(1);
        productDto.setPrice(1500.0);
        productDto.setQuantity(10);

        Category category = new Category();
        category.setId(1);

        productService.createProduct(productDto, category);

        verify(productRepositoryMock, times(1)).save(any(Product.class));
    }

    @Test(expected = CustomException.class)
    public void testCreateProductInvalidPrice() {
        ProductDto productDto = new ProductDto();
        productDto.setPrice(900.0); // Invalid price

        Category category = new Category();

        productService.createProduct(productDto, category);
    }

    @Test(expected = CustomException.class)
    public void testCreateProductNegativeQuantity() {
        ProductDto productDto = new ProductDto();
        productDto.setQuantity(-5); // Negative quantity

        Category category = new Category();

        productService.createProduct(productDto, category);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        when(productRepositoryMock.findAll()).thenReturn(allProducts);

        List<ProductDto> result = productService.getAllProducts();

        assertSame(0, result.size());
    }


    @Test(expected = Exception.class)
    public void testUpdateProductNotFound() throws Exception {
        ProductDto productDto = new ProductDto();
        int productId = 1;

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        productService.updateProduct(productDto, productId);
    }

    @Test(expected = CustomException.class)
    public void testUpdateProductInvalidPrice() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setPrice(900.0); // Invalid price

        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Optional<Product> optionalProduct = Optional.of(existingProduct);
        when(productRepositoryMock.findById(productId)).thenReturn(optionalProduct);

        productService.updateProduct(productDto, productId);
    }

    @Test
    public void testFindById() throws ProductNotExistsException {
        int productId = 1;
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(new Product()));

        assertNotNull(productService.findById(productId));
    }

    @Test(expected = ProductNotExistsException.class)
    public void testFindByIdNotFound() throws ProductNotExistsException {
        int productId = 1;
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        productService.findById(productId);
    }

    @Test
    public void testGetAvailableQuantity() {
        int productId = 1;
        Product product = new Product();
        product.setQuantity(10);

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));

        assertEquals(10, productService.getAvailableQuantity(productId));
    }

    @Test(expected = CustomException.class)
    public void testGetAvailableQuantityNotFound() {
        int productId = 1;

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        productService.getAvailableQuantity(productId);
    }
}
