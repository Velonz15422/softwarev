package com.pymextore.softwarev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pymextore.softwarev.common.ApiResponse;
import com.pymextore.softwarev.dto.ProductDto;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.model.WishList;
import com.pymextore.softwarev.service.AuthenticationService;
import com.pymextore.softwarev.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;


    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token) {
        
        authenticationService.authenticate(token);


        // find the user

        User user = authenticationService.getUser(token);

        // save the item in wishlist

        WishList wishList = new WishList(user, product);

        wishListService.createWishlist(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }


    // get all wishlist item for a user

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);


        // find the user

        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }



}