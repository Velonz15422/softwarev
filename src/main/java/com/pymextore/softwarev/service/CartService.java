package com.pymextore.softwarev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pymextore.softwarev.dto.cart.AddToCartDto;
import com.pymextore.softwarev.dto.cart.CartDto;
import com.pymextore.softwarev.dto.cart.CartItemDto;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.model.Cart;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.CartRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user) {

        Product product = productService.findById(addToCartDto.getProductId());

        Integer productId = addToCartDto.getProductId();
        Integer requestedQuantity = addToCartDto.getQuantity();
        int availableQuantity = productService.getAvailableQuantity(productId);

        if (requestedQuantity > availableQuantity) {
            throw new CustomException("Not enough stock for your cart");
        }

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());


        // save the cart
        cartRepository.save(cart);

    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return  cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw  new CustomException("cart item does not belong to user: " +cartItemId);
        }

        cartRepository.delete(cart);


    }
}