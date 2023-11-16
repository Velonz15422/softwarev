package com.pymextore.softwarev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pymextore.softwarev.dto.ProductDto;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.exceptions.ProductNotExistsException;
import com.pymextore.softwarev.model.Category;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        validateProductDto(productDto);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.save(product);
    }

    private void validateProductDto(ProductDto productDto) throws CustomException {
        if (productDto.getPrice() <= 1000) {
            throw new CustomException("Price must be greater than 1000");
        }

        if (productDto.getQuantity() <= 0) {
            throw new CustomException("Quantity cannot be negative or zero");
        }
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        product.setQuantity(productDto.getQuantity());

        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: allProducts) {
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new Exception("product not present");
        }
        validateProductDto(productDto);

        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }

    public Product findById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }
    public int getAvailableQuantity(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found id: " + productId));

        return product.getQuantity();
    }

    public Product getProductById(Long productId) {
        return null;
    }
}
