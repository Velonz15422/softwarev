package com.pymextore.softwarev.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pymextore.softwarev.dto.ProductDto;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.model.WishList;
import com.pymextore.softwarev.repository.WishListRepository;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (WishList wishList: wishLists) {
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }

        return productDtos;
    }
}