package com.pymextore.softwarev.controller;

import com.pymextore.softwarev.common.ApiResponse;
import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.service.ProductRatingService;
import com.pymextore.softwarev.service.ProductService;
import com.pymextore.softwarev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class ProductRatingController {

    @Autowired
    private ProductRatingService productRatingService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/{productId}/rate")
    public ResponseEntity<ApiResponse> rateProduct(
            @PathVariable Long productId,
            @RequestParam("token") String token,
            @RequestParam("rating") int rating
    ) {
        User user = userService.authenticate(token);

        Product product = productService.getProductById(productId);

        productRatingService.rateProduct(product, user, rating);

        return new ResponseEntity<>(new ApiResponse(true, "Product rated successfully"), HttpStatus.OK);
    }
}
