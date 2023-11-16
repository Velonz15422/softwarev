package com.pymextore.softwarev.service;


import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.User;

public interface ProductRatingService {
    void rateProduct(Product product, User user, int rating);
}