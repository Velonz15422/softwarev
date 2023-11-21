package com.pymextore.softwarev.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.pymextore.softwarev.dto.cart.AddToCartDto;
import com.pymextore.softwarev.dto.cart.CartDto;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.model.Cart;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.CartRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CartServiceTest {

    private CartService cartService;

    private ProductService productServiceMock;
    private CartRepository cartRepositoryMock;

    @Before
    public void setUp() {
        productServiceMock = mock(ProductService.class);
        cartRepositoryMock = mock(CartRepository.class);
        cartService = new CartService();
        cartService.productService = productServiceMock;
        cartService.cartRepository = cartRepositoryMock;
    }

    @Test
    public void testAddToCart() {
        User user = new User();
        AddToCartDto addToCartDto = new AddToCartDto();
        addToCartDto.setProductId(1);
        addToCartDto.setQuantity(2);

        Product product = new Product();
        product.setPrice(20.0);

        when(productServiceMock.findById(1)).thenReturn(product);
        when(productServiceMock.getAvailableQuantity(1)).thenReturn(5);

        cartService.addToCart(addToCartDto, user);

        verify(cartRepositoryMock, times(1)).save(any(Cart.class));
    }

    @Test(expected = CustomException.class)
    public void testAddToCartNotEnoughStock() {
        User user = new User();
        AddToCartDto addToCartDto = new AddToCartDto();
        addToCartDto.setProductId(1);
        addToCartDto.setQuantity(10);

        Product product = new Product();

        when(productServiceMock.findById(1)).thenReturn(product);
        when(productServiceMock.getAvailableQuantity(1)).thenReturn(5);

        cartService.addToCart(addToCartDto, user);
    }


    @Test
    public void testDeleteCartItem() {
        User user = new User();
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setQuantity(2);

        Optional<Cart> optionalCart = Optional.of(cart);

        when(cartRepositoryMock.findById(1)).thenReturn(optionalCart);

        cartService.deleteCartItem(1, user);

        verify(cartRepositoryMock, times(1)).delete(cart);
    }

    @Test(expected = CustomException.class)
    public void testDeleteCartItemInvalidId() {
        User user = new User();

        when(cartRepositoryMock.findById(1)).thenReturn(Optional.empty());

        cartService.deleteCartItem(1, user);
    }

    @Test(expected = CustomException.class)
    public void testDeleteCartItemNotBelongToUser() {
        User user = new User();
        Cart cart = new Cart();
        cart.setUser(new User()); // Different user

        Optional<Cart> optionalCart = Optional.of(cart);

        when(cartRepositoryMock.findById(1)).thenReturn(optionalCart);

        cartService.deleteCartItem(1, user);
    }
}
